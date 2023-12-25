package com.ssgroup.zelu.controller.discuss;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.ssgroup.zelu.annotation.Permission;
import com.ssgroup.zelu.annotation.RequestPage;
import com.ssgroup.zelu.annotation.RequestToken;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.discuss.Reply;
import com.ssgroup.zelu.pojo.request.SchoolAndCourseId;
import com.ssgroup.zelu.pojo.type.Role;
import com.ssgroup.zelu.pojo.user.User;
import com.ssgroup.zelu.service.discuss.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/api/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    /**
     * 获取讨论的所有回复
     *
     * @param discussId 讨论ID
     * @param page 分页数组，默认为 page[0] -> page页 = 1 page[1] -> limit数量 = 10
     * @return 讨论的所有回复
     */
    @Operation(summary = "获取讨论的所有回复")
    @GetMapping("/info/{discussId}/list")
    public Result<PageList<Reply>> getReplay(@PathVariable Long discussId,
                                             @RequestPage int[] page){
        PageList<Reply> pageList = replyService.getReplyByDiscussId(discussId, page[0], page[1]);
        return pageList != null? Result.success(pageList) : Result.failure("获取失败");
    }


    /**
     * 添加讨论回复
     *
     * @param discussId 讨论ID
     * @param username token解析出的用户信息 不用额外传参
     * @param reply     回复内容
     * @return          返回结果字符串
     */
    //@Parameter(name = "username", description = "token解析出的用户信息 不用额外传参" , required = true)
    @Operation(parameters = @Parameter(name = "username", description = "token解析出的用户信息 不用额外传参",hidden = true))
    @ApiOperationSupport(ignoreParameters = "username")
    @PostMapping("/{discussId}/add")
    public Result<String> addDiscussReply(@PathVariable Long discussId,
                                          @RequestToken("username") Long username,
                                          @RequestBody Reply reply){
        reply.setDiscussId(discussId);
        reply.setUsername(username);
        replyService.addReply(reply);
        return Result.success();
    }


    /**
     * 更新讨论回复
     *
     * @param contentId 内容ID
     * @param username token解析出的用户信息 不用额外传参
     * @param reply 回复内容
     * @return 返回结果字符串
     */
    @PostMapping("/{contentId}/update")
    public Result<String> updateDiscussReplay(@PathVariable Long contentId,
                                      @RequestToken("username") Long username,
                                     @RequestBody Reply reply){
        reply.setUsername(username);
        reply.setContentId(contentId);
        replyService.updateReply(reply);
        return Result.success();
    }


    /**
     * 获取指定内容的星标状态
     * @param contentId 内容ID
     * @param username token解析出的用户信息 不用额外传参
     * @return 指定内容的星标状态，true表示已收藏，false表示未收藏
     */
    @GetMapping("/{contentId}/stars/status")
    public Result<String> getStarStatus(@PathVariable Long contentId, @RequestToken("username") Long username) {
        return replyService.getStarStatus(contentId, username)? Result.success("已点赞"):Result.failure("还未点赞");
    }


    /**
     * 通过HTTP GET请求添加指定内容的收藏星标
     *
     * @param contentId 内容ID
     * @param username token解析出的用户信息 不用额外传参
     * @return 添加星标的结果，类型为AtomicBoolean
     */
    @GetMapping("/{contentId}/stars/add")
    public Result<String> addStar(@PathVariable Long contentId, @RequestToken("username") Long username) {
        AtomicBoolean atomicBoolean = replyService.updateStar(contentId, username, true);
        return atomicBoolean.get()? Result.success("收藏成功"):Result.failure("收藏失败");
    }


    /**
     * 通过HTTP GET请求删除指定内容的收藏星标
     *
     * @param contentId 内容ID
     * @param username token解析出的用户信息 不用额外传参
     * @return 删除星标的结果，类型为AtomicBoolean
     */
    @GetMapping("/{contentId}/stars/delete")
    public Result<String> deleteStar(@PathVariable Long contentId,
                                    @RequestToken("username") Long username) {
        AtomicBoolean atomicBoolean = replyService.updateStar(contentId, username, false);
        return atomicBoolean.get()? Result.success("取消收藏成功"):Result.failure("取消收藏失败");
    }


    /**
     * 添加内容回复
     *
     * @param discussId 讨论ID
     * @param replyId 回复ID
     * @param username token解析出的用户信息 不用额外传参
     * @param reply 回复内容
     * @return 返回添加结果
     */
    @PostMapping("/{discussId}/{replyId}/add")
    public Result<String> addContentReply(@PathVariable Long discussId,
                                  @PathVariable Long replyId,
                                  @RequestToken("username") Long username,
                                  @RequestBody Reply reply) {
        reply.setDiscussId(discussId);
        reply.setReplyId(replyId);
        reply.setUsername(username);
        replyService.addReply(reply);
        return Result.success();
    }


    /**
     * 删除内容
     *
     * @param contentId 内容ID（路径变量）
     * @param username token解析出的用户信息 不用额外传参
     * @param reply 回复对象（请求体）
     * @return 删除结果字符串
     */
     @PostMapping("{discussId}/{contentId}/delete")
     public Result<String> deleteContent(@PathVariable Long contentId,
                                 @PathVariable Long discussId,
                                 @RequestToken("username") Long username,
                                 @RequestBody Reply reply){
         reply.setContentId(contentId);
         reply.setUsername(username);
         reply.setDiscussId(discussId);
         replyService.deleteReply(reply);
         return Result.success();
     }

     /**
      * 根据内容ID获取评分信息
      *
      * @param contentId 内容ID
      * @return 评分信息
      */
     @GetMapping("/{contentId}/score/info")
     public Result<Integer> getScore(@PathVariable Long contentId) {
         return Result.success(replyService.getScore(contentId));
     }

     /**
      * 给定讨论和内容的评分
      *
      * @param schoolAndCourseId 用来鉴权
      * @param contentId 内容ID
      * @param discussId 讨论ID
      * @param score 评分
      * @return 判断评分是否成功
      */
     @Permission(Role.TEACHER)
     @GetMapping("/{discussId}/{contentId}/score/judge")
     public Result<String> judgeScore(SchoolAndCourseId schoolAndCourseId,
                               @PathVariable Long contentId,
                               @PathVariable Long discussId,
                               Integer score) {
         Reply reply = Reply.builder().contentId(contentId).discussId(discussId).score(score).build();
         boolean judgeScore = replyService.judgeScore(reply);
         return judgeScore? Result.success("评分成功"):Result.failure("评分失败");
     }

}

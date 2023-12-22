package com.ssgroup.zelu.controller.discuss;

import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.discuss.Reply;
import com.ssgroup.zelu.service.discuss.ReplyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    /**
     * 获取讨论的所有回复
     *
     * @param discussId 讨论ID
     * @param page 当前页码
     * @param size 每页显示的记录数
     * @return 讨论的所有回复
     */
    @Operation(summary = "获取讨论的所有回复")
    @GetMapping("/info/{discussId}/list")
    public PageList<Reply> getReplay(@PathVariable Long discussId,
                                     @RequestParam(required = false,defaultValue = "1") int page,
                                     @RequestParam(required = false,defaultValue = "10") int size){
        return replyService.getReplyByDiscussId(discussId,page,size);
    }


    /**
     * 添加讨论回复
     *
     * @param discussId 讨论ID
     * @param username  用户名
     * @param reply     回复内容
     * @return          返回结果字符串
     */
    @PostMapping("/{discussId}/add")
    public String addDiscussReply(@PathVariable Long discussId,
                                  Long username,
                                  @RequestBody Reply reply){
        reply.setDiscussId(discussId);
        reply.setUsername(username);
        replyService.addReply(reply);
        return "success";
    }


    /**
     * 更新讨论回复
     *
     * @param contentId 内容ID
     * @param username 用户名
     * @param reply 回复内容
     * @return 返回结果字符串
     */
    @PostMapping("/{contentId}/update")
    public String updateDiscussReplay(@PathVariable Long contentId,
                                     Long username,
                                     @RequestBody Reply reply){
        reply.setUsername(username);
        reply.setContentId(contentId);
        replyService.updateReply(reply);
        return "success";
    }


    /**
     * 获取指定内容的星标状态
     * @param contentId 内容ID
     * @param username 用户名
     * @return 指定内容的星标状态，true表示已收藏，false表示未收藏
     */
    @GetMapping("/{contentId}/stars/status")
    public boolean getStarStatus(@PathVariable Long contentId, Long username) {
        return replyService.getStarStatus(contentId, username);
    }


    /**
     * 通过HTTP GET请求添加指定内容的收藏星标
     *
     * @param contentId 内容ID
     * @param username  用户名
     * @return 添加星标的结果，类型为AtomicBoolean
     */
    @GetMapping("/{contentId}/stars/add")
    public AtomicBoolean addStar(@PathVariable Long contentId, Long username) {
        return replyService.updateStar(contentId, username, true);
    }


    /**
     * 通过HTTP GET请求删除指定内容的收藏星标
     *
     * @param contentId 内容ID
     * @param username  用户名
     * @return 删除星标的结果，类型为AtomicBoolean
     */
    @GetMapping("/{contentId}/stars/delete")
    public AtomicBoolean deleteStar(@PathVariable Long contentId,
                                    Long username) {
        return replyService.updateStar(contentId, username, false);
    }


    /**
     * 添加内容回复
     *
     * @param discussId 讨论ID
     * @param replyId 回复ID
     * @param username 用户名
     * @param reply 回复内容
     * @return 返回添加结果
     */
    @PostMapping("/{discussId}/{replyId}/add")
    public String addContentReply(@PathVariable Long discussId,
                                  @PathVariable Long replyId,
                                  Long username,
                                  @RequestBody Reply reply) {
        reply.setDiscussId(discussId);
        reply.setReplyId(replyId);
        reply.setUsername(username);
        replyService.addReply(reply);
        return "success";
    }


    /**
     * 删除内容
     *
     * @param contentId 内容ID（路径变量）
     * @param username 用户名
     * @param reply 回复对象（请求体）
     * @return 删除结果字符串
     */
     @PostMapping("{disscuss}/{contentId}/delete")
     public String deleteContent(@PathVariable Long contentId,
                                 @PathVariable Long discussId,
                                 Long username,
                                 @RequestBody Reply reply){
         reply.setContentId(contentId);
         reply.setUsername(username);
         reply.setDiscussId(discussId);
         replyService.deleteReply(reply);
         return "success";
     }

     /**
      * 根据内容ID获取评分信息
      * @param contentId 内容ID
      * @return 评分信息
      */
     @GetMapping("/{contentId}/score/info")
     public int getScore(@PathVariable Long contentId) {
         return replyService.getScore(contentId);
     }

     /**
      * 给定讨论和内容的评分
      *
      * @param contentId 内容ID
      * @param discussId 讨论ID
      * @param username 用户名
      * @param score 评分
      * @return 判断评分是否成功
      */
     @GetMapping("/{discussId}/{contentId}/score/judge")
     public boolean judgeScore(@PathVariable Long contentId,
                                @PathVariable Long discussId,
                                Long username,
                                Integer score) {
         Reply reply = Reply.builder().contentId(contentId).discussId(discussId).username(username).score(score).build();
         return replyService.judgeScore(reply);
     }
}

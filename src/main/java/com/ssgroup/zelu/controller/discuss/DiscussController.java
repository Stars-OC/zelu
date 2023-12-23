package com.ssgroup.zelu.controller.discuss;

import com.ssgroup.zelu.annotation.Permission;
import com.ssgroup.zelu.annotation.RequestPage;
import com.ssgroup.zelu.annotation.RequestToken;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.discuss.Discuss;
import com.ssgroup.zelu.pojo.request.SchoolAndCourseId;
import com.ssgroup.zelu.pojo.type.Role;
import com.ssgroup.zelu.pojo.user.User;
import com.ssgroup.zelu.service.discuss.DiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Permission(Role.TEACHER)
@RequestMapping("/api/discuss")
public class DiscussController {

    @Autowired
    private DiscussService discussService;

    /**
     * 根据课程ID获取讨论列表
     * @param courseId 课程ID
     * @param page 分页数组，默认为 page[0] -> page页 = 1 page[1] -> limit数量 = 10
     * @return 讨论列表
     */
    @Permission({Role.USER,Role.COURSE_ASSISTANT})
    @GetMapping("/info/{courseId}/list")
    public Result<PageList<Discuss>> getDiscussBySchoolId(@PathVariable Long courseId,
                                                          @RequestPage int[] page){
        PageList<Discuss> list = discussService.getDiscussByCourseId(courseId, page[0],page[1]);
        return Result.success(list);
    }


    /**
     * 根据讨论ID获取讨论信息
     * @param discussId 讨论ID
     * @return 讨论信息
     */
    @Permission({Role.USER,Role.COURSE_ASSISTANT})
    @GetMapping("/info/{discussId}")
    public Result<Discuss> getDiscuss(@PathVariable String discussId){
        Discuss discuss = discussService.getDiscuss(discussId);
        return discuss == null? Result.failure("未找到该讨论") : Result.success(discuss);
    }


    /**
     * 添加讨论
     *
     * @param schoolAndCourseId 课程和学校ID
     * @param username token解析出的用户信息 不用额外传参
     * @param discuss  讨论内容
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result<String> addDiscuss(SchoolAndCourseId schoolAndCourseId,
                             @RequestToken("username") Long username,
                             @RequestBody Discuss discuss){
        discuss.setCourseId(schoolAndCourseId.getCourseId());
        discuss.setUsername(username);
        discussService.addDiscuss(discuss);
        return Result.success();
    }

    /**
     * 更新讨论
     *
     * @param schoolAndCourseId 课程和学校ID
     * @param discussId 讨论ID
     * @param discuss 讨论内容
     * @return 更新结果
     */
    @PostMapping("/{discussId}/update")
    public Result<String> updateDiscuss(SchoolAndCourseId schoolAndCourseId,
                                @PathVariable Long discussId,
                                @RequestBody Discuss discuss){
        discuss.setDiscussId(discussId);
        discuss.setCourseId(schoolAndCourseId.getCourseId());
        discussService.updateDiscuss(discuss);
        return Result.success();
    }


    /**
     * 根据讨论ID删除讨论
     *
     * @param schoolAndCourseId 课程和学校ID
     * @param discussId 讨论ID
     * @return 删除结果
     */
    @GetMapping("/{courseId}/{discussId}/delete")

    public Result<String> deleteDiscuss(SchoolAndCourseId schoolAndCourseId,
                                @PathVariable Long discussId,
                                @RequestToken("username") Long username){
        Discuss discuss = Discuss.builder().discussId(discussId).courseId(schoolAndCourseId.getCourseId()).username(username).build();
        discussService.deleteDiscuss(discuss);
        return Result.success();
    }



}

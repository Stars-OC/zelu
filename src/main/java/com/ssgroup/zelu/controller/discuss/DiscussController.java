package com.ssgroup.zelu.controller.discuss;

import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.discuss.Discuss;
import com.ssgroup.zelu.service.discuss.DiscussService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discuss")
public class DiscussController {

    @Autowired
    private DiscussService discussService;

    /**
     * 根据课程ID获取讨论列表
     * @param courseId 课程ID
     * @param page 当前页码
     * @param size 每页显示条数
     * @return 讨论列表
     */
    @GetMapping("/info/{courseId}/list")
    public PageList<Discuss> getDiscussBySchoolId(@PathVariable Long courseId,
                                                  @RequestParam(required = false,defaultValue = "1") int page,
                                                  @RequestParam(required = false,defaultValue = "10") int size){
        return discussService.getDiscussByCourseId(courseId,page,size);
    }


    /**
     * 根据讨论ID获取讨论信息
     * @param discussId 讨论ID
     * @return 讨论信息
     */
    @GetMapping("/info/{discussId}")
    public Discuss getDiscuss(@PathVariable String discussId){
        return discussService.getDiscuss(discussId);
    }


    /**
     * 添加讨论
     *
     * @param courseId 课程ID
     * @param username 用户名
     * @param discuss  讨论内容
     * @return 操作结果
     */
    @PostMapping("/{courseId}/add")
    public String addDiscuss(@PathVariable Long courseId,
                             Long username,
                             @RequestBody Discuss discuss){
        discuss.setCourseId(courseId);
        discuss.setUsername(username);
        discussService.addDiscuss(discuss);
        return "success";
    }

    /**
     * 更新讨论
     *
     * @param discussId 讨论ID
     * @param username 用户名
     * @param discuss 讨论内容
     * @return 更新结果
     */
    @PostMapping("/{discussId}/update")
    public String updateDiscuss(@PathVariable Long discussId,
                                Long username,
                                @RequestBody Discuss discuss){
        discuss.setDiscussId(discussId);
        discuss.setUsername(username);
        discussService.updateDiscuss(discuss);
        return "success";
    }


    /**
     * 根据讨论ID删除讨论
     *
     * @param discussId 讨论ID
     * @param courseId 课程
     * @return 删除结果
     */
    @GetMapping("/{courseId}/{discussId}/delete")
    public String deleteDiscuss(@PathVariable Long discussId,
                                @PathVariable Long courseId,
                                Long username){
        Discuss discuss = Discuss.builder().discussId(discussId).courseId(courseId).username(username).build();
        discussService.deleteDiscuss(discuss);
        return "success";
    }



}

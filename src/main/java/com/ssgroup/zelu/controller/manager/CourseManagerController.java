package com.ssgroup.zelu.controller.manager;

import com.ssgroup.zelu.annotation.Permission;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.department.Course;
import com.ssgroup.zelu.pojo.request.SchoolAndCourseId;
import com.ssgroup.zelu.pojo.type.Role;
import com.ssgroup.zelu.service.manager.CourseManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("course/api/manager")
@Validated
@Permission(Role.TEACHER)
@Slf4j
public class CourseManagerController {

    @Autowired
    private CourseManagerService courseManagerService;

    @Permission(Role.USER)
    @GetMapping("/info")
    public Result<Course> getInfo(@Validated SchoolAndCourseId schoolCourseID){
        Course info = courseManagerService.getInfo(schoolCourseID);
        return Result.success("查询成功",info);
    }
}

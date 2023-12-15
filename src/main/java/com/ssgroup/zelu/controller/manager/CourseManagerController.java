package com.ssgroup.zelu.controller.manager;

import com.ssgroup.zelu.annotation.Permission;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.department.Course;
import com.ssgroup.zelu.pojo.type.Role;
import com.ssgroup.zelu.service.manager.CourseManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("school/api/manager/{school}")
@Validated
@Permission(Role.SCHOOL_ADMIN)
@Slf4j
public class CourseManagerController {

    @Autowired
    private CourseManagerService courseManagerService;

    @RequestMapping("/add")
    public Result<String> addCourse(@Validated @RequestBody Course course, @PathVariable String school) {
//        courseManagerService.addCourse(course);
        return Result.success();
    }

}

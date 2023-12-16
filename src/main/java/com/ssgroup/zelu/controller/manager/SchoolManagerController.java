package com.ssgroup.zelu.controller.manager;

import com.ssgroup.zelu.annotation.Permission;
import com.ssgroup.zelu.annotation.RequestPage;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.department.Course;
import com.ssgroup.zelu.pojo.type.Role;
import com.ssgroup.zelu.service.manager.CourseManagerService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("school/api/manager")
@Validated
@Permission(Role.SCHOOL_ADMIN)
@Slf4j
public class SchoolManagerController {

    @Autowired
    private CourseManagerService courseManagerService;

    @GetMapping("/info")
    public Result<PageList<Course>> getCoursesBySchoolId(@RequestParam long schoolId, @RequestPage int[] page) {
        return Result.success(courseManagerService.getCoursesBySchoolId(schoolId, page[0], page[1]));
    }

    @PostMapping("/add")
    public Result<String> addCourse(@Validated @RequestBody Course course ) {
        courseManagerService.addByAdmin(course);
        return Result.success();
    }

    @PostMapping("/update")
    public Result<String> updateCourse(@RequestBody Course course) {
        courseManagerService.updateByAdmin(course);
        return Result.success();
    }

    @GetMapping("/delete")
    public Result<String> deleteCourse(@RequestParam @NotNull Long[] courses) {
        // 调用schoolManagerService的删除学校方法，返回删除的学校数量
        int count = courseManagerService.deleteByAdmin(courses);
        // 如果删除的学校数量大于0，则表示删除成功，返回成功的结果对象；否则返回删除失败的结果对象
        return count > 0 ?
                Result.success("删除 [" + count + "/" + courses.length + "] 成功") :
                Result.failure("删除失败");
    }
}

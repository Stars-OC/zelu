package com.ssgroup.zelu.controller.manager;

import com.ssgroup.zelu.annotation.Permission;
import com.ssgroup.zelu.annotation.RequestPage;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.department.Course;
import com.ssgroup.zelu.pojo.request.SchoolAndCourseId;
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

    /**
     * 根据学校ID获取课程信息
     *
     * @param schoolCourseID 学校与课程请求ID
     * @param page 分页信息
     * @return 课程信息结果
     */
    @GetMapping("/info")
    public Result<PageList<Course>> getCoursesBySchoolId(@Validated SchoolAndCourseId schoolCourseID, @RequestPage int[] page) {
        return Result.success(courseManagerService.getCoursesBySchoolId(schoolCourseID.getSchoolId(), page[0], page[1]));
    }


    /**
     * 添加课程 教师也可以访问
     *
     * @param schoolCourseID 学校与课程请求ID
     * @param course 课程信息
     */
    @Permission(Role.TEACHER)
    @PostMapping("/add")
    public Result<String> addCourse(@Validated SchoolAndCourseId schoolCourseID,@Validated @RequestBody Course course){
        courseManagerService.add(schoolCourseID.getSchoolId(),course);
        return Result.success();
    }


    /**
     * 更新课程
     *
     * @param schoolCourseID 学校和课程ID
     * @param course 课程对象
     * @return 更新结果
     */
    @Permission(Role.TEACHER)
    @PostMapping("/update")
    public Result<String> updateCourse(@Validated SchoolAndCourseId schoolCourseID,@Validated @RequestBody Course course){
        courseManagerService.update(schoolCourseID.getSchoolId(),course);
        return Result.success("更新成功");
    }



    @PostMapping("admin/update")
    public Result<String> updateCourseByAdmin(@RequestBody Course course) {
        courseManagerService.updateByAdmin(course);
        return Result.success();
    }

    @GetMapping("/delete")
    public Result<String> deleteCourse(@NotNull Long[] courses) {
        // 调用schoolManagerService的删除学校方法，返回删除的学校数量
        int count = courseManagerService.deleteByAdmin(courses);
        // 如果删除的学校数量大于0，则表示删除成功，返回成功的结果对象；否则返回删除失败的结果对象
        return count > 0 ?
                Result.success("删除 [" + count + "/" + courses.length + "] 成功") :
                Result.failure("删除失败");
    }

    @Permission({Role.TEACHER,Role.COURSE_ASSISTANT})
    @GetMapping("/test")
    public Result<String> test(@Validated SchoolAndCourseId schoolCourseID) {
        return Result.success(schoolCourseID.toString());
    }


}

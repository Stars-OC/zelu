package com.ssgroup.zelu.service.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssgroup.zelu.mapper.CourseMapper;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.department.Course;
import com.ssgroup.zelu.pojo.request.SchoolAndCourseId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CourseManagerService {

    @Autowired
    private CourseMapper courseMapper;

    public PageList<Course> getAllCourses(int page, int limit) {
        return getCourses(1,100,null);
    }

    public void addByAdmin(Course course) {
        courseMapper.insert(course);
    }

    public void updateByAdmin(Course course) {
        courseMapper.updateById(course);
    }

    public int deleteByAdmin(Long[] courses) {
        return courseMapper.deleteBatchIds(Arrays.stream(courses).toList());
    }

    public PageList<Course> getCoursesBySchoolId(long schoolId,int page, int limit){
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("school_id",schoolId);
        return getCourses(page,limit,queryWrapper);
    }

    private PageList<Course> getCourses(int page, int limit,QueryWrapper<Course> queryWrapper) {
        Page<Course> userPage = new Page<>(page,limit);

        Page<Course> selectPage = courseMapper.selectPage(userPage, null);
        return new PageList<>(selectPage);
    }

    public void add(long schoolId, Course course) {
        course.setSchoolId(schoolId);
        courseMapper.insert(course);
    }


    public void update(long schoolId, Course course) {
        course.setSchoolId(schoolId);
        courseMapper.updateById(course);
    }

    public Course getInfo(SchoolAndCourseId schoolCourseID) {
        Course course = courseMapper.selectById(schoolCourseID.getCourseId());
        return course;
    }
}

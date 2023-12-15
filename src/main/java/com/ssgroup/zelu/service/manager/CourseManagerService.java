package com.ssgroup.zelu.service.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssgroup.zelu.mapper.CourseMapper;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.department.Course;
import com.ssgroup.zelu.pojo.department.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseManagerService {

    @Autowired
    private CourseMapper courseMapper;
    public PageList<Course> getCourses(int page, int limit) {
        Page<Course> userPage = new Page<>(page,limit);

        Page<Course> selectPage = courseMapper.selectPage(userPage, null);
        return new PageList<>(selectPage);
    }
}

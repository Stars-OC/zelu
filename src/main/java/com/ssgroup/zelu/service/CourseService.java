package com.ssgroup.zelu.service;

import com.ssgroup.zelu.mapper.CourseMapper;
import com.ssgroup.zelu.pojo.request.SchoolAndCourseId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private CourseMapper courseMapper;
    /**
     * 校验用户角色
     *
     * @param role - 角色
     * @param username - 用户名
     * @param schoolAndCourseId - 学校和课程ID
     * @return - 校验结果
     */
    public boolean checkRole(int role, long username, SchoolAndCourseId schoolAndCourseId) {
        // 获取需要检查的角色
        Integer checkRole = courseMapper.getCourseRoleToCheck(username, schoolAndCourseId);

        // 直接返回需要检查的角色和传入的角色是否相等
        return checkRole != null && checkRole == role;
    }
}

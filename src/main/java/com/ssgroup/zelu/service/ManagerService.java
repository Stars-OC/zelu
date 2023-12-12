package com.ssgroup.zelu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssgroup.zelu.mapper.SchoolMapper;
import com.ssgroup.zelu.mapper.UserMapper;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.department.School;
import com.ssgroup.zelu.pojo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ManagerService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    public PageList<User> getUsers(int page, int limit) {
        Page<User> userPage = new Page<>(page,limit);

        Page<User> selectPage = userMapper.selectPage(userPage, null);
        return new PageList<>(selectPage);
    }

    public void updateUser(User user) throws Exception{

        userMapper.updateById(user);
    }

    public int deleteUsers(Long[] users) {
        return userMapper.deleteBatchIds(Arrays.stream(users).toList());
    }

    public void addUser(User user) throws Exception{
        userMapper.insert(user);
    }


    /** School 相关的CRUD **/


    public void addSchool(School school) {
        schoolMapper.insert(school);
    }

    public void updateSchool(School schoolData) {
        schoolMapper.updateById(schoolData);
    }

    public int deleteSchools(Long[] schools) {
        return schoolMapper.deleteBatchIds(Arrays.stream(schools).toList());
    }

    public PageList<School> getSchools(int page, int limit) {
        Page<School> userPage = new Page<>(page,limit);

        Page<School> selectPage = schoolMapper.selectPage(userPage, null);
        return new PageList<>(selectPage);
    }
}

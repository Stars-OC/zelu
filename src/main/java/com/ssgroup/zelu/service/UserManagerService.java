package com.ssgroup.zelu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssgroup.zelu.mapper.UserMapper;
import com.ssgroup.zelu.aop.permission.Permission;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.type.ResultCode;
import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserManagerService {

    @Autowired
    private UserMapper userMapper;

    public PageList<User> getUsers(int page, int size) {
        Page<User> userPage = new Page<>(page,size);

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
}

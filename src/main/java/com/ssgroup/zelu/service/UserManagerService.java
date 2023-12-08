package com.ssgroup.zelu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssgroup.zelu.mapper.UserMapper;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.ResultCode;
import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagerService {

    @Autowired
    private UserMapper userMapper;

    public Result<PageList<User>> getUsers(String token, String type, int page, int size) {
        Integer role = JwtUtil.getRole(token);
        if (role == 0){
            return Result.codeFailure(ResultCode.ACCESS_DENIED);
        }
        Page<User> userPage = new Page<>(page,size);

        Page<User> selectPage = userMapper.selectPage(userPage, null);
        PageList<User> userPageList = new PageList<>(selectPage);
        return Result.success(userPageList);
    }
}

package com.ssgroup.zelu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssgroup.zelu.filter.JwtUtil;
import com.ssgroup.zelu.mapper.UserMapper;
import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.pojo.UserNameAndPWD;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class UserService {

    @Resource(name = "userMapper")
    private UserMapper userMapper;

    private User isExists(String type, String value){
        return userMapper.selectOne(new QueryWrapper<User>().eq(type, value));
    }

    public User findUsername(String username){

        //System.out.println(userMapper.findByUsername(username));
        return isExists("username",username);
    }

    public User insertUser(UserNameAndPWD userNameAndPWD){
        User user = new User();
        String username = userNameAndPWD.getUsername();
        String password = userNameAndPWD.getPassword();
        user.setUsername(username);
        user.setNickname(username);
        user.setPassword(password);
        if (findUsername(username) == null && userMapper.insert(user) > 0){
            return user;
        }
        return null;
    }
//    public String login(){
//
//    }
}

package com.ssgroup.zelu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssgroup.zelu.filter.JwtUtil;
import com.ssgroup.zelu.mapper.UserMapper;
import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.pojo.UserNameAndPWD;
import com.ssgroup.zelu.pojo.WechatUser;
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

    /**后面用来适配微信登录**/
    public User insertUser(WechatUser wechatUser){
        User user = new User();
        String username = wechatUser.getNickname();
        String password = wechatUser.getNickname();
        user.setUsername(username);
        user.setNickname(username);
        user.setPassword(password);
        if (findUsername(username) == null && userMapper.insert(user) > 0){
            return user;
        }
        return null;
    }

    public boolean register(User user){
        if (user == null){
            return false;
        }

        if (findUsername(user.getUsername()) != null){
            return false;
        }

        return userMapper.insert(user) > 0;
    }
//    public String login(){
//
//    }
}

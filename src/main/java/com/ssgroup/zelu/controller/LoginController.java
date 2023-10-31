package com.ssgroup.zelu.controller;

import com.ssgroup.zelu.filter.JwtUtil;
import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.pojo.UserNameAndPWD;
import com.ssgroup.zelu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    public String login(@RequestBody UserNameAndPWD userNameAndPWD){

        User user = userService.findUsername(userNameAndPWD.getUsername());

        try {
            if (userNameAndPWD.getPassword().equals(user.getPassword())){
                return JwtUtil.createJwt(user,10000);
            }
        }catch (NullPointerException e){
            log.warn(e.getMessage());
            return "账号密码错误";
        }

        return "账号密码错误";
    }

    @PostMapping("/user/register")
    public String register(@RequestBody UserNameAndPWD userNameAndPWD){

        User user = userService.insertUser(userNameAndPWD);
        if (user != null){
            return JwtUtil.createJwt(user,10000);
        }

        return "账号已被注册";
    }

    @GetMapping("/user/test")
    public String test(){
        return "OK";
    }
}

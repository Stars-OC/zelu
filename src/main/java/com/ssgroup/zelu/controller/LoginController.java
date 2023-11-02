package com.ssgroup.zelu.controller;

import com.ssgroup.zelu.filter.JwtUtil;
import com.ssgroup.zelu.pojo.Result;
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

    /**
     * 用户登录
     * @param userNameAndPWD 用户名和密码
     * @return 登录成功返回JWT，登录失败返回"账号密码错误"
     */
    @PostMapping("/user/login")
    public Result<String> login(@RequestBody UserNameAndPWD userNameAndPWD){

        // 根据用户名查找用户
        User user = userService.findUsername(userNameAndPWD.getUsername());

        try {
            // 判断密码是否匹配
            if (userNameAndPWD.getPassword().equals(user.getPassword())){
                // 登录成功，返回JWT
                String jwt = JwtUtil.createJwt(user, 10000);
                return Result.success(jwt);
            }
        }catch (NullPointerException e){
            log.warn(e.getMessage());
            // 密码不匹配或用户不存在，返回"账号密码错误"
            return Result.failure("账号密码错误");
        }

        // 密码不匹配或用户不存在，返回"账号密码错误"
        return Result.failure("账号密码错误");
    }


    @PostMapping("/user/register")
    public Result<String> register(@RequestBody User user){

        if (userService.register(user)){
            String jwt = JwtUtil.createJwt(user, 10000);
            return Result.success(jwt);
        }

        return Result.failure("账号已被注册");
    }

    @GetMapping("/user/test")
    public String test(){
        return "OK";
    }
}

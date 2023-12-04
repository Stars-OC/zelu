package com.ssgroup.zelu.controller;

import com.ssgroup.zelu.filter.JwtUtil;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.pojo.UsernameAndPWD;
import com.ssgroup.zelu.service.UserService;
import com.ssgroup.zelu.utils.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     *
     * @param usernameAndPWD 用户名和密码
     * @return 登录成功返回JWT，登录失败返回"账号密码错误"
     */
    @PostMapping("/user/login")
    public Result login(@Validated @RequestBody UsernameAndPWD usernameAndPWD){

        return userService.login(usernameAndPWD);

    }

    /**
     * 使用微信登录
     * @param code 微信授权码
     * @return 登录结果
     */
    @GetMapping("/user/login/wechat")
    public Result loginWechat(@RequestParam String code){

        return userService.loginWechat(code);

    }


    /**
     * 用户注册
     * POST /user/register
     *
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping("/user/register")
    public Result register(@Validated @RequestBody User user){

        return userService.register(user);

    }


    /**
     * 验证Token
     * @return 验证结果
     */
    @GetMapping("/verify")
    public Result<String> verify(){
        return Result.success("token验证成功");
    }

    @GetMapping("/user/logout")
    public Result<String> logout(){

        return Result.success("退出成功");
    }




    @GetMapping("/test")
    public Result<User> test(){
        return Result.success("test",userService.findUsername(Long.valueOf(1)));
    }

    @GetMapping("/test2")
    public ResponseEntity<Result<User>> test2(){
        ResponseEntity.BodyBuilder status = ResponseEntity.status(404);
        return status.body(Result.success("test",userService.findUsername(Long.valueOf(2234))));
    }
}

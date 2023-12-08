package com.ssgroup.zelu.controller;

import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.pojo.UsernameAndPWD;
import com.ssgroup.zelu.service.AuthService;
import com.ssgroup.zelu.service.JwtService;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Validated
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    /**
     * 用户登录
     *
     * @param usernameAndPWD 用户名和密码
     * @return 登录成功返回JWT，登录失败返回"账号密码错误"
     */
    @PostMapping("/login")
    public Result<String> login(@Validated @RequestBody UsernameAndPWD usernameAndPWD){

        return authService.login(usernameAndPWD);

    }

    /**
     * 使用微信登录
     * @param code 微信授权码
     * @return 登录结果
     */
    @GetMapping("/login/wechat")
    public Result<String> loginWechat(@RequestParam String code){

        return authService.loginWechat(code);

    }


    /**
     * 用户注册
     * POST /user/register
     *
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<String> register(@Validated @RequestBody User user){

        return authService.register(user);

    }


    /**
     * 验证Token
     * @return 验证结果
     */
    @GetMapping("/verify")
    public Result<String> verify(){
        return Result.success("token验证成功");
    }

    /**
     * 退出登录
     *
     * @return 登录结果
     */
    @GetMapping("/logout")
    public Result<String> logout(@RequestParam @NotEmpty(message = "用户名不能为空") String username){

        jwtService.deleteJwtByUsername(username);

        return Result.success("退出成功");
    }


}

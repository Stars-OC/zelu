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
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Value("${user.verifyTime}")
    private int verifyTime;

    /**
     * 用户登录
     *
     * @param usernameAndPWD 用户名和密码
     * @return 登录成功返回JWT，登录失败返回"账号密码错误"
     */
    @PostMapping("/user/login")
    public Result<String> login(@RequestBody UsernameAndPWD usernameAndPWD){

        // 根据用户名查找用户
        User user = userService.findUsername(usernameAndPWD.getUsername());

        try {
            // 判断密码是否匹配
            String password = AesUtil.encrypt(usernameAndPWD.getPassword());
            if (password.equals(user.getPassword())){
                // 登录成功，返回JWT
                String jwt = JwtUtil.createJwt(user, verifyTime);
                return Result.success("登录成功",jwt);
            }
        }catch (NullPointerException e){
            log.warn(e.getMessage());
        }

        // 密码不匹配或用户不存在，返回"账号密码错误"
        return Result.failure("账号密码错误");
    }

    /**
     * 使用微信登录
     * @param code 微信授权码
     * @return 登录结果
     */
    @GetMapping("/user/login/wechat")
    public Result<String> loginWechat(@RequestParam String code){
        // 调用服务端的微信登录方法，传入授权码，返回用户信息
        User user = userService.loginWechat(code);
        // 如果用户为空，返回登录失败结果
        if (user == null){
            return Result.failure("登录失败 请重试");
        }
        // 利用用户信息生成JWT token，设置过期时间为10000秒
        String jwt = JwtUtil.createJwt(user, verifyTime);
        // 返回登录成功结果，包含JWT token
        return Result.success("授权成功",jwt);
    }


    /**
     * 用户注册
     * POST /user/register
     *
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping("/user/register")
    public Result<String> register(@RequestBody User user){

        if (userService.register(user)){
            String jwt = JwtUtil.createJwt(user, 10000);
            return Result.success("账户注册成功",jwt);
        }

        return Result.failure("账号已被注册");
    }


    /**
     * 验证Token
     * @return 验证结果
     */
    @GetMapping("/verify")
    public Result<String> verify(){
        return Result.success("token验证成功");
    }

}

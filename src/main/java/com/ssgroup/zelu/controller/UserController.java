package com.ssgroup.zelu.controller;

import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.service.JwtService;
import com.ssgroup.zelu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    /**
     * 刷新token
     * @param token 要刷新的token
     * @return 刷新结果
     */
    @GetMapping("/refresh_token")
    public Result<String> refreshToken(@RequestHeader String token){

        return Result.success("刷新token成功",jwtService.uploadJwtByToken(token));

    }

    /**
     * 获取用户信息
     *
     * @param token 用户的token
     * @return 返回用户信息的结果对象
     */
    @GetMapping("/info")
    public Result userInfo(@RequestHeader String token){
        return userService.getUserInfo(token);
    }


     /**
     * 上传用户信息
     *
     * @param user 用户信息
     * @param token 请求头token
     * @return 结果对象
     */
    @PostMapping("/upload/info")
    public Result uploadInfo(@RequestBody @Validated User user,@RequestHeader String token){

        return userService.uploadInfo(user,token);
    }


    @PostMapping("/upload/avatar")
    public Result uploadAvatar(){

        return Result.success("上传头像成功");
    }


    @GetMapping("/test")
    public Result<User> test(){
        return Result.success("test",userService.findUsername(Long.valueOf(1)));
    }

}

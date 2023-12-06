package com.ssgroup.zelu.controller;

import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.service.AvatarService;
import com.ssgroup.zelu.service.JwtService;
import com.ssgroup.zelu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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


    /**
     * 上传用户头像
     *
     * @param file 上传的文件
     * @param token 请求头中的token
     * @return 返回上传结果
     * @throws IOException 异常情况
     */
    @PostMapping("/upload/avatar/oss")
    public Result uploadAvatarByOSS(@RequestBody MultipartFile file,@RequestHeader String token) throws IOException {
        //TODO 后面接口可以用文件系统读写，以username为标识
        return userService.uploadAvatarByOSS(file,token);
    }

    /**
     * 上传用户头像
     * @param file 要上传的文件
     * @param token 请求头中的token
     * @return 返回上传结果
     * @throws IOException 如果发生I/O错误
     */
    @PostMapping("/upload/avatar")
    public Result uploadAvatar(@RequestBody MultipartFile file,@RequestHeader String token) throws IOException {
        return userService.uploadAvatar(file,token);
    }



    /**
     * 根据用户名下载头像
     * @param username 用户名
     * @return 返回 ResponseEntity 类型对象
     * @throws IOException 异常
     */
    @GetMapping("/download/avatar/{username}")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable String username) throws IOException {
        // 设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        // 从用户服务中下载头像字节数组
        byte[] bytes = userService.downloadAvatar(username);

        // 设置下载文件的名称
        headers.setContentDispositionFormData("attachment", username + ".jpg");

        // 返回 ResponseEntity 类型对象，包含字节数组、响应头和状态码
        return new ResponseEntity<>(bytes, headers, org.springframework.http.HttpStatus.OK);
    }


}

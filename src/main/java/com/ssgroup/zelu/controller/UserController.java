package com.ssgroup.zelu.controller;

import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.user.User;
import com.ssgroup.zelu.service.auth.JwtService;
import com.ssgroup.zelu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
     *
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
    public Result<User> userInfo(@RequestHeader String token){
        return userService.getUserInfo(token) != null?
                Result.success("获取用户信息成功",userService.getUserInfo(token)) :
                Result.failure("获取用户信息失败");
    }


     /**
     * 上传用户信息
     *
     * @param user 用户信息
     * @param token 请求头token
     * @return 结果对象
     */
    @PostMapping("/upload/info")
    public Result<String> uploadInfo(@RequestBody @Validated User user,@RequestHeader String token){

        return userService.uploadInfo(user,token) != null?
                Result.success("上传用户信息成功") :
                Result.failure("上传用户信息失败");
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
    public Result<String> uploadAvatarByOSS(@RequestBody MultipartFile file,@RequestHeader String token) throws IOException {
        if (file.isEmpty()) {
            return Result.failure("上传失败，请检查文件是否为空");
        }
        if (file.getSize() > 1024 * 1024 * 10) {
            return Result.failure("上传失败，请检查文件大小是否超过10M");
        }
        return userService.uploadAvatarByOSS(file,token);
    }

    /**
     * 上传用户头像
     *
     * @param file 要上传的文件
     * @param token 请求头中的token
     * @return 返回上传结果
     * @throws IOException 如果发生I/O错误
     */
    @PostMapping("/upload/avatar")
    public Result<String> uploadAvatar(@RequestBody MultipartFile file,@RequestHeader String token) throws IOException {
        if (file.isEmpty()) {
            return Result.failure("上传失败，请检查文件是否为空");
        }
        if (file.getSize() > 1024 * 1024 * 10) {
            return Result.failure("上传失败，请检查文件大小是否超过10M");
        }
        String newToken = userService.uploadAvatarByLocal(file, token);
        return newToken != null ?
                Result.success("上传头像成功",newToken) :
                Result.failure("上传头像失败");
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
        return new ResponseEntity<>(bytes, HttpStatus.OK);
    }


}

package com.ssgroup.zelu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ssgroup.zelu.config.FileConfiguration;
import com.ssgroup.zelu.mapper.UserMapper;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.user.User;
import com.ssgroup.zelu.service.auth.JwtService;
import com.ssgroup.zelu.utils.AesUtil;
import com.ssgroup.zelu.utils.AliOSSUtil;
import com.ssgroup.zelu.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private FileConfiguration file;

    /**
     * 判断给定条件在指定数据表中是否存在
     * @param mapper 数据表名
     * @param type 列名
     * @param value 列值
     * @return 存在返回对应对象，否则返回null
     */
    private Object isExists(String mapper, String type, Object value) {
        switch (mapper) {
            case "user":
                return userMapper.selectOne(new QueryWrapper<User>().eq(type, value));
            default:
                return null;
        }
    }

    /**
     * 根据指定的用户名查找用户
     *
     * @param username 要查找的用户名
     * @return 如果找到匹配的用户，则返回该用户对象；否则返回null
     */
    public User findUsername(Long username){
        return (User) isExists("user","username", username);
    }




    /**
     * @deprecated 获取用户信息 (前端可以直接取playload中的用户信息)
     *
     * @param token 用户的token
     * @return 返回包含成功标志和用户信息的结果对象
     */
    @Deprecated
    public User getUserInfo(String token) {

        return new User(token);
    }


    /**
     * 更新用户信息
     *
     * @param user    要更新的用户对象
     * @return 更新结果及更新后的用户对象
     */
    public String updateInfo(User user) {
        if (user.getNewPassword() != null){
            User oldUser = findUsername(user.getUsername());
            String newPassword = AesUtil.encrypt(user.getNewPassword());
            if (oldUser.getPassword().equalsIgnoreCase(newPassword)) {
                // 对更新后的用户密码进行加密
                user.setPassword(newPassword);
            }else {
                return null;
            }
        }

        // 更新用户信息至数据库
        userMapper.updateById(user);
        String jwt = jwtService.createJwt(user);
        // 返回更新成功的提示信息及更新后的用户对象
        return jwt;
    }

    /**
     * 通过OSS上传头像
     *
     * @param file          头像文件
     * @param token         JWT token
     * @return              上传结果及新的token
     * @throws IOException  IO异常
     */
    public Result<String> uploadAvatarByOSS(MultipartFile file,String token) throws IOException {
        //OSS写法
        String uploadUrl = AliOSSUtil.upload(file);
        if (uploadUrl == null) {
            return null;
        }

        Long username = JwtUtil.getUsername(token);

        userMapper.updateAvatar(username, uploadUrl);
        String newToken = jwtService.uploadJwtByToken(token);
        return Result.success("上传成功",newToken);
    }

    /**
     * 通过本地文件上传工具进行头像上传
     *
     * @param file 上传的头像文件
     * @param token 上传凭证
     * @return 上传结果及新的token
     * @throws IOException 文件读取或上传过程中发生IO异常
     */
    @Transactional
    public String uploadAvatarByLocal(MultipartFile file, String token) throws IOException {
        // 通过Local写法进行文件上传
        // 获取用户名
        Long username = JwtUtil.getUsername(token);
        // 进行文件上传并获取上传后的URL
        String uploadUrl = uploadAvatarByLocal(file, username);
        if (uploadUrl == null) {
            return null;
        }
        // 更新用户头像路径
        userMapper.updateAvatar(username, uploadUrl);
        // 通过上传的token生成新的token
        String newToken = jwtService.uploadJwtByToken(token);
        return newToken;
    }

    /**
     * 下载用户头像
     *
     * @param username 用户名
     * @return 下载的头像数据
     * @throws IOException 如果发生I/O错误
     */
    public byte[] downloadAvatar(String username) throws IOException {
        return getAvatar(username);
    }


    /**
     * 获取头像数据
     *
     * @param avatar 头像路径
     * @return 头像数据的字节数组
     * @throws IOException 输入输出异常
     */
    public byte[] getAvatar(String avatar) throws IOException {
        // 如果头像路径为空
        if (StringUtils.isEmpty(avatar)) {
            // 返回空字节数组
            return null;
        }
        // 构建头像文件路径
        String path = file.getAvatarPath() + avatar;
        // 创建头像文件对象
        File file = new File(path);
        // 如果头像文件存在
        if (file.exists()) {
            // 返回头像文件的字节数组
            return Files.readAllBytes(file.toPath());
        }
        // 头像文件不存在，返回空字节数组
        return null;
    }


    /**
     * 本地上传头像
     *
     * @param multipartFile 多部分文件（包含头像文件）
     * @param username 用户名
     * @return 上传后的头像URL
     * @throws IOException 如果发生I/O错误
     */
    public String uploadAvatarByLocal(MultipartFile multipartFile, Long username) throws IOException {
        // 获取文件名称
        String fileName = multipartFile.getOriginalFilename();

        int i = fileName.lastIndexOf(".");
        String suffix = fileName.substring(i);

        String name = username + suffix;

        String path = file.getAvatarPath() + name;

        File avatar = new File(path);
        multipartFile.transferTo(avatar);

        return file.getAvatarUrl(name);
    }

}

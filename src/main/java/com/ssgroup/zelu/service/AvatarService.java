package com.ssgroup.zelu.service;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.ssgroup.zelu.config.FileConfiguration;
import com.ssgroup.zelu.filter.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@Service
public class AvatarService {

    @Autowired
    private FileConfiguration file;

    /**
     * 获取头像数据
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
     * 上传头像
     * @param multipartFile 多部分文件（包含头像文件）
     * @param username 用户名
     * @return 上传后的头像URL
     * @throws IOException 如果发生I/O错误
     */
    public String uploadAvatar(MultipartFile multipartFile, Long username) throws IOException {
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

package com.ssgroup.zelu.config;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * @deprecated 文件上传配置
 *
 */
@Configuration
@Slf4j
public class FileConfiguration {


    @Autowired
    private ApplicationContext applicationContext;

    @Value("${fileUpload.path}")
    private String path;


    @Value("${fileUpload.avatar}")
    private String avatar;

    @Value("${fileUpload.url}")
    private String url;


    public String getPath() {
        if (StringUtils.isEmpty(path)) {
            path = System.getProperty("user.dir");
        }
        return path;
    }

    public String getAvatarPath() {
        return getPath() + "/" + avatar;
    }

    @Bean
    public void avatarMkdir(){

        // 创建文件对象，获取用户头像路径
        File file = new File(getAvatarPath());

        // 如果文件目录不存在并且创建失败
        if(!file.exists() && !file.mkdir()){
            // 记录错误日志，提示创建文件目录失败
            log.error("创建文件目录失败");
            // 退出Spring Boot应用
            SpringApplication.exit(applicationContext);
        }else if(!file.exists()){
            // 记录日志，提示文件目录已存在
            log.info("文件目录创建成功");
        }

        // 记录日志，提示文件目录创建成功
        log.info("文件目录加载成功");
    }

    public String getAvatarUrl(String username){
        return url + "api/user/download/avatar/" + username;
    }

}

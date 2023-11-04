package com.ssgroup.zelu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssgroup.zelu.pojo.WechatUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class WechatAuth {

    private OkHttpClient client = new OkHttpClient.Builder().connectTimeout(1, TimeUnit.SECONDS).build();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${wechat.appID}")
    private String appID;

    @Value("${wechat.appSecret}")
    private String appSecret;


    /**
     * 获取访问令牌
     * @param code 微信授权码
     * @return 访问令牌字符串
     * @throws IOException 输入输出异常
     */
    public String getAccessToken(String code) throws IOException {
        String url = "https://api.weixin.qq.com/sns/jscode2session?&appid=" + appID + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
        Request request = new Request.Builder().url(url).build();
        String result = client.newCall(request).execute().body().string();
        return result;
    }

    /**
     * 根据code获取微信用户信息
     * @param code 微信开放平台颁发的code
     * @return WechatUser对象，表示微信用户信息
     */
    public WechatUser getWechatUser(String code){
        try {
            String accessToken = getAccessToken(code); // 获取access_token
            return objectMapper.readValue(accessToken, WechatUser.class); // 将access_token解析为WechatUser对象
        } catch (IOException e) {
            log.warn("token获取失败 {}",code); // 捕获IO异常并记录日志
        }
        return null; // 返回null表示获取微信用户信息失败
    }


}

package com.ssgroup.zelu.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("wechat_user")
public class WechatUser {
    private long username;
    private String openid;
    private String unionid;

//    private String sessionKey;
}

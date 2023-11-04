package com.ssgroup.zelu.pojo;

import lombok.Data;

@Data
public class WechatUser {
    private long username;
    private String openid;
    private String unionID;
    private String session_key;
}

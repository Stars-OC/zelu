package com.ssgroup.zelu.pojo;

import lombok.Data;

@Data
public class WechatUser {
    private String userid;
    private String username;
    private String nickname;
    private String password;
    private String avatar;
    private String unionID;
    private String sex;
    private String phonenumber;
    private long createtime;
    private long lastlogintime;
}

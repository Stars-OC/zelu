package com.ssgroup.zelu.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    private long uid;
    private String username;
    private String nickname;
    private String password;
    private int hasAvatar;
    private int role;
    private int loginWay;
    private long createAt = System.currentTimeMillis();
}

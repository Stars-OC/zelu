package com.ssgroup.zelu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long username;
    private String nickname;
    private String password;
    private int hasAvatar;
    private int role;
    private int registerWay;
    /**
     * 创建时间建议手动创建或者有参构造
     */
    private long createAt;

    public User(Long username, String nickname, String password, int hasAvatar, int role, int registerWay) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.hasAvatar = hasAvatar;
        this.role = role;
        this.registerWay = registerWay;
        this.createAt = System.currentTimeMillis() / 1000;
    }

    public User(Long username, String nickname, String password, int hasAvatar, int role, int registerWay, long createAt) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.hasAvatar = hasAvatar;
        this.role = role;
        this.registerWay = registerWay;
        this.createAt = createAt;
    }
}

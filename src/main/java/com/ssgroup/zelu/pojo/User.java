package com.ssgroup.zelu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssgroup.zelu.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@NoArgsConstructor
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    @NotNull(message = "用户名不能为空")
    private Long username;

    @NotEmpty(message = "昵称不能为空")
    private String nickname;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @URL(message = "头像地址不合法")
    private String avatarUrl;

    private int role;

    @JsonIgnore
    private int registerWay;

//    public void setRegisterWay(LoginWay loginWay) {
//        this.registerWay = loginWay.getCode();
//    }

    /**
     * 创建时间建议手动创建或者有参构造
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private long createAt;

    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public User(Long username, String nickname, String password, String avatarUrl, int role, int registerWay) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.role = role;
        this.createAt = System.currentTimeMillis() / 1000;
    }


    public User(String token){
        Claims claims = JwtUtil.getClaims(token);
        this.username = claims.get("username", Long.class);
        this.nickname = claims.get("nickname", String.class);
        this.avatarUrl = claims.get("avatarUrl", String.class);
        this.createAt = claims.get("createAt", Long.class);
        this.role = claims.get("role", Integer.class);
    }
    public User(Long username, String nickname, String password, String avatarUrl, int role, int registerWay, long createAt) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.role = role;
        this.registerWay = registerWay;
        this.createAt = createAt;
    }
}

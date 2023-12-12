package com.ssgroup.zelu.pojo.user;

import com.baomidou.mybatisplus.annotation.*;
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
    @TableField(updateStrategy = FieldStrategy.NEVER)
    @NotNull(message = "用户名不能为空")
    private Long username;

    @NotEmpty(message = "昵称不能为空")
    private String nickname;

    @NotEmpty(message = "密码不能为空")
    private String password;

    @URL(message = "头像地址不合法")
    private String avatarUrl;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Long deptId;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Integer role;

    @JsonIgnore
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Integer registerWay;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Long createAt;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Integer deleted;

    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public User(String token){
        Claims claims = JwtUtil.getClaims(token);
        this.username = claims.get("username", Long.class);
        this.nickname = claims.get("nickname", String.class);
        this.avatarUrl = claims.get("avatarUrl", String.class);
        this.createAt = claims.get("createAt", Long.class);
        this.role = claims.get("role", Integer.class);
    }
}
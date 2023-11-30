package com.ssgroup.zelu.pojo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UsernameAndPWD {

    @NotNull(message = "用户名不能为空")
    private Long username;

    @NotEmpty(message = "密码不能为空")
//    @Pattern(regexp = "^[a-zA-Z0-9_-]{4,16}$", message = "密码必须是4-16位的字母、数字、下划线或中划线")
    private String password;

}

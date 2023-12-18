package com.ssgroup.zelu.pojo.department;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssgroup.zelu.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("school")
@NoArgsConstructor
public class School {

    @TableId(type = IdType.AUTO)
    private Integer schoolId;

    @NotEmpty
    private String schoolName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT,updateStrategy = FieldStrategy.NEVER)
    private long createAt;

    private String schoolAvatar;

    private String schoolDesc;

    private String schoolAddress;

    @NotEmpty
    private String phone;

    private Integer status;

}

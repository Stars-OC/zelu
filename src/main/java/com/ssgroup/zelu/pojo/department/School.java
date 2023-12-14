package com.ssgroup.zelu.pojo.department;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@TableName("school_info")
public class School {

    @TableId(type = IdType.AUTO)
    private Integer schoolId;

    private String schoolName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private long createAt;

    private String schoolAvatar;

    private String schoolDesc;

    private String schoolAddress;

    private Integer status;

    private Integer deleted;
}

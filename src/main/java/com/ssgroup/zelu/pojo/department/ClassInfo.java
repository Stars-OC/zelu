package com.ssgroup.zelu.pojo.department;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@TableName("class_info")
public class ClassInfo {
    @TableId(type = IdType.AUTO)
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String classId;

    private String className;

    private String classAvatar;

    private String classDesc;

    private Integer status;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Long schoolId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Long createAt;

    private Integer deleted;
}

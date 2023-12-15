package com.ssgroup.zelu.pojo.department;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@TableName("course")

public class Course {
    @TableId(type = IdType.AUTO)
    private String courseId;

    private String courseName;

    private String courseAvatar;

    private String courseDesc;

    private Integer status;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Long schoolId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT,updateStrategy = FieldStrategy.NEVER)
    private Long createAt;
}

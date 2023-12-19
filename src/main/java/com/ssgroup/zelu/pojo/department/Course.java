package com.ssgroup.zelu.pojo.department;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("course")
public class Course {

    @TableId(type = IdType.AUTO)
    private Long courseId;

    @NotEmpty
    private String courseName;

    private String courseAvatar;

    private String courseDesc;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer status;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long schoolId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT,updateStrategy = FieldStrategy.NEVER)
    private Long createAt;

}

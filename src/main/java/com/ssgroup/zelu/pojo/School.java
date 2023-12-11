package com.ssgroup.zelu.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@TableName("school")
public class School {

    @TableId
    private Integer schoolId;

    private String schoolName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private long createAt;

    private String schoolAvatar;

    private String schoolDesc;

    private String schoolAddress;
}

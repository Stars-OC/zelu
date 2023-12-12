package com.ssgroup.zelu.pojo.department;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@TableName("company_info")
public class Company {
    @TableId(type = IdType.AUTO)
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String companyId;

    private String companyName;

    private String companyAvatar;

    private String companyDesc;

    private String companyAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Long createdBy;

    private Integer deleted;

    private Integer status;
}

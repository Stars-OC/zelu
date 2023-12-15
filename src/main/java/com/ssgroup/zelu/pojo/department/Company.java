package com.ssgroup.zelu.pojo.department;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("company")
@NoArgsConstructor
public class Company {
    @TableId(type = IdType.AUTO)
    private String companyId;

    private String companyName;

    private String companyAvatar;

    private String companyDesc;

    private String companyAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT,updateStrategy = FieldStrategy.NEVER)
    private Long createdBy;

    private String phone;

    private Integer status;

}

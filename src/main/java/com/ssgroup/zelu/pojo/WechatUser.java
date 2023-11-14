package com.ssgroup.zelu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.beans.Transient;

@Data
@TableName("wechat_user")
public class WechatUser {
    @TableId(type = IdType.AUTO)
    private Long username;
    private String openid;
    private String unionid;
    @TableField(exist = false)
    private String session_key;
}

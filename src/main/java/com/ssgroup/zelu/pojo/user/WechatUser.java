package com.ssgroup.zelu.pojo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("wechat_user")
public class WechatUser {
    @TableId
    private Long username;
    /**
     * 微信openid
     * 注意此参数并不是驼峰命名
     */
    private String openid;
    /**
     * 微信unionid
     * 注意此参数并不是驼峰命名
     */
    private String unionid;
    @TableField(exist = false)
    private String session_key;
}

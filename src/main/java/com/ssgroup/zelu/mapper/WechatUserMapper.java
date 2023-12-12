package com.ssgroup.zelu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssgroup.zelu.pojo.user.User;
import com.ssgroup.zelu.pojo.user.WechatUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WechatUserMapper extends BaseMapper<WechatUser> {

    /**
     * 通过openId获取用户
     * @param openid 微信openId
     * @return User 用户对象
     */
    User getUserByOpenid(String openid);

}

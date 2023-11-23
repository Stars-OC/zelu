package com.ssgroup.zelu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.pojo.WechatUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WechatUserMapper extends BaseMapper<WechatUser> {

    /**
     * 通过openId获取用户
     * @param openid
     * @return
     */
    User getUserByOpenid(String openid);

//    WechatUser findUserByOpenId(String openid);
}

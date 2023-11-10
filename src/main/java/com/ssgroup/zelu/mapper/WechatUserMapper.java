package com.ssgroup.zelu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssgroup.zelu.pojo.WechatUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface WechatUserMapper extends BaseMapper<WechatUser> {

    /**
     * 根据openId查询用户
     * @param openid
     * @return Long username
     */
    @Select("select username from wechat_user where openid = #{openid}")
    Long findUsernameByOpenId(String openid);
}

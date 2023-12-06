package com.ssgroup.zelu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssgroup.zelu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 更新用户头像
     *
     * @param username 用户名
     * @param uploadUrl 头像地址
     */
    @Update("update user set avatar_url = #{uploadUrl} where username = #{username}")
    void updateAvatar(Long username, String uploadUrl);


//    /** 用来通过@username来获取user对象    */
//    @Select("select username,nickname,password,has_avatar,role,register_way from user where username = #{username} and deleted = false;")
//    User findByUsername(@Param("username") Long username);



}

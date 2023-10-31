package com.ssgroup.zelu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssgroup.zelu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

    User findById(Integer uid);

    /** 用来通过@username来获取user对象    */
    @Select("select uid,username,nickname,password,has_avatar,role,login_way from user where username = #{username} and deleted = false;")
    User findByUsername(@Param("username") String username);
}

package com.ssgroup.zelu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssgroup.zelu.filter.JwtUtil;
import com.ssgroup.zelu.mapper.UserMapper;
import com.ssgroup.zelu.mapper.WechatUserMapper;
import com.ssgroup.zelu.pojo.*;
import com.ssgroup.zelu.utils.AesUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtService jwtService;



    /**
     * 判断给定条件在指定数据表中是否存在
     * @param mapper 数据表名
     * @param type 列名
     * @param value 列值
     * @return 存在返回对应对象，否则返回null
     */
    private Object isExists(String mapper, String type, Object value) {
        switch (mapper) {
            case "user":
                return userMapper.selectOne(new QueryWrapper<User>().eq(type, value));
            default:
                return null;
        }
    }

    /**
     * 根据指定的用户名查找用户
     *
     * @param username 要查找的用户名
     * @return 如果找到匹配的用户，则返回该用户对象；否则返回null
     */
    public User findUsername(Long username){
        return (User) isExists("user","username", username);
    }




    /**
     * 获取用户信息
     * @param token 用户的token
     * @return 返回包含成功标志和用户信息的结果对象
     */
    public Result<User> getUserInfo(String token) {

        User user = new User(token);

        return Result.success("获取成功", user);
    }


    /**
     * 更新用户信息
     *
     * @param user    要更新的用户对象
     * @param token   当前用户的token
     * @return 更新结果及更新后的用户对象
     */
    public Result<String> uploadInfo(User user, String token) {

        User oldUser = new User(token);
        // 设置更新后的用户名为原用户的用户名
        user.setUsername(oldUser.getUsername());
        // 设置更新后的用户角色为原用户的用户角色
        user.setRole(oldUser.getRole());
        // 设置更新后的用户创建时间为原用户的用户创建时间
        user.setCreateAt(oldUser.getCreateAt());
        // 对更新后的用户密码进行加密
        user.setPassword(AesUtil.encrypt(user.getPassword()));
        // 更新用户信息至数据库
        userMapper.updateById(user);
        String jwt = jwtService.createJwt(user);
        // 返回更新成功的提示信息及更新后的用户对象
        return Result.success("更新成功",jwt);
    }

}

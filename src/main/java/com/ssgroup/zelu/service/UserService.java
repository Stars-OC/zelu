package com.ssgroup.zelu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssgroup.zelu.mapper.UserMapper;
import com.ssgroup.zelu.mapper.WechatUserMapper;
import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.pojo.WechatUser;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserService {

    @Resource(name = "userMapper")
    private UserMapper userMapper;

    @Autowired
    private WechatUserMapper wechatUserMapper;

    @Autowired
    private WechatAuth wechatAuth;


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
            case "wechat":
                return wechatUserMapper.selectOne(new QueryWrapper<WechatUser>().eq(type, value));
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
     * 根据openid查询已存在的微信用户
     * @param openid 微信用户openid
     * @return 如果存在该openid的微信用户，则返回该用户对象，否则返回null
     */
    public WechatUser findOpenid(String openid){
        return (WechatUser) isExists("wechat","openid", openid);
    }


    /**
     * 使用微信登录
     * @param code 微信授权码
     * @return 登录成功返回用户对象，否则返回null
     */
    public User loginWechat(String code){

        // 使用授权码换取微信用户信息
        WechatUser wechatUserAuth = wechatAuth.getWechatUser(code);
        if (wechatUserAuth == null) return null;

        // 根据openid查询已存在的微信用户
        WechatUser wechatUser = findOpenid(wechatUserAuth.getOpenid());
        if (wechatUser != null) return (User) isExists("user","username", wechatUser.getUsername());

        // 创建新的用户对象
        User user = getNewUser(wechatUserAuth);

        // 将用户信息插入数据库
        userMapper.insert(user);
        wechatUserMapper.insert(wechatUserAuth);
        return user;
    }





    /**
     * 根据微信用户信息创建新用户
     * @param wechatUser 微信用户信息
     * @return 新用户对象
     */
    public User getNewUser(WechatUser wechatUser){
        User user = new User();
        String nickname = "微信用户" + wechatUser.getOpenid().substring(0, 6);
        user.setNickname(nickname);
        user.setRegisterWay(2);
        String password = UUID.randomUUID().toString().replace("-","").substring(0,8);
        user.setPassword(password);
        return user;
    }


//    public boolean register(User user){
//        if (user == null){
//            return false;
//        }
//
//        if (findUsername(user.getUsername()) != null){
//            return false;
//        }
//
//        return userMapper.insert(user) > 0;
//    }
//    public String login(){
//
//    }
}

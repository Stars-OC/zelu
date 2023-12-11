package com.ssgroup.zelu.service.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssgroup.zelu.mapper.UserMapper;
import com.ssgroup.zelu.mapper.WechatUserMapper;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.type.LoginWay;
import com.ssgroup.zelu.pojo.user.User;
import com.ssgroup.zelu.pojo.UsernameAndPWD;
import com.ssgroup.zelu.pojo.user.WechatUser;
import com.ssgroup.zelu.utils.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtService jwtService;

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
     * 使用微信登录 操作sql
     *
     * @param code 微信授权码
     * @return 登录成功返回用户对象，否则返回null
     */
    @Transactional
    User loginWechatByCode(String code){

        // 使用授权码换取微信用户信息
        WechatUser wechatUserAuth = wechatAuth.getWechatUser(code);
        if (wechatUserAuth == null) return null;

        // 根据openid查询已存在的微信用户
        User user = wechatUserMapper.getUserByOpenid(wechatUserAuth.getOpenid());
//        WechatUser wechatUser = findOpenid(wechatUserAuth.getOpenid());
        if (user != null) return user;

        // 创建新的用户对象
        User newUser = getNewUser(wechatUserAuth);

        // 将用户微信登录信息插入数据库
        wechatUserMapper.insert(wechatUserAuth);

        // 将用户信息插入数据库
        Long username = wechatUserAuth.getUsername();
        newUser.setUsername(username);


        userMapper.insert(newUser);

        return newUser;
    }




    /**
     * 用户登录
     *
     * @param usernameAndPWD 用户名和密码
     * @return 登录成功返回JWT，登录失败返回"账号密码错误"
     */
    public Result<String> login(UsernameAndPWD usernameAndPWD){

        Long username = usernameAndPWD.getUsername();
        // 根据用户名查找用户
        User user = findUsername(username);

        try {
            // 判断密码是否匹配
            String password = AesUtil.encrypt(usernameAndPWD.getPassword());

            if (password.equals(user.getPassword())){

                // 登录成功，返回JWT
                String jwt = jwtService.createJwt(user);

                log.info("用户 {} 登录成功", username);

                return Result.success("登录成功",jwt);
            }
        }catch (NullPointerException e){

            log.warn("用户 {} 登录失败，疑似危险操作", username);
        }

        // 密码不匹配或用户不存在，返回"账号密码错误"
        return Result.failure("账号密码错误");
    }



    /**
     * 根据微信用户信息创建新用户
     *
     * @param wechatUser 微信用户信息
     * @return 新用户对象
     */
    public User getNewUser(WechatUser wechatUser){

        User user = new User();
        String openid = wechatUser.getOpenid();
        String nickname = "微信用户" + openid.substring(0, 6);
        user.setNickname(nickname);
        user.setRegisterWay(LoginWay.WECHAT_LOGIN.getCode());
        String password = openid.substring(0,8);
        user.setPassword(password);
        // 注册时间
        user.setCreateAt(System.currentTimeMillis()/1000);
        return user;
    }


    /**
     * 注册用户
     *
     * @param user 要注册的用户对象
     * @return 注册是否成功
     */
    public String register(User user){

        Long username = user.getUsername();
        if (findUsername(username) == null){

            user.setPassword(AesUtil.encrypt(user.getPassword()));
            user.setRegisterWay(LoginWay.NORMAL_LOGIN.getCode());
            user.setCreateAt(System.currentTimeMillis()/1000);

            userMapper.insert(user);

            String jwt = jwtService.createJwt(user);
            return jwt;
        }

        return null;
    }


    /**
     * 使用微信登录
     *
     * @param code 授权码
     * @return 登录结果
     */
    public Result<String> loginWechat(String code) {
        // 调用服务端的微信登录方法，传入授权码，返回用户信息
        User user = loginWechatByCode(code);
        // 如果用户为空，返回登录失败结果
        if (user == null){
            return Result.failure("登录失败 请重试");
        }
        // 利用用户信息生成JWT token
        String jwt = jwtService.createJwt(user);
        // 返回登录成功结果，包含JWT token
        return Result.success("授权成功",jwt);
    }


}

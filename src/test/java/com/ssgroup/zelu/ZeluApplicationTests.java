package com.ssgroup.zelu;

import com.ssgroup.zelu.config.FileConfiguration;
import com.ssgroup.zelu.filter.JwtUtil;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.ResultCode;
import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.pojo.WechatUser;
import com.ssgroup.zelu.service.AvatarService;
import com.ssgroup.zelu.service.WechatAuth;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.lettuce.core.ScriptOutputType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.FileNotFoundException;
import java.io.OutputStream;

@SpringBootTest
class ZeluApplicationTests {

	@Autowired
	private StringRedisTemplate redis;

	@Autowired
	private AvatarService service;
	@Test
	void contextLoads() throws FileNotFoundException {

	}


}

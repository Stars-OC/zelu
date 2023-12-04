package com.ssgroup.zelu;

import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.pojo.WechatUser;
import com.ssgroup.zelu.service.WechatAuth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class ZeluApplicationTests {

	@Autowired
	private StringRedisTemplate redis;
	@Test
	void contextLoads() {
		System.out.println(redis.opsForValue().get("13"));
	}


}

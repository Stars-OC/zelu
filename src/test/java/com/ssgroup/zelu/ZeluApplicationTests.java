package com.ssgroup.zelu;

import com.ssgroup.zelu.aop.permission.Permission;
import com.ssgroup.zelu.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.FileNotFoundException;

@SpringBootTest
class ZeluApplicationTests {

	@Autowired
	private StringRedisTemplate redis;

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() throws FileNotFoundException {
		Permission annotation = userService.getClass().getAnnotation(Permission.class);

		System.out.println(annotation.value());
	}



}

package com.ssgroup.zelu;

import io.lettuce.core.ScriptOutputType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.FileNotFoundException;
import java.util.UUID;

//@SpringBootTest
class ZeluApplicationTests {

	@Autowired
	private StringRedisTemplate redis;

	@Test
	void contextLoads() throws FileNotFoundException {
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
	}


}

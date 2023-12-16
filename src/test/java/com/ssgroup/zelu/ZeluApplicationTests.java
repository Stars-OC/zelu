package com.ssgroup.zelu;

import com.ssgroup.zelu.annotation.Permission;
import com.ssgroup.zelu.pojo.department.Course;
import com.ssgroup.zelu.pojo.department.School;
import com.ssgroup.zelu.pojo.user.User;
import com.ssgroup.zelu.service.UserService;
import com.ssgroup.zelu.service.manager.SchoolManagerService;
import com.ssgroup.zelu.utils.JwtUtil;
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

	@Autowired
	private SchoolManagerService schoolManagerService;

	@Test
	void contextLoads() throws FileNotFoundException {
test1();

	}

	private void test1() {
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ6ZWx1IiwiaWF0IjoxNzAyNjE5ODgyLCJleHAiOjE3MDI2MzQyODIsInVzZXJuYW1lIjoxMjM0NTYsIm5pY2tuYW1lIjoiMTIzNDU2Iiwicm9sZSI6NSwiY3JlYXRlQXQiOjE3MDI1NDI0NzI4NzQsImRlcHRJZCI6MCwiZGVwdFR5cGUiOjB9.ySvZoqBK2gG2i8jHTwf8JojTMJ1ecHRht5Ye1op8Kd0";
		long start = System.currentTimeMillis();
		Long username = JwtUtil.getUsername(jwt);
		User username1 = userService.findUsername(username);
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println(time);
	}

	private void test2() {
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ6ZWx1IiwiaWF0IjoxNzAyNjE5ODgyLCJleHAiOjE3MDI2MzQyODIsInVzZXJuYW1lIjoxMjM0NTYsIm5pY2tuYW1lIjoiMTIzNDU2Iiwicm9sZSI6NSwiY3JlYXRlQXQiOjE3MDI1NDI0NzI4NzQsImRlcHRJZCI6MCwiZGVwdFR5cGUiOjB9.ySvZoqBK2gG2i8jHTwf8JojTMJ1ecHRht5Ye1op8Kd0";
		long start = System.currentTimeMillis();
		JwtUtil.getClaims(jwt);
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println(time);
	}



}

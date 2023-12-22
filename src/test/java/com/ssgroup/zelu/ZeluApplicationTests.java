package com.ssgroup.zelu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssgroup.zelu.mapper.CourseMapper;
import com.ssgroup.zelu.mapper.UserMapper;
import com.ssgroup.zelu.pojo.request.SchoolAndCourseId;
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
	private UserMapper userMapper;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private SchoolManagerService schoolManagerService;

	@Test
	void contextLoads() throws FileNotFoundException, InterruptedException {
		SchoolAndCourseId schoolAndCourseId = new SchoolAndCourseId();
		schoolAndCourseId.setSchoolId(100000);
		schoolAndCourseId.setCourseId(1);
		Integer a = courseMapper.getCourseRoleToCheck(12345, schoolAndCourseId);
		System.out.println(a);

	}

	private void test1(int i) {
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ6ZWx1IiwiaWF0IjoxNzAyNjE5ODgyLCJleHAiOjE3MDI2MzQyODIsInVzZXJuYW1lIjoxMjM0NTYsIm5pY2tuYW1lIjoiMTIzNDU2Iiwicm9sZSI6NSwiY3JlYXRlQXQiOjE3MDI1NDI0NzI4NzQsImRlcHRJZCI6MCwiZGVwdFR5cGUiOjB9.ySvZoqBK2gG2i8jHTwf8JojTMJ1ecHRht5Ye1op8Kd0"+i;

		long start = System.currentTimeMillis();
		Long username = JwtUtil.getUsername(jwt);
		userMapper.selectById(username);
//		User username1 = userService.findUsername(username);
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println("1:" + time);
	}

	private void test2(int i) {
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ6ZWx1IiwiaWF0IjoxNzAyNzIyNDA3LCJleHAiOjE3MDI3MzY4MDcsInVzZXJuYW1lIjoxMjM0NTYsIm5pY2tuYW1lIjoiMTIzNDU2Iiwicm9sZSI6NSwiY3JlYXRlQXQiOjE3MDI1NDI0NzI4NzQsImRlcHRJZCI6MTAwMDAwfQ.ZZAe_YKflHXDkzw2IG-fo8-hI07wLCvVsZvFnPjkIIs";
		Long username = JwtUtil.getUsername(jwt);
		long start = System.currentTimeMillis();
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("username", username);
		userMapper.selectOne(wrapper);
		long end = System.currentTimeMillis();
		long time = end - start;
		System.out.println("2:" + time);
	}



}

package com.ssgroup.zelu;

import com.ssgroup.zelu.annotation.Permission;
import com.ssgroup.zelu.pojo.department.Course;
import com.ssgroup.zelu.pojo.department.School;
import com.ssgroup.zelu.service.UserService;
import com.ssgroup.zelu.service.manager.SchoolManagerService;
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


		School school = new School();
		school.setSchoolName("test");
		school.setPhone("123456");
		schoolManagerService.addByAdmin(school);
	}



}

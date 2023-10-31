package com.ssgroup.zelu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com/ssgroup/zelu/mapper")
public class ZeluApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeluApplication.class, args);
	}

}

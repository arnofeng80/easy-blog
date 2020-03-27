package com.arno.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.arno.blog.mapper")
public class EasyBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasyBlogApplication.class, args);
	}

}

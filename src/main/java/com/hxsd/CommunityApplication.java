package com.hxsd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.hxsd.mapper")
@EnableCaching
public class CommunityApplication{

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
		System.out.println("项目启动成功....");
	}

}

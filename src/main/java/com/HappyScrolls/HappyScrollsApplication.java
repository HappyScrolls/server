package com.HappyScrolls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class HappyScrollsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappyScrollsApplication.class, args);
	}

}

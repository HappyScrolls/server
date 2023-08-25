package com.HappyScrolls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HappyScrollsApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappyScrollsApplication.class, args);
	}

}

package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
//@Configuration
//@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class})
public class DemonApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemonApplication.class, args);
	}
}

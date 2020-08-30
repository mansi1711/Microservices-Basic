package com.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LoginApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(LoginApplication.class);
		logger.info("LoginApplication: ", "Started Login Service");
		SpringApplication.run(LoginApplication.class, args);
	}

}

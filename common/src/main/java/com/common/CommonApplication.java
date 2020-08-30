package com.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CommonApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(CommonApplication.class);
		logger.info("CommonApplication: ", "Started Common Service");
		SpringApplication.run(CommonApplication.class, args);
	}

}

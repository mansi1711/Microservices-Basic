package com.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class AdminApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(AdminApplication.class);
		logger.info("AdminApplication: ", "Started Admin Service");
		SpringApplication.run(AdminApplication.class, args);
	}

	@Bean(name = "restTemp")
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

package com.customer;

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
public class CustomerApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(CustomerApplication.class);
		logger.info("CustomerApplication: ", "Started Customer Service");
		SpringApplication.run(CustomerApplication.class, args);
	}

	@Bean(name = "restTemp")
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

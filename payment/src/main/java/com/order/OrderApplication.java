package com.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(OrderApplication.class);
		logger.info("OrderApplication: ", "Started Order Service");
		SpringApplication.run(OrderApplication.class, args);
	}

}

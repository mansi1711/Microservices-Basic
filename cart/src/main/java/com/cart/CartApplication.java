package com.cart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CartApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(CartApplication.class);
		logger.info("CartApplication", "Started Cart Service");
		SpringApplication.run(CartApplication.class, args);
	}

}

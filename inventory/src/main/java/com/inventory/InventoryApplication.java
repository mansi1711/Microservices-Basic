package com.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(InventoryApplication.class);
		logger.info("InventoryApplication: ", "Started Inventory Service");
		SpringApplication.run(InventoryApplication.class, args);
	}

}

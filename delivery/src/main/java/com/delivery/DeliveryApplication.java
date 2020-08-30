package com.delivery;

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
public class DeliveryApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(DeliveryApplication.class);
		logger.info("DeliveryApplication: ", "Started Delivery Service");
		SpringApplication.run(DeliveryApplication.class, args);
	}

	@Bean(name = "restTemp")
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}

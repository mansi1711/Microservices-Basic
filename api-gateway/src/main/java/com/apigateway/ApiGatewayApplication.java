package com.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(ApiGatewayApplication.class);
		logger.info("ApiGatewayApplication: ", "Started API Gateway");
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}

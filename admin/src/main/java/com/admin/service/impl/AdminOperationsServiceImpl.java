package com.admin.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.admin.service.AdminOperationsService;
import com.common.entities.Item;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

public class AdminOperationsServiceImpl implements AdminOperationsService {

	Logger logger = LoggerFactory.getLogger(AdminOperationsServiceImpl.class);

	private final RabbitTemplate rabbitTemplate;

	private final Exchange exchange;

	@Autowired
	LoadBalancerClient loadBalancerClient;

	@Resource
	private RestTemplate restTemplate;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public AdminOperationsServiceImpl(RabbitTemplate rabbitTemplate, Exchange exchange) {
		this.rabbitTemplate = rabbitTemplate;
		this.exchange = exchange;
	}

	@Override
	public String addItem(Item item) {
		String routingKey = "inventory.add";
		rabbitTemplate.convertAndSend(exchange.getName(), routingKey, item);
		return "Item sent to queue";
	}

	@Override
	@HystrixCommand(fallbackMethod = "defaultDeleteItem")
	public String deleteItem(Item item) {
		String baseUrl = loadBalancerClient.choose("inventory").getUri().toString() + "/inventory/delete";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;

		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
					.queryParam("itemId", item.getItemId()).queryParam("itemName", item.getItemName())
					.queryParam("itemPrice", item.getItemPrice());
			response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.DELETE, null, String.class);
		} catch (Exception ex) {
			logger.info("AdminOperationsServiceImpl: ", ex.getMessage());
			return "Unable to delete item, please try again";
		}
		return response.getBody();
	}

	public String defaultDeleteItem(Item item) {
		return "Server down, please try after some time";
	}

}

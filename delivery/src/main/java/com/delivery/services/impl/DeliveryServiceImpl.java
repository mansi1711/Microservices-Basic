package com.delivery.services.impl;

import java.util.HashSet;
import java.util.Set;

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

import com.common.entities.CartItem;
import com.common.entities.DeliveryDetails;
import com.delivery.services.DeliveryService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

public class DeliveryServiceImpl implements DeliveryService {

	Logger logger = LoggerFactory.getLogger(DeliveryServiceImpl.class);

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

	public DeliveryServiceImpl(RabbitTemplate rabbitTemplate, Exchange exchange) {
		this.rabbitTemplate = rabbitTemplate;
		this.exchange = exchange;
	}

	@Override
	@HystrixCommand(fallbackMethod = "defaultSetDeliveryStatus")
	public String setDeliveryStatus(Integer itemId, String itemName, Double itemPrice, String customerName,
			String deliveryStatus) {
		if ("Undelivered".equals(deliveryStatus)) {
			String baseUrl = loadBalancerClient.choose("order").getUri().toString() + "/order/status";
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<Boolean> response = null;

			try {
				UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl).queryParam("itemId", itemId)
						.queryParam("itemName", itemName).queryParam("itemPrice", itemPrice)
						.queryParam("customerName", customerName).queryParam("deliveryStatus", deliveryStatus);
				response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.PUT, null, Boolean.class);
			} catch (Exception ex) {
				logger.info("DeliveryServiceImpl: ", ex.getMessage());
			}
			if (response.getBody()) {
				return "Delivery status updated";
			} else {
				return "Item not found";
			}
		} else {
			DeliveryDetails details = new DeliveryDetails(itemId, itemName, itemPrice, deliveryStatus, customerName);
			String routingKey = "order.status";
			rabbitTemplate.convertAndSend(exchange.getName(), routingKey, details);
			return "Delivery status update sent to queue";
		}

	}

	public String defaultSetDeliveryStatus(Integer itemId, String itemName, Double itemPrice, String customerName,
			String deliveryStatus) {
		return "Server down, please try after some time";
	}

	@Override
	@HystrixCommand(fallbackMethod = "defaultViewCustomerOrders")
	public Set<CartItem> viewCustomerOrders(String customerName) {
		String baseUrl = loadBalancerClient.choose("order").getUri().toString() + "/order/vieworders";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Set> response = null;

		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl).queryParam("customerName",
					customerName);
			response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, null, Set.class);
		} catch (Exception ex) {
			logger.info("DeliveryServiceImpl: ", ex.getMessage());
		}
		return response.getBody();

	}

	public Set<CartItem> defaultViewCustomerOrders(String customerName) {
		Set<CartItem> itemSet = new HashSet<>();
		CartItem cartItem = new CartItem(0, "Server down, please try after some time", 0.0, 0, "");
		itemSet.add(cartItem);
		return itemSet;
	}

}

package com.customer.services.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.common.entities.Item;
import com.customer.services.CustomerOperationsService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class CustomerOperationsServiceImpl implements CustomerOperationsService {

	Logger logger = LoggerFactory.getLogger(CustomerOperationsServiceImpl.class);

	@Autowired
	LoadBalancerClient loadBalancerClient;

	@Resource
	private RestTemplate restTemplate;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	@HystrixCommand(fallbackMethod = "defaultSearchItems")
	public Set<Item> searchItems(String itemName) {
		String baseUrl = loadBalancerClient.choose("inventory").getUri().toString() + "/inventory/search";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Set> response = null;

		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl).queryParam("itemName", itemName);
			response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, null, Set.class);
		} catch (Exception ex) {
			logger.info("CustomerOperationsServiceImpl: ", ex.getMessage());
		}
		Set<Item> itemSet = (Set<Item>) response.getBody();

		return itemSet;
	}

	public Set<Item> defaultSearchItems(String itemName) {
		Set<Item> itemSet = new HashSet<>();
		Item item = new Item(0, "Server down, please try after some time", 0.0);
		itemSet.add(item);
		return itemSet;
	}

	@Override
	@HystrixCommand(fallbackMethod = "defaultCartOperations")
	public String addToCart(Item item, String customerName) {

		String baseUrl = loadBalancerClient.choose("cart").getUri().toString() + "/cart/add";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;

		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
					.queryParam("itemId", item.getItemId()).queryParam("itemName", item.getItemName())
					.queryParam("itemPrice", item.getItemPrice()).queryParam("customerName", customerName);
			response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.POST, null, String.class);
		} catch (Exception ex) {
			logger.info("CustomerOperationsServiceImpl: ", ex.getMessage());
		}

		return response.getBody();
	}

	@Override
	@HystrixCommand(fallbackMethod = "defaultCartOperations")
	public String removeFromCart(Item item, String customerName) {

		String baseUrl = loadBalancerClient.choose("cart").getUri().toString() + "/cart/remove";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;

		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
					.queryParam("itemId", item.getItemId()).queryParam("itemName", item.getItemName())
					.queryParam("itemPrice", item.getItemPrice()).queryParam("customerName", customerName);
			response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.DELETE, null, String.class);
		} catch (Exception ex) {
			logger.info("CustomerOperationsServiceImpl: ", ex.getMessage());
		}

		return response.getBody();

	}
	
	public String defaultCartOperations(Item item, String customerName) {
		return "Server down, please try after some time";
	}

	@Override
	@HystrixCommand(fallbackMethod = "defaultcheckoutCart")
	public String checkoutCart(String customerName, String paymentStatus) {
		String baseUrl = loadBalancerClient.choose("cart").getUri().toString() + "/cart/checkout";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;

		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
					.queryParam("customerName", customerName).queryParam("paymentStatus", paymentStatus);
			response = restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, null, String.class);
		} catch (Exception ex) {
			logger.info("CustomerOperationsServiceImpl: ", ex.getMessage());
		}

		return response.getBody();
	}
	
	public String defaultcheckoutCart(String customerName, String paymentStatus) {
		return "Server down, please try after some time";
	}


}

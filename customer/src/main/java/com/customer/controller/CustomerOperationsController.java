package com.customer.controller;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.common.entities.Item;
import com.customer.services.CustomerOperationsService;

@RestController
@RequestMapping(value = "/customer")
@EnableCircuitBreaker
public class CustomerOperationsController {

	@Value("${server.port}")
	private int port;

	@Resource
	CustomerOperationsService customerOperationsService;

	@Resource(name = "restTemp")
	private RestTemplate restTemplate;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public Set<Item> searchItem(@RequestBody String itemName) {

		System.out.println("Working from port " + port + " of Customer Service");

		return customerOperationsService.searchItems(itemName);

	}

	@RequestMapping(value = "/addtocart", method = RequestMethod.POST)
	public String addToCart(@RequestBody Item item, @RequestParam(name = "customerName") String customerName) {

		System.out.println("Working from port " + port + " of Customer Service");

		return customerOperationsService.addToCart(item, customerName);

	}

	@RequestMapping(value = "/removefromcart", method = RequestMethod.DELETE)
	public String removeFromCart(@RequestBody Item item, @RequestParam(name = "customerName") String customerName) {

		System.out.println("Working from port " + port + " of Customer Service");

		return customerOperationsService.removeFromCart(item, customerName);

	}

	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	public String checkoutCart(@RequestParam(name = "customerName") String customerName,
			@RequestParam(name = "paymentStatus") String paymentStatus) {

		System.out.println("Working from port " + port + " of Customer Service");

		return customerOperationsService.checkoutCart(customerName, paymentStatus);

	}

}

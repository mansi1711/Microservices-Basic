package com.cart.controllers;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cart.services.CartOperationsService;

@RestController
@RequestMapping(value = "/cart")
@EnableCircuitBreaker
public class CartOperationsController {

	@Value("${server.port}")
	private int port;

	@Resource
	CartOperationsService cartOperationService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addItem(@RequestParam(name = "itemId") Integer itemId,
			@RequestParam(name = "itemName") String itemName, @RequestParam(name = "itemPrice") Double itemPrice,
			@RequestParam(name = "customerName") String customerName) {

		System.out.println("Working from port " + port + " of Cart Service");

		return cartOperationService.addToCart(itemId, itemName, itemPrice, customerName);

	}

	@RequestMapping(value = "/remove", method = RequestMethod.DELETE)
	public String removeItem(@RequestParam(name = "itemId") Integer itemId,
			@RequestParam(name = "itemName") String itemName, @RequestParam(name = "itemPrice") Double itemPrice,
			@RequestParam(name = "customerName") String customerName) {

		System.out.println("Working from port " + port + " of Cart Service");

		return cartOperationService.removeFromCart(itemId, itemName, itemPrice, customerName);

	}

	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	public String checkout(@RequestParam(name = "customerName") String customerName,
			@RequestParam(name = "paymentStatus") String paymentStatus) {

		System.out.println("Working from port " + port + " of Cart Service");

		return cartOperationService.checkout(customerName, paymentStatus);

	}

}

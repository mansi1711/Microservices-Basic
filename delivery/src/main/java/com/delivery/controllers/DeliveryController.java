package com.delivery.controllers;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.common.entities.CartItem;
import com.delivery.services.DeliveryService;

@RestController
@RequestMapping(value = "/delivery")
@EnableCircuitBreaker
public class DeliveryController {

	@Value("${server.port}")
	private int port;

	@Resource
	DeliveryService deliveryService;

	@RequestMapping(value = "/status", method = RequestMethod.PUT)
	public String setDeliveryStatus(@RequestParam(name = "itemId") Integer itemId,
			@RequestParam(name = "itemName") String itemName, @RequestParam(name = "itemPrice") Double itemPrice,
			@RequestParam(name = "customerName") String customerName,
			@RequestParam(name = "deliveryStatus") String deliveryStatus) {

		System.out.println("Working from port " + port + " of Delivery Service");

		return deliveryService.setDeliveryStatus(itemId, itemName, itemPrice, customerName, deliveryStatus);

	}

	@RequestMapping(value = "/vieworders", method = RequestMethod.GET)
	public Set<CartItem> viewCustomerOrders(@RequestParam(name = "customerName") String customerName) {

		System.out.println("Working from port " + port + " of Delivery Service");

		return deliveryService.viewCustomerOrders(customerName);

	}

}

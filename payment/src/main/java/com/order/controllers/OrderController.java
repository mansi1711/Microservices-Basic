package com.order.controllers;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.common.entities.CartItem;
import com.order.services.OrderService;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

	@Value("${server.port}")
	private int port;

	@Resource
	OrderService orderService;

	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String checkout(@RequestParam(name = "customerName") String customerName,
			@RequestParam(name = "paymentStatus") String paymentStatus, @RequestBody Set<CartItem> itemSet) {

		System.out.println("Working from port " + port + " of Order Service");

		return orderService.pay(customerName, paymentStatus, itemSet);

	}

	@RequestMapping(value = "/status", method = RequestMethod.PUT)
	public Boolean setDeliveryStatus(@RequestParam(name = "itemId") Integer itemId,
			@RequestParam(name = "itemName") String itemName, @RequestParam(name = "itemPrice") Double itemPrice,
			@RequestParam(name = "customerName") String customerName,
			@RequestParam(name = "deliveryStatus") String deliveryStatus) {

		System.out.println("Working from port " + port + " of Order Service");

		return orderService.setDeliveryStatus(itemId, itemName, itemPrice, customerName, deliveryStatus);

	}

	@RequestMapping(value = "/vieworders", method = RequestMethod.GET)
	public Set<CartItem> viewCustomerOrders(@RequestParam(name = "customerName") String customerName) {

		System.out.println("Working from port " + port + " of Order Service");

		return orderService.viewCustomerOrders(customerName);

	}

}

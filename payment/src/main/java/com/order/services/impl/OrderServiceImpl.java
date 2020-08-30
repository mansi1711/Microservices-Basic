package com.order.services.impl;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.common.entities.CartItem;
import com.order.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	public final String PATH_TO_ORDER_FILE = "/Users/mansisharma/Documents/workspace/nagp/Microservices/database/order/";

	@Autowired
	LoadBalancerClient loadBalancerClient;

	@Resource
	private RestTemplate restTemplate;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	private boolean itemChanged = false;

	@Override
	public String pay(String customerName, String paymentStatus, Set<CartItem> itemSet) {
		if ("Successful".equals(paymentStatus)) {

			if (this.order(itemSet, customerName)) {
				return "Payment Successful, Order Placed";
			} else {
				return "Order Unsuccessful, initiating refund";
			}
		} else {
			return "Payment Unsuccessful, Unable to place order";
		}
	}

	private boolean order(Set<CartItem> itemSet, String customerName) {
		String filePath = PATH_TO_ORDER_FILE + customerName;
		boolean isOrderSuccessful = true;
		try {

			itemSet.forEach(i -> i.setOrderStatus("Ordered"));

			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(itemSet);
			objectOut.close();

			FileInputStream fis1 = new FileInputStream(filePath);
			ObjectInputStream ois1 = new ObjectInputStream(fis1);
			itemSet = (Set<CartItem>) ois1.readObject();
			ois1.close();

		} catch (FileNotFoundException e) {
			logger.info("OrderServiceImpl: ", "File not found, unable to order");
			isOrderSuccessful = false;
		} catch (EOFException exc) {
			logger.info("OrderServiceImpl: ", "Unable to get items, unable to order");
			isOrderSuccessful = false;
		} catch (Exception ex) {
			logger.info("OrderServiceImpl: ", ex.getMessage());
			isOrderSuccessful = false;
		}

		return isOrderSuccessful;

	}

	@Override
	public boolean setDeliveryStatus(Integer itemId, String itemName, Double itemPrice, String customerName,
			String deliveryStatus) {

		String filePath = PATH_TO_ORDER_FILE + customerName;
		Set<CartItem> itemSet = new HashSet<>();
		try {

			FileInputStream fis = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			itemSet = (Set<CartItem>) ois.readObject();
			ois.close();

		} catch (FileNotFoundException e) {
			logger.info("OrderServiceImpl: ", "File not found, unable to set delivery status");
			return false;
		} catch (Exception ex) {
			logger.info("OrderServiceImpl: ", ex.getMessage());
			return false;
		}

		if (itemSet.isEmpty()) {
			return false;
		} else {

			itemSet.forEach(i -> {
				if (i.getItemId().equals(itemId) && i.getItemName().equals(itemName)
						&& i.getItemPrice().equals(itemPrice)) {
					i.setOrderStatus(deliveryStatus);
					itemChanged = true;
				}
			});
			if (!itemChanged) {
				return false;
			} else {
				itemChanged = false;
			}
		}

		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(itemSet);
			objectOut.close();

		} catch (Exception ex) {
			logger.info("OrderServiceImpl: ", ex.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public Set<CartItem> viewCustomerOrders(String customerName) {
		String filePath = PATH_TO_ORDER_FILE + customerName;
		Set<CartItem> itemSet = new HashSet<>();
		try {

			FileInputStream fis = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			itemSet = (Set<CartItem>) ois.readObject();
			ois.close();

		} catch (FileNotFoundException e) {
			logger.info("OrderServiceImpl: ", "File not found, unable to view order");
		} catch (Exception ex) {
			logger.info("OrderServiceImpl: ", ex.getMessage());
		}
		if (itemSet.isEmpty()) {
			logger.info("OrderServiceImpl: ", "Order not found");
		}
		return itemSet;
	}

}

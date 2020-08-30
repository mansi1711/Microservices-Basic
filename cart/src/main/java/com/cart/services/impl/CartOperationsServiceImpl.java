package com.cart.services.impl;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.cart.CartApplication;
import com.cart.services.CartOperationsService;
import com.common.entities.CartItem;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class CartOperationsServiceImpl implements CartOperationsService {
	
	public final String PATH_TO_CART_FILE = "/Users/mansisharma/Documents/workspace/nagp/Microservices/database/cart/";

	Logger logger = LoggerFactory.getLogger(CartOperationsServiceImpl.class);

	@Autowired
	LoadBalancerClient loadBalancerClient;

	@Resource
	private RestTemplate restTemplate;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	boolean itemChanged = false;
	boolean isFallbackMethodCalled = false;

	@Override
	public String addToCart(Integer itemId, String itemName, Double itemPrice, String customerName) {
		CartItem cartItem = new CartItem(itemId, itemName, itemPrice, 1, "In Cart");
		if (this.isItemOutOfStock(itemId, itemName, itemPrice)) {
			if (isFallbackMethodCalled) {
				isFallbackMethodCalled = false;
				return "Server down, please try after some time";
			} else {
				return "Item Out of Stock";
			}
		}

		String filePath = PATH_TO_CART_FILE + customerName;
		Set<CartItem> itemSet = new HashSet<>();
		try {

			FileInputStream fis = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			itemSet = (Set<CartItem>) ois.readObject();
			ois.close();

		} catch (FileNotFoundException e) {
			logger.info("CartOperationsServiceImpl: ", "File not found, creating new file");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("CartOperationsServiceImpl: ", ex.getMessage());
		}

		if (itemSet.isEmpty()) {
			itemSet.add(cartItem);
		} else {

			itemSet.forEach(i -> {
				if (i.getItemId().equals(cartItem.getItemId()) && i.getItemName().equals(cartItem.getItemName())
						&& i.getItemPrice().equals(cartItem.getItemPrice())) {
					i.setItemQuantity(i.getItemQuantity() + 1);
					itemChanged = true;
				}
			});
			if (!itemChanged) {
				itemSet.add(cartItem);
			} else {
				itemChanged = false;
			}
		}

		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(itemSet);
			objectOut.close();

			logger.info("CartOperationsServiceImpl: ", "The item  was succesfully added to a cart");

		} catch (Exception ex) {
			logger.info("CartOperationsServiceImpl: ", ex.getMessage());
		}
		return "Item Added to Cart";
	}

	@HystrixCommand(fallbackMethod = "defaultIsItemOutOfStock")
	private boolean isItemOutOfStock(Integer itemId, String itemName, Double itemPrice) {
		String baseUrl = loadBalancerClient.choose("inventory").getUri().toString() + "/inventory/checkoutofstock";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Boolean> response = null;

		try {
			UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl).queryParam("itemId", itemId)
					.queryParam("itemName", itemName).queryParam("itemPrice", itemPrice);
			response = restTemplate.exchange(builder.build().toUri(), HttpMethod.GET, null, Boolean.class);
		} catch (Exception ex) {
			logger.info("CartOperationsServiceImpl: ", ex.getMessage());
		}

		return response.getBody();
	}

	public boolean defaultIsItemOutOfStock(Integer itemId, String itemName, Double itemPrice) {
		isFallbackMethodCalled = true;
		return isFallbackMethodCalled;
	}

	@Override
	public String removeFromCart(Integer itemId, String itemName, Double itemPrice, String customerName) {
		String filePath = PATH_TO_CART_FILE + customerName;
		Set<CartItem> itemSet = new HashSet<>();
		boolean itemFound = false;

		try {

			FileInputStream fis = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			itemSet = (Set<CartItem>) ois.readObject();
			ois.close();

			Iterator<CartItem> iterator = itemSet.iterator();

			while (iterator.hasNext()) {
				CartItem item = iterator.next();
				if (item.getItemId().equals(itemId) && item.getItemName().equals(itemName)
						&& item.getItemPrice().equals(itemPrice)) {
					if (item.getItemQuantity() > 1) {
						item.setItemQuantity(item.getItemQuantity() - 1);
						itemFound = true;
					} else {
						itemSet.remove(item);
						itemFound = true;
					}
				}
			}

			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(itemSet);
			objectOut.close();

			logger.info("CartOperationsServiceImpl: ", "The item  was succesfully deleted from cart");

		} catch (FileNotFoundException e) {
			logger.info("CartOperationsServiceImpl: ", "File not found, unable to delete item");

		} catch (Exception ex) {
			logger.info("CartOperationsServiceImpl: ", ex.getMessage());

		}
		if (itemFound) {
			itemFound = false;
			return "Item removed from cart";
		} else {
			return "Item not found in cart";
		}
	}

	@Override
	@HystrixCommand(fallbackMethod = "defaultCheckout")
	public String checkout(String customerName, String paymentStatus) {

		String filePath = PATH_TO_CART_FILE + customerName;
		Set<CartItem> itemSet = new HashSet<>();

		try {

			FileInputStream fis = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			itemSet = (Set<CartItem>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			logger.info("CartOperationsServiceImpl: ", "File not found, unable to order");
		} catch (EOFException exc) {
			logger.info("CartOperationsServiceImpl: ", "Unable to get items, unable to order");
		} catch (Exception ex) {
			logger.info("CartOperationsServiceImpl: ", ex.getMessage());
		}
		if (!itemSet.isEmpty()) {
			String baseUrl = loadBalancerClient.choose("order").getUri().toString() + "/order/payment";
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = null;

			try {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
						.queryParam("customerName", customerName).queryParam("paymentStatus", paymentStatus);
				response = restTemplate.exchange(builder.build().toUri(), HttpMethod.POST,
						new HttpEntity<Set<CartItem>>(itemSet, headers), String.class);
			} catch (Exception ex) {
				logger.info("CartOperationsServiceImpl: ", ex.getMessage());
			}
			if ("Payment Successful, Order Placed".equals(response.getBody())) {
				this.clearCart(customerName);
			}
			return response.getBody();
		} else {
			return "No item in cart";
		}

	}

	public String defaultCheckout(String customerName, String paymentStatus) {
		return "Server down, please try after some time";
	}

	private void clearCart(String customerName) {
		String filePath = PATH_TO_CART_FILE + customerName;
		Set<CartItem> itemSet = new HashSet<>();

		try {

			FileInputStream fis = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			itemSet = (Set<CartItem>) ois.readObject();
			ois.close();

			itemSet.clear();

			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(itemSet);
			objectOut.close();

			FileInputStream fis1 = new FileInputStream(filePath);
			ObjectInputStream ois1 = new ObjectInputStream(fis1);
			itemSet = (Set<CartItem>) ois1.readObject();
			ois1.close();

		} catch (FileNotFoundException e) {
			logger.info("CartOperationsServiceImpl: ", "File not found, unable to clear");
		} catch (EOFException exc) {
			logger.info("CartOperationsServiceImpl: ", "Unable to get items, unable to clear");
		} catch (Exception ex) {
			logger.info("CartOperationsServiceImpl: ", ex.getMessage());
		}

	}

}

package com.delivery.services;

import java.util.Set;

import com.common.entities.CartItem;

public interface DeliveryService {

	public String setDeliveryStatus(Integer itemId, String itemName, Double itemPrice, String customerName,
			String deliveryStatus);

	public Set<CartItem> viewCustomerOrders(String customerName);

}

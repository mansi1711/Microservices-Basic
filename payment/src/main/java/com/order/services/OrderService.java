package com.order.services;

import java.util.Set;

import com.common.entities.CartItem;

public interface OrderService {

	public String pay(String customerName, String paymentStatus, Set<CartItem> itemSet);

	public boolean setDeliveryStatus(Integer itemId, String itemName, Double itemPrice, String customerName,
			String deliveryStatus);

	public Set<CartItem> viewCustomerOrders(String customerName);

}

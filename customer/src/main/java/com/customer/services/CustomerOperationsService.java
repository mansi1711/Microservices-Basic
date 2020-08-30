package com.customer.services;

import java.util.Set;

import com.common.entities.Item;

public interface CustomerOperationsService {

	public Set<Item> searchItems(String itemName);

	public String addToCart(Item item, String customerName);

	public String removeFromCart(Item item, String customerName);

	public String checkoutCart(String customerName, String paymentStatus);

}

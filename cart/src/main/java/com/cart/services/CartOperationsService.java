package com.cart.services;

public interface CartOperationsService {

	public String addToCart(Integer itemId, String itemName, Double itemPrice, String customerName);

	public String removeFromCart(Integer itemId, String itemName, Double itemPrice, String customerName);

	public String checkout(String customerName, String paymentStatus);

}

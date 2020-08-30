package com.common.entities;

import java.io.Serializable;

public class DeliveryDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer itemId;

	private String itemName;

	private Double itemPrice;

	private String deliveryStatus;

	private String customerName;

	public DeliveryDetails(Integer itemId, String itemName, Double itemPrice, String deliveryStatus,
			String customerName) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.deliveryStatus = deliveryStatus;
		this.customerName = customerName;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}

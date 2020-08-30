package com.common.entities;

import java.io.Serializable;

public class CartItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer itemId;

	private String itemName;

	private Double itemPrice;

	private Integer itemQuantity;

	private String orderStatus;

	public CartItem(Integer itemId, String itemName, Double itemPrice, Integer itemQuantity, String orderStatus) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemQuantity = itemQuantity;
		this.orderStatus = orderStatus;
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

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() == obj.getClass()) {
			CartItem item = (CartItem) obj;
			if (this.itemId.equals(item.itemId) && this.itemName.equals(item.itemName)
					&& this.itemPrice.equals(item.itemPrice) && this.itemQuantity.equals(item.itemQuantity)
					&& this.orderStatus.equals(item.orderStatus)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hashcode = 0;
		hashcode = this.itemId * 20;
		hashcode += this.itemName.hashCode();
		hashcode += this.itemPrice.hashCode();
		return hashcode;
	}
}

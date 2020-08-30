package com.common.entities;

import java.io.Serializable;

public class Item implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer itemId;

	private String itemName;

	private Double itemPrice;

	public Item(Integer itemId, String itemName, Double itemPrice) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
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

	@Override
	public boolean equals(Object obj) {
		if (this.getClass() == obj.getClass()) {
			Item item = (Item) obj;
			if (this.itemId.equals(item.itemId) && this.itemName.equals(item.itemName)
					&& this.itemPrice.equals(item.itemPrice)) {
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

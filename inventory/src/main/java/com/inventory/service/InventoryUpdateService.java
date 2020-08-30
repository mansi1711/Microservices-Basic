package com.inventory.service;

import com.common.entities.Item;

public interface InventoryUpdateService {

	public String addItem(Item item);

	public String deleteItem(Integer itemId, String itemName, Double itemPrice);

}

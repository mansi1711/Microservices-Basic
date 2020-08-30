package com.inventory.service;

import java.util.Set;

import com.common.entities.Item;

public interface InventorySearchService {

	public Set<Item> searchItems(String itemName);

	public Boolean isItemOutOfStock(Item item);

}

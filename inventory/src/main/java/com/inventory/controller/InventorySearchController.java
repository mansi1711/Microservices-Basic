package com.inventory.controller;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.common.entities.Item;
import com.inventory.service.InventorySearchService;

@RestController
@RequestMapping(value = "/inventory")
public class InventorySearchController {

	@Value("${server.port}")
	private int port;

	@Resource
	InventorySearchService inventorySearchService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public Set<Item> searchItem(@RequestParam(name = "itemName") String itemName) {

		System.out.println("Working from port " + port + " of Inventory Service");

		return inventorySearchService.searchItems(itemName);

	}

	@RequestMapping(value = "/checkoutofstock", method = RequestMethod.GET)
	public Boolean isItemOutOfStock(@RequestParam(name = "itemId") Integer itemId,
			@RequestParam(name = "itemName") String itemName, @RequestParam(name = "itemPrice") Double itemPrice) {

		System.out.println("Working from port " + port + " of Inventory Service");

		Item item = new Item(itemId, itemName, itemPrice);
		return inventorySearchService.isItemOutOfStock(item);

	}

}

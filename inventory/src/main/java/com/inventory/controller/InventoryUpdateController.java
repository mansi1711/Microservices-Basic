package com.inventory.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.service.InventoryUpdateService;

@RestController
@RequestMapping(value = "/inventory")
public class InventoryUpdateController {

	@Value("${server.port}")
	private int port;

	@Resource
	InventoryUpdateService inventoryUpdateService;

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public String deleteItem(@RequestParam(name = "itemId") Integer itemId,
			@RequestParam(name = "itemName") String itemName, @RequestParam(name = "itemPrice") Double itemPrice) {

		System.out.println("Working from port " + port + " of Inventory Service");

		return inventoryUpdateService.deleteItem(itemId, itemName, itemPrice);

	}

}

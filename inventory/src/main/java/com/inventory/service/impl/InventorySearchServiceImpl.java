package com.inventory.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.common.entities.Item;
import com.inventory.service.InventorySearchService;

@Service
public class InventorySearchServiceImpl implements InventorySearchService {

	Logger logger = LoggerFactory.getLogger(InventorySearchServiceImpl.class);
	
	public final String PATH_TO_INVENTORY_FILE = "/Users/mansisharma/Documents/workspace/nagp/Microservices/database/inventory/datafile";

	@Override
	public Set<Item> searchItems(String itemName) {
		Set<Item> resultSet = new HashSet<>();

		try {
			String filePath = PATH_TO_INVENTORY_FILE;

			FileInputStream fis = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Set<Item> itemSet = (Set<Item>) ois.readObject();
			ois.close();
			itemSet.forEach(i -> {
				if (i.getItemName().equals(itemName)) {
					resultSet.add(i);
				}
			});

		} catch (FileNotFoundException e) {
			logger.info("InventorySearchServiceImpl: ", "File not found, unable to search item");
		} catch (Exception ex) {
			logger.info("InventorySearchServiceImpl: ", ex.getMessage());
		}
		return resultSet;
	}

	@Override
	public Boolean isItemOutOfStock(Item item) {
		Set<Item> resultSet = new HashSet<>();

		try {
			String filePath = PATH_TO_INVENTORY_FILE;

			FileInputStream fis = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Set<Item> itemSet = (Set<Item>) ois.readObject();
			ois.close();
			itemSet.forEach(i -> {
				if (i.getItemName().equals(item.getItemName()) && i.getItemId().equals(item.getItemId())
						&& i.getItemPrice().equals(item.getItemPrice())) {
					resultSet.add(i);
				}
			});

		} catch (FileNotFoundException e) {
			logger.info("InventorySearchServiceImpl: ", "File not found, unable to find item");
		} catch (Exception ex) {
			logger.info("InventorySearchServiceImpl: ", ex.getMessage());
		}
		if (resultSet.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

}

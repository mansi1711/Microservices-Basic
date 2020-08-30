package com.inventory.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.common.entities.Item;
import com.inventory.service.InventoryUpdateService;

@Service
public class InventoryUpdateServiceImpl implements InventoryUpdateService {

	Logger logger = LoggerFactory.getLogger(InventoryUpdateServiceImpl.class);
	
	public final String PATH_TO_INVENTORY_FILE = "/Users/mansisharma/Documents/workspace/nagp/Microservices/database/inventory/datafile";

	@Override
	public String addItem(Item item) {
		return this.addItemToFile(item);
	}

	@Override
	public String deleteItem(Integer itemId, String itemName, Double itemPrice) {
		Item item = new Item(itemId, itemName, itemPrice);
		return this.deleteItemFromFile(item);
	}

	private String deleteItemFromFile(Item item) {
		String filePath = PATH_TO_INVENTORY_FILE;
		Set<Item> itemSet = new HashSet<>();

		try {

			FileInputStream fis = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			itemSet = (Set<Item>) ois.readObject();
			ois.close();

			itemSet.removeIf(i -> i.getItemId().equals(item.getItemId()));

			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(itemSet);
			objectOut.close();

		} catch (FileNotFoundException e) {
			logger.info("InventoryUpdateServiceImpl: ", "File not found, unable to delete item");
			return "File not found, unable to delete item";
		} catch (Exception ex) {
			logger.info("InventoryUpdateServiceImpl: ", ex.getMessage());
			return "Unable to delete item from file, try again";
		}

		return "Item Deleted from Inventory";
	}

	private String addItemToFile(Item item) {

		String filePath = PATH_TO_INVENTORY_FILE;
		Set<Item> itemSet = new HashSet<>();
		try {

			FileInputStream fis = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			itemSet = (Set<Item>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			logger.info("InventoryUpdateServiceImpl: ", "File not found, creating new file");
		} catch (Exception ex) {
			logger.info("InventoryUpdateServiceImpl: ", ex.getMessage());
		}

		itemSet.add(item);

		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(itemSet);
			objectOut.close();

		} catch (Exception ex) {
			logger.info("InventoryUpdateServiceImpl: ", ex.getMessage());
			return "Unable to add item, try again";
		}

		return "Item Added to Inventory";

	}

}

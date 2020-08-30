package com.inventory.rabbitmq.consumer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.common.entities.Item;
import com.inventory.service.InventoryUpdateService;

public class ConsumeAdminOperations {

	@Resource
	InventoryUpdateService inventoryUpdateService;

	private Logger logger = LoggerFactory.getLogger(ConsumeAdminOperations.class);

	@RabbitListener(queues = "adminServiceQueue")
	public void receive(Item item) {
		String message = inventoryUpdateService.addItem(item);
		logger.info("Received message '{}'", message);
	}

}

package com.order.rabbitmq.consumer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.common.entities.DeliveryDetails;
import com.order.services.OrderService;

public class ConsumeDeliveryOperations {

	@Resource
	OrderService orderService;

	private Logger logger = LoggerFactory.getLogger(ConsumeDeliveryOperations.class);

	@RabbitListener(queues = "deliveryServiceQueue")
	public void receive(DeliveryDetails details) {
		String message;
		if (orderService.setDeliveryStatus(details.getItemId(), details.getItemName(), details.getItemPrice(),
				details.getCustomerName(), details.getDeliveryStatus())) {
			message = "Delivery status updated";
		} else {
			message = "Item not found";
		}
		logger.info("Received message '{}'", message);
	}

}

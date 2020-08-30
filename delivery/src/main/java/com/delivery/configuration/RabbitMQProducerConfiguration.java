package com.delivery.configuration;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.delivery.services.impl.DeliveryServiceImpl;

@Configuration
public class RabbitMQProducerConfiguration {

	@Bean
	public Exchange eventExchange() {
		return new TopicExchange("deliveryExchange");
	}

	@Bean
	public DeliveryServiceImpl adminOperationsServiceImpl(RabbitTemplate rabbitTemplate, Exchange eventExchange) {
		return new DeliveryServiceImpl(rabbitTemplate, eventExchange);
	}

}

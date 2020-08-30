package com.admin.configuration;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.admin.service.impl.AdminOperationsServiceImpl;

@Configuration
public class RabbitMQProducerConfiguration {

	@Bean
	public Exchange eventExchange() {
		return new TopicExchange("eventExchange");
	}

	@Bean
	public AdminOperationsServiceImpl adminOperationsServiceImpl(RabbitTemplate rabbitTemplate,
			Exchange eventExchange) {
		return new AdminOperationsServiceImpl(rabbitTemplate, eventExchange);
	}

}

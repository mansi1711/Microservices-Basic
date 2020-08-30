package com.inventory.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.inventory.rabbitmq.consumer.ConsumeAdminOperations;

@Configuration
public class RabbitMQConsumerConfiguration {

	@Bean
	public Exchange eventExchange() {
		return new TopicExchange("eventExchange");
	}

	@Bean
	public Queue queue() {
		return new Queue("adminServiceQueue");
	}

	@Bean
	public Binding binding(Queue queue, Exchange eventExchange) {
		return BindingBuilder.bind(queue).to(eventExchange).with("inventory.*").noargs();
	}

	@Bean
	public ConsumeAdminOperations eventReceiver() {
		return new ConsumeAdminOperations();
	}

}

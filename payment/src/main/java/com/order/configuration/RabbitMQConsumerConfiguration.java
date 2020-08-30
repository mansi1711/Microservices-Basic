package com.order.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.order.rabbitmq.consumer.ConsumeDeliveryOperations;

@Configuration
public class RabbitMQConsumerConfiguration {

	@Bean
	public Exchange eventExchange() {
		return new TopicExchange("deliveryExchange");
	}

	@Bean
	public Queue queue() {
		return new Queue("deliveryServiceQueue");
	}

	@Bean
	public Binding binding(Queue queue, Exchange eventExchange) {
		return BindingBuilder.bind(queue).to(eventExchange).with("order.*").noargs();
	}

	@Bean
	public ConsumeDeliveryOperations eventReceiver() {
		return new ConsumeDeliveryOperations();
	}

}

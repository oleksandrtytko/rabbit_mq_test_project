package com.pdp.rabbitmq.config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${rabbitmq.queue.income.name}")
	private String incomeQueueName;

	@Value("${rabbitmq.queue.processed.name}")
	private String processedQueueName;

	@Autowired
	private AmqpAdmin amqpAdmin;

	@PostConstruct
	public void createQueueIfNotExist() {
		QueueInformation incomeQueueInfo = amqpAdmin.getQueueInfo(incomeQueueName);
		if (incomeQueueInfo == null) {
			Queue imcomeQueue = new Queue(incomeQueueName, true, false, false);
			amqpAdmin.declareQueue(imcomeQueue);
		}
		QueueInformation processedQueueInfo = amqpAdmin.getQueueInfo(processedQueueName);
		if (processedQueueInfo == null) {
			Queue processedQueue = new Queue(processedQueueName, true, false, false);
			amqpAdmin.declareQueue(processedQueue);
		}
	}
}

package com.pdp.rabbitmq.consumer;

import com.pdp.rabbitmq.model.IncomeMessageData;
import com.pdp.rabbitmq.model.ProcessedMessageData;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@Slf4j
public class ProcessorConsumer {

	@Value("${rabbitmq.queue.processed.name}")
	private String processedQueueName;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RabbitListener(queues = "${rabbitmq.queue.income.name}", concurrency = "1-6")
	public void readMessage(IncomeMessageData incomeData, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
		ProcessedMessageData processedData = ProcessedMessageData.builder()
				.messageCreated(LocalDateTime.now())
				.messageCreated(incomeData.getDate())
				.result(incomeData.getData()
						.values()
						.stream()
						.mapToInt(v -> v)
						.sum())
				.build();
		rabbitTemplate.convertAndSend(processedQueueName, processedData);
		log.info("Processed message " + tag);
		channel.basicAck(tag, false);
	}
}

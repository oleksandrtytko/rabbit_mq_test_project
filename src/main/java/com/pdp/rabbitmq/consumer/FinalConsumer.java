package com.pdp.rabbitmq.consumer;

import com.pdp.rabbitmq.model.ProcessedMessageData;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

@Slf4j
@Component
public class FinalConsumer {

	ArrayBlockingQueue<ProcessedMessageData> data = new ArrayBlockingQueue<>(1000);

	@RabbitListener(queues = "${rabbitmq.queue.processed.name}", concurrency = "1-6")
	public void readMessage(ProcessedMessageData processedData, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
		data.add(processedData);
		channel.basicAck(tag, false);
		log.info("Processed message " + tag);
	}

	@Scheduled(cron = "* * * * * *")
	public void averageCalc() {
		List<ProcessedMessageData> receivedDate = new ArrayList<>();
		if (data.size() > 100) {
			data.drainTo(receivedDate);
			log.info("Average: " + receivedDate.stream().mapToInt(ProcessedMessageData::getResult).average().getAsDouble());
		}

	}
}

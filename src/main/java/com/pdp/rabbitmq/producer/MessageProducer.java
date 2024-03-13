package com.pdp.rabbitmq.producer;

import com.pdp.rabbitmq.model.IncomeMessageData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class MessageProducer {

	@Value("${rabbitmq.queue.income.name}")
	private String queueName;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private final Random random = new Random();

	@Scheduled(cron = "* * * * * *")
	public void generateMessages() {
		//rabbitTemplate.
		for (int i = 0; i < 20; i++) {
			IncomeMessageData incomeMessageData = new IncomeMessageData();
			Map<String, Integer> map = new HashMap<>();
			map.put("Sum1", getRandomNumber(0, 100000));
			map.put("Sum2", getRandomNumber(0, 100000));
			map.put("Sum3", getRandomNumber(0, 100000));
			incomeMessageData.setData(map);
			rabbitTemplate.convertAndSend(queueName, incomeMessageData);
		}
	}

	public int getRandomNumber(int min, int max) {
		return random.nextInt(max - min) + min;
	}
}

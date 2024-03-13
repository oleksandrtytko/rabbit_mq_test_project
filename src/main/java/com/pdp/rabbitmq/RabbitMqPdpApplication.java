package com.pdp.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitMqPdpApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitMqPdpApplication.class, args);
	}

}

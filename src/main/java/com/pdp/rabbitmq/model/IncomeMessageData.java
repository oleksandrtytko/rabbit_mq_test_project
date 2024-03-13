package com.pdp.rabbitmq.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@ToString
public class IncomeMessageData implements Serializable {

	private Map<String, Integer> data;
	private LocalDateTime date = LocalDateTime.now();
}

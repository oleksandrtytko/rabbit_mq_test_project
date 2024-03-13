package com.pdp.rabbitmq.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@Builder
public class ProcessedMessageData implements Serializable {

	private LocalDateTime messageCreated;
	private LocalDateTime messageProcessed;
	private Integer result;
}

package com.kafkatool.demo.service.test;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class KafkaReceiverService {
       
	private static Logger logger = Logger.getLogger(KafkaReceiverService.class);
	
	@KafkaListener(topics = {"kafka-test-5"},group="test-consumer-group")
	public void processMessage(ConsumerRecord<?, ?> record) {
		Optional<?> kafkaMessage = Optional.ofNullable(record.value());
		if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            logger.info("record 记录:" + record);
            logger.info("message 信息:" + message);
        }
		
	}
}

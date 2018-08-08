package com.kafkatool.demo.service.test;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.kafkatool.demo.enums.ResultCodeEnum;
import com.kafkatool.demo.exception.KafKaException;
import com.kafkatool.demo.service.customer.KafkaConsumerByAnnation;

/**
 * 通过KafkaListener来消费信息
 * 
 * @author wd-pc
 *
 */
@Component
public class KafkaConsumerAnnationService implements KafkaConsumerByAnnation {

	private static Logger logger = Logger.getLogger(KafkaConsumerAnnationService.class);

	@Override
	@KafkaListener(topics = { "test10" }, group = "test-consumer-group")
	public void consumerMessageByAnnation(ConsumerRecord<?, ?> record) {
		Optional<?> kafkaMessage = Optional.ofNullable(record.value());
		if (kafkaMessage.isPresent()) {
			Object message = kafkaMessage.get();
			logger.info("record 记录:" + record);
			logger.info("message 信息:" + message);
		} else {
			throw new KafKaException(ResultCodeEnum.ERROR.getCode(), "receive message occur error!");
		}
	}

}

package com.kafkatool.demo.service.test;

import java.util.List;
import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
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
    private int recordSize=0;
	
	@Override
	@KafkaListener(topics = {"test10" }, group = "test-consumer-group", containerFactory = "kafkaListenerContainerFactory")
	public void consumerMessageByAnnation(List<ConsumerRecord> records, Acknowledgment ack) {
		logger.info("record size:"+records.size());
		try {
			for (ConsumerRecord consumerRecord : records) {
				Optional<?> kafkaMessage = Optional.ofNullable(consumerRecord.value());
				if (kafkaMessage.isPresent()) {
					Object message = kafkaMessage.get();
					logger.info("record 记录:" + consumerRecord);
					logger.info("message 信息:" + message);
				} else {
					throw new KafKaException(ResultCodeEnum.ERROR.getCode(), "receive message occur error!");
				}
			}
		} catch (Exception e) {
			logger.error("consumer message failed", e);
		} finally{
			ack.acknowledge();
		}

	}

}

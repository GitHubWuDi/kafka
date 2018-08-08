package com.kafkatool.demo.service.test;

import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.kafkatool.demo.service.customer.KafkaConsumerServiceAbs;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2018年8月8日 上午9:24:30
* 类说明 KafKa 手动提交消费偏移量
*/
@Component
public class KafKaConsumerConfigService extends KafkaConsumerServiceAbs {

	private int minCommitSize = 10;  //最小提交数
	private int iCount = 0;
	private static Logger logger = Logger.getLogger(KafKaConsumerConfigService.class);
	
	
	@Override
	public void businessOfConsumer(ConsumerRecords<?, ?> record, KafkaConsumer<String, String> consumer) {
		for (ConsumerRecord<?, ?> consumerRecord : record) {
		    	logger.info(String.format("offset:%s,key:%s,partition:%s", consumerRecord.offset(),consumerRecord.key(), consumerRecord.partition()));
		    	iCount++;
			}
		    if(iCount>=minCommitSize){
		    	//TODO 进行业务处理
		    	consumer.commitAsync(new OffsetCommitCallback() {
					@Override
					public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
						if(exception==null){
							logger.info("comsumer conmmit success");
						}else {
							logger.error("comsumer conmmit failed", exception);
						}
					}
				});
		    	iCount = 0;
		    }
	}
	
	/**
	 *自定义配置消费 
	 */
	public void executeConfigConsumer(String topicName){
		 Properties props = new Properties();
		 props.put("bootstrap.servers", "192.168.118.81:9092");
	     props.put("group.id", "test-consumer-group");
	     props.put("enable.auto.commit", "false");
	     props.put("session.timeout.ms", "30000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		 consumerMessages(topicName, props);
	}
	

}

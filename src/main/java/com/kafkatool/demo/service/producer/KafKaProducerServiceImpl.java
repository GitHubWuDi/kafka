package com.kafkatool.demo.service.producer;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2018年8月7日 下午3:42:58
* 类说明 KafKa发送者实现类
*/
@Service
public class KafKaProducerServiceImpl implements KafKaProducerService  {

	private static Logger logger = Logger.getLogger(KafKaProducerServiceImpl.class);
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Value("${kafka.producer.thread_num}")
	private String thread_num;
	
	@Override
	public void send(String topicName, String data) {
		ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.send(topicName, data);
		listenableFuture.addCallback(
				success -> logger.info("KafkaMessageProducer 发送消息" + "topic:" + topicName + "成功！" + "message:" + data),
				fail -> logger.error("KafkaMessageProducer 发送消息失败！"));
	}

	@Override
	public void send(String topicName, String content, Properties properties) {
		Producer<String, String> producer = new KafkaProducer<>(properties);
		ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topicName, content);
		try {
			producer.send(producerRecord, new Callback() {
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if (exception != null) {
						logger.error("send Message occurs exception", exception);
					}
					if (metadata != null) {
						logger.info(String.format("offset:%s,partition:%s", metadata.offset(), metadata.partition()));
					}
				}
			});
		} catch (Exception e) {
			logger.error("send Message occurs exception", e);
		} finally {
			producer.close();
		}
	}

	@Override
	public void sendDefault(String content) {
		ListenableFuture<SendResult<String, String>> listenableFuture = kafkaTemplate.sendDefault(content);
		listenableFuture.addCallback(success -> logger.info("KafkaMessageProducer 发送消息成功！" + "message:" + content),
				fail -> logger.error("KafkaMessageProducer 发送消息失败！"));
	}

	@Override
	public void multiThreadSend(String topicName, List<String> contents, Properties properties) {
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
		ProducerRecord<String, String> producerRecord = null;
		ExecutorService executor = Executors.newFixedThreadPool(Integer.valueOf(thread_num));
		try {
			for (String content : contents){
				producerRecord = new ProducerRecord<String, String>(topicName, content);
				executor.submit(new KafKaProducerRunnale(producer, producerRecord));
			}
		}catch(Exception e){
			logger.error("send message occurs exception:", e);
		}finally {
			producer.close();
			executor.shutdown();
		}
		
	}

}

package com.kafkatool.demo.service.customer;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2018年8月7日 下午4:18:56
* 类说明  kafka消费机制
*/
public abstract class KafkaConsumerServiceAbs {
     
	private static final Logger logger = Logger.getLogger(KafkaConsumerServiceAbs.class);
	private static Boolean flag = true;
	
	@Value("${kafka.consumer.poll}")
	private long poll;
	  /**
	   * 业务消费实现类
	   * @param record
	   */
	  public abstract void businessOfConsumer(ConsumerRecords<?, ?> record, KafkaConsumer<String, String> consumer);
	
	 /**
	  * 自定义配置取值
	  * @param topicName
	  * @param properties
	  */
	 public void consumerMessages(String topicName,Properties properties){
		 KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
		 String[] topicArr = topicName.split(",");
		 consumer.subscribe(Arrays.asList(topicArr));
		 try{
			 while(flag){
				 ConsumerRecords<String,String> consumerRecords = consumer.poll(poll);
				 businessOfConsumer(consumerRecords,consumer);
			 }
		 }catch(Exception e){
			 logger.error("consumer message occuries error:", e);
		 }finally{
			 consumer.close();
		 }
	 }
     
     
}

package com.kafkatool.demo.service.customer;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.net.SyslogOutputStream;



/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2020年8月4日 下午2:14:05
* 类说明
*/
public class KafkaConsumerDemo {
	
	private static boolean flag = true;
	private static Logger logger = LoggerFactory.getLogger(KafkaConsumerDemo.class);
	
    public static void main(String[] args) {
    	       //创建配置对象
    			Properties props = new Properties();
    			//连接的broker
    			props.put("bootstrap.servers", "192.168.120.103:9092");
//    			//设置确认机制
//    			props.put("acks", "1");
    			//设置消息的key的序列化方式
    			props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    			//设置消息的value的序列化方式
    			props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    			//设置消费者组
    			props.put("group.id", "topic2");
    			
    			//创建消费者对象
    			KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
    			//订阅的主题
    			consumer.subscribe(Arrays.asList("huaweitest"));
    			//kafka中的消息消费是一个不断轮询的过程，消费者需要做的就是重复的pull，pull方法返回订阅主题分区上的一组消息，如果这分区中没有消息，那么pull回来的就是空。
	             long inputTime =new Date().getTime();
    			 long count = 0;
	             System.out.println("开始进入轮询");
	             while(flag) {
	            	//通过消费者对象的poll方法获取数据
	    			//poll的参数控制pull方法的阻塞时间，在消费者的缓冲区里没有可用的数据时会发生阻塞，时间参数的设置取决于应用程序对相应速度的要求，比如需要在多长时间里将控制权交给执行轮询的线程。
	            	ConsumerRecords<String,String> records = consumer.poll(1/100);
	            	for (ConsumerRecord<String, String> consumerRecord : records) {
	            		String value = consumerRecord.value();
	            		//logger.info("value:{}", value);
	            		count++;
	            		logger.info("count:{}", count);
					}
	            	flag = false;
	            	
	            }
	             long outputTime =new Date().getTime();
        		 System.out.println("耗费时间:"+(outputTime-inputTime)/1000+"s"+",count次数："+count);
    }         
	
}

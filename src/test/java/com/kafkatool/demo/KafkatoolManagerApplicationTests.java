package com.kafkatool.demo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.kafkatool.demo.service.manager.KafKaManager;
import com.kafkatool.demo.service.producer.KafKaProducerService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkatoolManagerApplicationTests {
    
	private static Logger logger = Logger.getLogger(KafkatoolManagerApplicationTests.class);
	
	@Autowired
	private KafKaManager kafKaManager;
	@Autowired
	private KafKaProducerService kafKaProducerService;
	
	/**
	 * 创建KafkaTopic
	 */
	@Test
	public void createKafkaTopicTest() {
		String topicName = "kafka-test-6";
		int  partitions = 3;
		int  replication = 1;
		Properties properties = new Properties();
		properties.put("max.message.bytes", "1000012");
		kafKaManager.createTopic(topicName, partitions, replication, properties);
	}
	
	/**
	 * 查询所有的TopicList配置
	 */
	@Test
	public void queryTopicListTest(){
	    Map<String, Properties> map = kafKaManager.queryTopicList();
		Set<String> keySet = map.keySet();
	}

	/**
	 * 获得zookeeper列表
	 */
	@Test
	public void getAllTopicListTest(){
		List<String> list = kafKaManager.getAllTopicList();
		assertEquals(10, list.size());
	}
	
	/**
	 * 查修女某一个topic信息测试配置
	 */
	@Test
	public void queryTopicTest(){
		String topicName = "test10";
		Map<String, Object> map = kafKaManager.queryTopic(topicName);
		logger.info(map);
	}
	
	/**
	 * 修改topic配置信息
	 */
	@Test
	public void modifyTopicInfo(){
		String topicName = "test11";
		Properties properties = new Properties();
		properties.put("max.message.bytes", "1000000");
		kafKaManager.modifyTopicConfig(topicName, properties);
	}
	
	/**
	 * 修改分区测试
	 */
	@Test
	public void modifyTopicPartitionsTest(){
		String topicName = "test10";
		kafKaManager.modifyTopicPartitions(topicName, 8);
	}
	
	/**
	 * 修改分区和副本
	 */
	@Test
	public void modifyTopicPartitionsAndReplicationTest(){
		String topicName = "test10";
		int partition = 1; 
		int replication = 3;
		kafKaManager.modifyTopicPartitionsAndReplication(topicName, partition, replication);
	}
	
	/**
	 *删除topic
	 */
	@Test
	public void deleteTopic(){
		String topicName = "my-stream-processing-application3-KSTREAM-JOINTHIS-0000000004-store-changelog";
		kafKaManager.deleteTopic(topicName);
	}
	
	
//	/**
//	 * 默认配置发送
//	 */
//	@Test
//	public void sendDefaultTest(){
//		String topicName = "test10";
//		String data = getTestContent();
//		List<String> list = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			list.add(data);
//		}
//		Gson gson = new Gson();
//		String json = gson.toJson(list);
//		kafKaProducerService.send(topicName, json);
//	}
//	
//	/**
//	 * 根据配置发送信息
//	 */
//	@Test
//	public void sendSelectionTest(){
//		String testContent = getTestContent();
//		String topicName = "test10";
//		Properties properties = new Properties();
//		properties.put("bootstrap.servers", "192.168.118.81:9092");
//		properties.put("acks", "all");
//		properties.put("retries", 0);
//		properties.put("batch.size", 16384);
//		properties.put("linger.ms", 1);
//		properties.put("buffer.memory", 33554432);
//		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		for (int i = 0; i < 10; i++) {
//			kafKaProducerService.send(topicName, testContent, properties);
//		}
//	}
//
//	/**
//	 * 发送默认配置的信息
//	 */
//	@Test
//	public void sendDefaultTopicTest() {
//		String testContent = getTestContent();
//		kafKaProducerService.sendDefault(testContent);
//	}
//	
//	/**
//	 * 发送大量的信息
//	 */
//	@Test
//	public void sendMultiMessage(){
//		List<String> list = new ArrayList<>();
//		String topicName = "test10";
//		Properties properties = new Properties();
//		properties.put("bootstrap.servers", "192.168.118.81:9092");
//		properties.put("acks", "all");
//		properties.put("retries", 0);
//		properties.put("batch.size", 16384);
//		properties.put("linger.ms", 1);
//		properties.put("buffer.memory", 33554432);
//		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//		for (int i = 0; i < 1000; i++) {
//			String testContent = getTestContent();
//			list.add(testContent);
//		}
//		kafKaProducerService.multiThreadSend(topicName, list, properties);
//	}
//	/**
//	 * 获得测试发送数据内容
//	 * @return
//	 */
//	private String getTestContent() {
//		Gson gson = new Gson();
//		Map<String,Object> map = new HashMap<String,Object>();
//        map.put("id", System.currentTimeMillis());
//        map.put("msg", UUID.randomUUID().toString());
//        map.put("sendTime", new Date());
//        String data = gson.toJson(map);
//        return data;
//	}
	
	
}

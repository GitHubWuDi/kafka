package com.kafkatool.demo;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kafkatool.demo.service.KafKaManager;


@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkatoolManagerApplicationTests {
    
	private static Logger logger = Logger.getLogger(KafkatoolManagerApplicationTests.class);
	
	@Autowired
	private KafKaManager kafKaManager;
	
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
	
	@Test
	public void modifyTopicInfo(){
		String topicName = "test10";
		Properties properties = new Properties();
		properties.put("max.message.bytes", "1000000");
		kafKaManager.modifyTopicConfig(topicName, properties);
	}
	
	
	
}

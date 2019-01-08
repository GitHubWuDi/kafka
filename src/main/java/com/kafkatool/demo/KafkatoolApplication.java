package com.kafkatool.demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.google.gson.Gson;
import com.kafkatool.demo.service.producer.KafKaProducerService;
import com.kafkatool.demo.service.test.KafKaConsumerConfigService;

@EnableScheduling
@SpringBootApplication
public class KafkatoolApplication implements CommandLineRunner {

	@Autowired
	private KafKaProducerService kafKaProducerService;
	@Autowired
	private KafKaConsumerConfigService kafKaConsumerConfigService;
	
	public static void main(String[] args) {
		SpringApplication.run(KafkatoolApplication.class, args);
	}
	
//	@Scheduled(fixedRate=1000*3)
//	public void testKafka(){
//		String topic = "test10";
//		Gson gson = new Gson();
//		Map<String,Object> map = new HashMap<String,Object>();
//        map.put("id", System.currentTimeMillis());
//        map.put("msg", UUID.randomUUID().toString());
//        map.put("sendTime", new Date());
//        String json = gson.toJson(map);
//        kafKaProducerService.send(topic, json);
//	}

	@Override
	public void run(String... args) throws Exception {
//		String topicName = "test10";
//		kafKaConsumerConfigService.executeConfigConsumer(topicName);		
	}
	
	
	
	
}

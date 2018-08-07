package com.kafkatool.demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.kafkatool.demo.service.test.KafkaSenderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkatoolApplicationTests {
     
	@Autowired
	private KafkaSenderService kafkaSenderService;
	
	@Test
	public void testKafkaProducerAndCustomer() {
		String topic = "kafka-test-1";
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String,Object>();
        map.put("id", System.currentTimeMillis());
        map.put("msg", UUID.randomUUID().toString());
        map.put("sendTime", new Date());
        String json = gson.toJson(map);
		while(true){
            try {
            	kafkaSenderService.send(topic, json);
				Thread.sleep(3*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}

}

package com.kafkatool.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableScheduling
@SpringBootApplication
public class KafkatoolApplication {

//	@Autowired
//	private KafkaSenderService kafkaSenderService;
	
	public static void main(String[] args) {
		SpringApplication.run(KafkatoolApplication.class, args);
	}
	
//	@Scheduled(fixedRate=1000*3)
//	public void testKafka(){
//		String topic = "kafka-test-5";
//		Gson gson = new Gson();
//		Map<String,Object> map = new HashMap<String,Object>();
//        map.put("id", System.currentTimeMillis());
//        map.put("msg", UUID.randomUUID().toString());
//        map.put("sendTime", new Date());
//        String json = gson.toJson(map);
//		kafkaSenderService.send(topic, json);
//	}
	
}

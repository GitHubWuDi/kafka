package com.kafkatool.demo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kafkatool.demo.service.test.KafkaSenderService;
import com.kafkatool.demo.util.DateUtil;
import com.kafkatool.demo.util.TimestampTypeAdapter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkatoolApplicationTests {
     
	@Autowired
	private KafkaSenderService kafkaSenderService;
	
	private static Logger logger = Logger.getLogger(KafkatoolApplicationTests.class);
	/**
	 * kafka生产者
	 */
	@Test
	public void testKafkaProducerAndCustomer() {
		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String topic = "flink-kafka";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("related_ips", "10.64.10.27");
		map.put("dst_ips", "127.0.0.1");
		map.put("src_ips", "10.3.3.64");
		map.put("src_ports", "80");
		map.put("dst_ports", "8080");
		while(true){
			String date = DateUtil.format(new Date());
			map.put("trigger_time",date);
			String json = gson.toJson(map);
            try {
            	kafkaSenderService.send(topic, json);
				Thread.sleep(3*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
	
	@Test
	public void testFlinkSql(){
		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String topic = "flink-kafka";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user", "wudi");
		map.put("url", "www.baidu.com");
		while(true){
//			String date = DateUtil.format(new Date());
//			map.put("trigger_time", date);
			String json = gson.toJson(map);
            try {
            	kafkaSenderService.send(topic, json);
				Thread.sleep(3*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	
	}
	
	
	@Test
	public void testKafkaProducer() {
		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String topic = "TaxiRides";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userName", "wudi");
		map.put("url", "/wwww");
		//map.put("userActionTime", new Date().getTime());
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
	
	
	@Test
	public void testRule(){
		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String topic = "IDS";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dstIp", "192.168.118.87");
		map.put("srcIp", "192.168.118.91");
		map.put("attackId", "HTTP_XSS攻击");
		map.put("count", 1000);
		map.put("subject", "HTTP_Webshell TCP_RDP远程桌面登录口令穷举");
		while(true){
//			String date = DateUtil.format(new Date());
//			map.put("trigger_time", date);
			String json = gson.toJson(map);
			kafkaSenderService.send(topic, json);
            try {
				Thread.sleep(3*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	
	}
	
	@Test
	public void testHoneyPotScanner(){
		EventType eventType = new EventType();
		eventType.setCn("Nmap");
		eventType.setEn("Namp scan");
		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String topic = "HoneyPotScan";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("source", "scanner");
		map.put("id", UUID.randomUUID().toString());
		map.put("type", "Tcp-Syn");
		map.put("event_type_display_name", eventType);
		map.put("start_time", new Date().getTime());
		map.put("end_time", new Date().getTime());
		map.put("agent", "displayName");
		map.put("src_ip", "192.168.118.81");
		map.put("src_port", "80");
		map.put("dest_ip", "192.168.118.91");
		map.put("dest_port", "8080");
		while(true){
//			String date = DateUtil.format(new Date());
//			map.put("trigger_time", date);
			String json = gson.toJson(map);
			kafkaSenderService.send(topic, json);
            try {
				Thread.sleep(3*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	
	}
	
	
	@Test
	public void testHoneyPotIntrusion(){
		EventType eventType = new EventType();
		eventType.setCn("Nmap");
		eventType.setEn("Namp scan");
		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String topic = "HoneyPotIntrusion";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("source", "honeypot");
		map.put("id", UUID.randomUUID().toString());
		map.put("type", "Tcp-Syn");
		map.put("event_type_display_name", eventType);
		map.put("time", new Date().getTime());
		map.put("agent", "displayName");
		map.put("src_ip", "192.168.118.81");
		map.put("src_mac", "00:22:33:55:66");
		map.put("src_port", "80");
		map.put("dest_ip", "192.168.118.91");
		map.put("dest_port", "8080");
		map.put("risk_level", "high");
		map.put("conn_id", UUID.randomUUID().toString());
		while(true){
//			String date = DateUtil.format(new Date());
//			map.put("trigger_time", date);
			String json = gson.toJson(map);
			kafkaSenderService.send(topic, json);
            try {
				Thread.sleep(3*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	
	}
	
	
	public class EventType{
    	private String cn;
    	private String en;
		public String getCn() {
			return cn;
		}
		public void setCn(String cn) {
			this.cn = cn;
		}
		public String getEn() {
			return en;
		}
		public void setEn(String en) {
			this.en = en;
		}
    	
    	
    }
	
	

}

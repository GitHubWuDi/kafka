package com.kafkatool.demo.service.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kafkatool.demo.util.TimestampTypeAdapter;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2019年1月4日 下午4:35:07
* 类说明    发送数据类
*/
@Component
public class RuleProducerCommand implements CommandLineRunner {

	@Autowired
	private KafkaSenderService kafkaSenderService;
	
	public static final String TOPIC_CONSUMER_NAME_IDS = "IDS-V1FeatureDetec";
	public static final String TOPIC_CONSUMER_NAME_HoneyPotIntrusion = "HoneyPotIntrusion";
    public static final String TOPIC_CONSUMER_NAME_HoneyPotScan = "HoneyPotScan";
	
	
	@Override
	public void run(String... args) throws Exception {
		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Map<String, Object> getprivilege_escalation_remote_overflow = getprivilege_escalation_remote_overflow();
		Map<String, Object> getserver_control_server_backdoor = getserver_control_server_backdoor();
		Map<String, Object> getcross_injection_webshell_control = getcross_injection_webshell_control();
		Map<String, Object> honeyPotIntrusionMap = getHoneyPotIntrusionMap();
		Map<String, Object> honeyPotScannerMap = getHoneyPotScannerMap();
		Map<String, Object> getcross_injection_webshell_control2 = getcross_injection_webshell_control();
		Map<String, Object> getprivilege_escalation_create_account = getprivilege_escalation_create_account();
		Map<String, Object> getprivilege_escalation_equity = getprivilege_escalation_equity();
		Map<String, Object> getserver_control_server_trojan_control = getserver_control_server_trojan_control();
		Map<String, Object> getviolation_procedure_computer_torjan = getviolation_procedure_computer_torjan();
		Map<String, Object> getviolation_procedure_compuyer_virus = getviolation_procedure_compuyer_virus();
		List<Map<String, Object>> ids_list  = new ArrayList<>();
		ids_list.add(getprivilege_escalation_remote_overflow);
		ids_list.add(getserver_control_server_backdoor);
		ids_list.add(getcross_injection_webshell_control);
		ids_list.add(getcross_injection_webshell_control2);
		ids_list.add(getprivilege_escalation_create_account);
		ids_list.add(getprivilege_escalation_equity);
		ids_list.add(getviolation_procedure_computer_torjan);
		ids_list.add(getviolation_procedure_compuyer_virus);
		ids_list.add(getserver_control_server_trojan_control);
		while(true) {
			for (Map<String, Object> map : ids_list) {
				kafkaSenderService.send(TOPIC_CONSUMER_NAME_IDS, gson.toJson(map));
			}
			kafkaSenderService.send(TOPIC_CONSUMER_NAME_HoneyPotScan, gson.toJson(honeyPotIntrusionMap));
			kafkaSenderService.send(TOPIC_CONSUMER_NAME_HoneyPotScan, gson.toJson(honeyPotScannerMap));
			Thread.sleep(3000);
			
		}
		
		
		
		
		
	}

	public Map<String,Object> getIDSMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dt_version","1111");
		map.put("level",1);
		map.put("id",UUID.randomUUID().toString());
		map.put("type","123");
		map.put("tm",100);
		map.put("src_ip","192.168.118.91");
		map.put("src_port",8080);
		map.put("src_mac","00:dd:22:22");
		map.put("dst_ip","192.168.118.81");
		map.put("dst_port",80);
		map.put("dst_mac","dd:09:08:11");
		map.put("counts",100);
		map.put("protocol","tcp");
		map.put("subject","test");
		map.put("security_id","123");
		map.put("attack_id","100");
		map.put("message","IDS LOG");
		map.put("ajb_producer","AJB");
		map.put("ajb_host","127.0.0.1");
		map.put("vrv_receive_time","2019-12-24:00:00:00");
		map.put("dt","2019-12-24");
		map.put("province","HUBEI");
	    return map;
	}
	
	
	/***
	 * 远程溢出
	 * @return
	 */
	public Map<String,Object> getprivilege_escalation_remote_overflow(){
		Map<String, Object> idsMap = getIDSMap();
		idsMap.put("attack_id", "2003");
		return idsMap;
	}
	
	/***
	 * 远程溢出
	 * @return
	 */
	public Map<String,Object> getserver_control_server_backdoor(){
		Map<String, Object> idsMap = getIDSMap();
		idsMap.put("attack_id", "3003");
		return idsMap;
	}
	
	
	/***
	 * WEBSHELL控制
	 * @return
	 */
	public Map<String,Object> getcross_injection_webshell_control(){
		Map<String, Object> idsMap = getIDSMap();
		idsMap.put("attack_id", "3001");
		return idsMap;
	}
	
	
	/***
	 * 远程溢出
	 * @return
	 */
	public Map<String,Object> getprivilege_escalation_equity(){
		Map<String, Object> idsMap = getIDSMap();
		idsMap.put("attack_id", "2010");
		return idsMap;
	}
	
	
	/***
	 * 远程溢出
	 * @return
	 */
	public Map<String,Object> getpc_control_trojan_control(){
		Map<String, Object> idsMap = getIDSMap();
		idsMap.put("attack_id", "3003");
		return idsMap;
	}
	
	
	/***
	 * 远程溢出
	 * @return
	 */
	public Map<String,Object> getprivilege_escalation_create_account(){
		Map<String, Object> idsMap = getIDSMap();
		idsMap.put("attack_id", "2011");
		return idsMap;
	}
	
	
	/***
	 * 远程溢出
	 * @return
	 */
	public Map<String,Object> getserver_control_server_trojan_control(){
		Map<String, Object> idsMap = getIDSMap();
		idsMap.put("attack_id", "3004");
		return idsMap;
	}
	
	
	/***
	 * 远程溢出
	 * @return
	 */
	public Map<String,Object> getviolation_procedure_computer_torjan(){
		Map<String, Object> idsMap = getIDSMap();
		idsMap.put("attack_id", "7003");
		return idsMap;
	}
	
	
	/***
	 * 远程溢出
	 * @return
	 */
	public Map<String,Object> getviolation_procedure_compuyer_virus(){
		Map<String, Object> idsMap = getIDSMap();
		idsMap.put("attack_id", "7001");
		return idsMap;
	}
	
	/**
	 * 蜜罐扫描事件
	 * @return
	 */
	public Map<String,Object> getHoneyPotScannerMap(){
		//String topic = "HoneyPotScan";
		EventType eventType = new EventType();
		eventType.setCn("Nmap");
		eventType.setEn("Namp scan");
		//Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
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
		return map;
	
	}
	
	/**
	 * 蜜罐入侵事件
	 * @return
	 */
	public Map<String,Object> getHoneyPotIntrusionMap(){
		EventType eventType = new EventType();
		//String topic = "HoneyPotIntrusion";
		eventType.setCn("Nmap");
		eventType.setEn("Namp scan");
		//Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
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
		return map;
	
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

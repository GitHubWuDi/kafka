package com.kafkatool.demo;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.loader.LaunchedURLClassLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kafkatool.demo.model.DeviceAccess;
import com.kafkatool.demo.model.Dimension;
import com.kafkatool.demo.model.DwDevicePrintprotectedlog;
import com.kafkatool.demo.model.DwDeviceProcessmonitorLog;
import com.kafkatool.demo.model.DwDeviceServicechangeLog;
import com.kafkatool.demo.model.HostVrus;
import com.kafkatool.demo.model.IPMACBinding;
import com.kafkatool.demo.model.IdsEvent;
import com.kafkatool.demo.model.IdsEvent2;
import com.kafkatool.demo.model.IpsEvent;
import com.kafkatool.demo.model.ProbeNetflow;
import com.kafkatool.demo.model.RouteEvent;
import com.kafkatool.demo.model.SwitchEvents;
import com.kafkatool.demo.model.SwitchTableVO;
import com.kafkatool.demo.model.WindowsViolence;
import com.kafkatool.demo.model.alarmdeal.WarnResultLogVO;
import com.kafkatool.demo.model.switch1.IfEntry;
import com.kafkatool.demo.model.switch1.IfEntry1;
import com.kafkatool.demo.model.switch1.RunningDetails;
import com.kafkatool.demo.model.switch1.RunningDetails2;
import com.kafkatool.demo.model.switch1.RunningDetailsTest;
import com.kafkatool.demo.model.switch1.SwitchPOJO;
import com.kafkatool.demo.model.switch1.SwitchTestVO;
import com.kafkatool.demo.model.switch1.SwitchTestVO.IfEntryTest;
import com.kafkatool.demo.model.switch1.SwitchVo;
import com.kafkatool.demo.service.test.KafkaSenderService;
import com.kafkatool.demo.util.DateUtil;
import com.kafkatool.demo.util.FileUtil;
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
		String topic = "windows-log-V2";
		String txt2String = FileUtil.txt2String(new File("C:\\Users\\wd-pc\\Documents\\Tencent Files\\504454799\\FileRecv\\widows-log.txt"));
		long count = 0;
		while(true){
			count++;
		WindowsViolence windowsViolence = gson.fromJson(txt2String, WindowsViolence.class);
		Timestamp ts = new Timestamp(new Date().getTime());
		windowsViolence.setEventTime(ts);
		windowsViolence.setEventID("4625");
		windowsViolence.setSeverity("ERROR");
		windowsViolence.setReport_ip("192.168.120.102");
		txt2String = gson.toJson(windowsViolence);
//			map.put("time", DateUtil.format(new Date()));
//			map.put("loginTime", DateUtil.format(new Date()));
			//String json = gson.toJson(map);
            try {
            	kafkaSenderService.send(topic, txt2String);
				Thread.sleep(1*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            System.out.println(count);
		}
	}
	
	@Test
	public void testFlinkSql(){
		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String topic = "test123";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("related_ips", "192.168.120.101");
		map.put("dst_ips", "192.168.120.101");
		map.put("dst_ports", "8080");
		map.put("src_ips", "192.168.120.102");
		map.put("src_ports", "8080");
		int count= 0;
		while(true){
			count++;
			map.put("trigger_time", DateUtil.addMinutes(new Date(), -1).getTime());
//			String date = DateUtil.format(new Date());
//			map.put("trigger_time", date);
			String json = gson.toJson(map);
            try {
            	kafkaSenderService.send(topic, json);
            	System.out.println("count:"+count);
				Thread.sleep(1*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	
	}
	
	/**
	 * 获得随机的目的IP
	 * @return
	 */
	private String getRandomDstIp() {
		String[] dstIpArray = new String[] {"192.168.89.131","192.168.116.81","192.168.34.123"};
		int index = (int) (Math.random() * dstIpArray.length);
		String dstIp = dstIpArray[index];
		return dstIp;
	}
	
	
	private Integer getRandomWeight() {
		Integer[] weightArray = new Integer[] {1,2,3,4};
		int index = (int) (Math.random() * weightArray.length);
		Integer weight  = weightArray[index];
		return weight;
	}
	
	@Test
	public void testAssetRiskSql(){
		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String topic = "alarmdeal-returnflow";
		int count= 0;
		while(true){
			WarnResultLogVO warnResultLogVO = new WarnResultLogVO();
			String randomDstIp = getRandomDstIp();
			Integer randomWeight = getRandomWeight();
			warnResultLogVO.setDstIps(randomDstIp);
			warnResultLogVO.setWeight(randomWeight);
			warnResultLogVO.setTriggerTime(new Date());
			count++;
//			String date = DateUtil.format(new Date());
//			map.put("trigger_time", date);
			String json = gson.toJson(warnResultLogVO);
            try {
            	kafkaSenderService.send(topic, json);
            	System.out.println("count:"+count);
				Thread.sleep(1*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	
	}
	
	
	
	
	@Test
	public void testFindOpenTracingAgentRules() throws MalformedURLException{
		String path = "D:\\code\\vap_gitlap\\vapTest\\target\\vapTest-0.0.1-SNAPSHOT.jar";
		File f = new File(path);
		URL[] urls = new URL[1];
		 urls[0] = f.toURI().toURL();
		 LaunchedURLClassLoader loader = new LaunchedURLClassLoader(urls,
		            ClassLoader.getSystemClassLoader());
		 //loader.
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
	
	
	
	/**
	 * 国网数据（设备事件）
	 */
	@Test
	public void testJoinProducer(){
	long count = 0;
		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String topic = "route"; //安全基线
//		IdsEvent idsEvent = getIdsEvent();
//		IdsEvent idsEvent = getIdsEvent();
		//IdsEvent2 idsEvent2 = getIdsEvent2();
		//SwitchTestVO switchVO = getSwitchVO();
		//DwDevicePrintprotectedlog dwSelflog = getDwSelflog();
		//DwDeviceProcessmonitorLog dwDeviceProcessmonitorLog = getDwDeviceProcessmonitorLog();
		//ProbeNetflow probNetFlow = getProbNetFlow();
		//map.put("userActionTime", new Date().getTime());
		//String json1 = gson.toJson(idsEvent);
		while(true){
			RouteEvent routeEvent = getRouteEvent();
			String json2 = gson.toJson(routeEvent);
			count++;
			kafkaSenderService.send(topic, json2);
			System.out.println("count:"+count);
            try {
				Thread.sleep(1*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}
	
	
	private RouteEvent getRouteEvent() {
		RouteEvent routeEvent = new RouteEvent();
		routeEvent.setId(UUID.randomUUID().toString());
		routeEvent.setIp_add("192.168.118.91");
		routeEvent.setMac("24:34:55:32");
		routeEvent.setRoute_name("路由器");
		//routeEvent.setTriggerTime(new Date().getTime());
		return routeEvent;
	}
	
	/**
	 * 国网数据（设备事件）
	 */
	@Test
	public void testGwProducer(){
	long count = 0;
	boolean flag = true;	
	Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String topic = "ips-event"; //安全基线
//		IdsEvent idsEvent = getIdsEvent();
//		IdsEvent idsEvent = getIdsEvent();
		//IdsEvent2 idsEvent2 = getIdsEvent2();
		//SwitchTestVO switchVO = getSwitchVO();
		//DwDevicePrintprotectedlog dwSelflog = getDwSelflog();
		//DwDeviceProcessmonitorLog dwDeviceProcessmonitorLog = getDwDeviceProcessmonitorLog();
		//ProbeNetflow probNetFlow = getProbNetFlow();
		//map.put("userActionTime", new Date().getTime());
		//String json1 = gson.toJson(idsEvent);
		long preciseDateTime = new Date().getTime();
		while(flag){
			//Dimension dimension = getDimension();
			//SwitchEvents switchEvent = getSwitchEvent();
			//SwitchVo switch1 = getSwitch();
			IpsEvent ipsEvent = getIpsEvent();
//			List<RouteEvent> list = new ArrayList<>();
//		    for (int i = 0; i < 10; i++) {
//		    	RouteEvent routeEvent = getRouteEvent();
//		    	list.add(routeEvent);
//			}
//		    switchEvent.setRouteEvent(list);
		    String json2 = gson.toJson(ipsEvent);
			count++;
			kafkaSenderService.send(topic, json2);
			System.out.println("count:"+count);
            try {
				Thread.sleep(1*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//            if(count>1000){ //大于10s
//            	flag = false;
//            	long afterTime = new Date().getTime();
//            	System.out.println("开始时间:"+DateUtil.format(new Date()));
//            	System.out.println("count:"+count+",消耗时间："+(afterTime-preciseDateTime)/1000+"s");
//            
//            }
		}
	}
	
	
	
	private static Dimension getDimension() {
		Dimension dimension = new Dimension();
		String guid = "00025409a5394382aa423b63a4e7c028";
		dimension.setAssetGuid(guid);
		dimension.setIfNumber("1234567890");
		dimension.setCollectorIp("113.57.190.50");
		return dimension;
	}
	
	
	private static IpsEvent getIpsEvent() {
		IpsEvent ipsEvent = new IpsEvent();
		ipsEvent.setGuid(UUID.randomUUID().toString());
		String disIp = getAssetIp();
		String srcIp = getAssetIp();
		Long reportIpNum = getReportIpNum();
		ipsEvent.setDst_ip(disIp);
		ipsEvent.setSrc_ip(srcIp);
		ipsEvent.setMsg_src("failed");
		ipsEvent.setAction_result("failed");
		ipsEvent.setEvent_name("IPS事件");
		ipsEvent.setDevice_name("设备名称");
		ipsEvent.setReport_ip_num(reportIpNum);
		ipsEvent.setEvent_time(DateUtil.format(new Date()));
		ipsEvent.setVul_attack_type("abc");
		return ipsEvent;
	}
	
	
	private static IdsEvent getIdsEvent(){
		IdsEvent idsEvent = new IdsEvent();
		idsEvent.setSrc_ip("64.97.149.81");
		idsEvent.setDst_ip("64.97.170.193");
		idsEvent.setEvent_name("SQL注入");
		idsEvent.setDevice_name("test1");
		return idsEvent;
	}
	
	
	private static IdsEvent2 getIdsEvent2(){
		IdsEvent2 idsEvent = new IdsEvent2();
		idsEvent.setSrc_ip("64.97.134.65");
		idsEvent.setDst_ip("64.97.134.66");
		idsEvent.setEvent_name("SQL注入");
		return idsEvent;
	}
	
	
	public static String getAssetGuid() {
		String[] assetGuidAray = new String[] {"asset-1","asset-2","asset-3"};
		int count = (int) (Math.random() * assetGuidAray.length);
		return assetGuidAray[count];
	}
	
	public static String getAssetIp() {
		String[] assetIpArray = new String[] {"192.168.118.91","192.168.120.105","192.168.120.119","10.3.3.51","10.4.4.67","10.3.3.68"};
		int count = (int) (Math.random() * assetIpArray.length);
		return assetIpArray[count];
	}
	
	
	
	public static Long getReportIpNum() {
		Long[] assetIpArray = new Long[] {1000L,200L,1123L,5643L};
		int count = (int) (Math.random() * assetIpArray.length);
		return assetIpArray[count];
	}
	
	
	private static SwitchTableVO getSwitchTableVO(){
		SwitchTableVO switchTestVO = new SwitchTableVO();
		String assetGuid = getAssetGuid();
		switchTestVO.setAssetGuid(assetGuid);
		switchTestVO.setIfNumber(UUID.randomUUID().toString());
		switchTestVO.setCollectorIp("64.97.130.1");
		switchTestVO.setTriggerTime(new Date().getTime());
		switchTestVO.setCounts(8);
		switchTestVO.setSpeedRate(1503L);
		List<IfEntry1> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			IfEntry1 ifEntryTest = new IfEntry1();
			ifEntryTest.setIfMtu("150"+i);
			ifEntryTest.setIfDescr("desc"+i);
			list.add(ifEntryTest);
		}
		switchTestVO.setIfEntryList(list);
		
		RunningDetails2 runningDetails2 = new RunningDetails2();
		runningDetails2.setBytesSent("12000");
		runningDetails2.setCurrentThreadCount("100");
		runningDetails2.setCurrentThreadsBusy("345");
		switchTestVO.setRunningDetail(runningDetails2);
		
		
		List<String> portArray = new ArrayList<>();
		portArray.add("1");
		portArray.add("2");
		portArray.add("3");
		switchTestVO.setPortArray(portArray);
		
		
		List<Long> countArray = new ArrayList<>();
		countArray.add(1L);
		countArray.add(2L);
		countArray.add(3L);
		switchTestVO.setCountArray(countArray);
		
		return switchTestVO;
	}
	
	
	
	private SwitchEvents getSwitchEvent() {
		SwitchEvents switchEvents = new SwitchEvents();
		String assetIp = getAssetIp();
		switchEvents.setGuid(UUID.randomUUID().toString());
		switchEvents.setDevice_ip("192.168.118.91");
		switchEvents.setDevice_name("ASUS");
		switchEvents.setEvent_detail("person computer");
		switchEvents.setEvent_level("1");
		switchEvents.setEvent_name("attack switch");
		switchEvents.setEvent_time(DateUtil.format(new Date()));
		switchEvents.setEvent_type("attack");
		switchEvents.setIndate(DateUtil.format(new Date()));
		switchEvents.setLog_type("orginal");
		switchEvents.setMsg_src("msg src");
		switchEvents.setReport_ip("192.168.120.105");
		switchEvents.setReport_ip_num(15000L);
		switchEvents.setReport_msg("report msg");
		switchEvents.setSafety_margin("safty margin");
		switchEvents.setSafety_margin_ip("192.168.120.107");
		switchEvents.setSecurity_level("1");
		switchEvents.setEvent_time(DateUtil.format(new Date()));
		switchEvents.setTriggerTime(new Date().getTime());
		return switchEvents;
		
	}
	

	public static Float getIfMtu() {
		Float[] mtuArray = new Float[] {1300.0f,1400.0f,1500.0f};
		int count = (int) (Math.random() * mtuArray.length);
		return mtuArray[count];
	}
	
	
	private static SwitchVo getSwitch(){
		SwitchVo switchTestVO = new SwitchVo();
		switchTestVO.setGuid(UUID.randomUUID().toString());
		switchTestVO.setAssetGuid(UUID.randomUUID().toString());
		switchTestVO.setIfNumber(10);
		String assetIp = getAssetIp();
		switchTestVO.setCollectorIp(assetIp);
		RunningDetails runningDetailsTest = new RunningDetails();
		runningDetailsTest.setActiveSessions("1000");
		runningDetailsTest.setBytesReceived("1000");
		runningDetailsTest.setBytesSent("10000");
		runningDetailsTest.setStartTime("2019-10-21 23:00:00");
		switchTestVO.setRunningDetails(runningDetailsTest);
		List<IfEntry> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			IfEntry ifEntryTest = new IfEntry();
			Float ifMtu = getIfMtu();
			ifEntryTest.setIfMtu(ifMtu);
			ifEntryTest.setIfSpeed(ifMtu.toString());
			ifEntryTest.setIfDescr("desc");
			ifEntryTest.setIfIndex(String.valueOf(i));
			ifEntryTest.setIfType("switch");
			list.add(ifEntryTest);
		}
		switchTestVO.setIfEntryList(list);
		return switchTestVO;
	}
	
	
	private SwitchTestVO   getSwitchVO(){
		SwitchTestVO switchTestVO = new SwitchTestVO();
		switchTestVO.setAssetGuid(UUID.randomUUID().toString());
		switchTestVO.setIfNumber(UUID.randomUUID().toString());
		RunningDetailsTest runningDetailsTest = new RunningDetailsTest();
		runningDetailsTest.setActiveSessions("1000");
		runningDetailsTest.setBytesReceived("1000");
		runningDetailsTest.setBytesSent("10000");
		runningDetailsTest.setStartTime("2019-10-14 23:00:00");
		switchTestVO.setRunningDetailsTest(runningDetailsTest);
		List<IfEntryTest> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			IfEntryTest ifEntryTest = new IfEntryTest();
			ifEntryTest.setIfMtu("1500.30");
			ifEntryTest.setIfDescr("desc");
			ifEntryTest.setIfIndex("2000");
			ifEntryTest.setIfType("switch");
			list.add(ifEntryTest);
		}
		switchTestVO.setIfEntryList(list);
		return switchTestVO;
	}
	
	
	
	private SwitchPOJO getSwitchPOJO() {
		
		SwitchPOJO switchVo = new SwitchPOJO();
		switchVo.setAssetGuid(UUID.randomUUID().toString());
		switchVo.setInDate(DateUtil.format(new Date()));
		switchVo.setIcmpPing("true");
		switchVo.setCollectorIp("192.168.96.178");
		switchVo.setInstanceId("");
		switchVo.setDataPickerPlugin("snmpSource");
		switchVo.setEvent_Table_Name("e_vaiops_switch");
		switchVo.setRunningTime("139天20小时33分钟36.98秒");
		switchVo.setAvgBusy1("");
		switchVo.setH3cEntityExtMemUsage("");
		switchVo.setIfNumber("38");
		switchVo.setIfMtu(Long.valueOf("1300"));
		switchVo.setIfIndex("1500");
		switchVo.setIfDescr("Vlan1");
		switchVo.setIfType("53");
		return switchVo;
		
		
	}
	
	
	
	private IPMACBinding getDwBehaveor() {
		
		IPMACBinding dwDeviceProcessmonitorLog  = new IPMACBinding();
		dwDeviceProcessmonitorLog.setId("111111");
		dwDeviceProcessmonitorLog.setDev_only_id("11234222");
		dwDeviceProcessmonitorLog.setIp("127.0.0.1");
		dwDeviceProcessmonitorLog.setName("11111");
		//dwDeviceProcessmonitorLog.setSrc_Ip("192.168.120.105");
		dwDeviceProcessmonitorLog.setSrc_port("8080");
		dwDeviceProcessmonitorLog.setDst_IP("192.168.117.120");
		dwDeviceProcessmonitorLog.setDst_port("8081");
		dwDeviceProcessmonitorLog.setRelate_ip("192.168.119.120");
		dwDeviceProcessmonitorLog.setAreaName("海南");
		dwDeviceProcessmonitorLog.setAreaCode("460000000000");
		return dwDeviceProcessmonitorLog;
		
		
	}
	
	
	private DwDeviceServicechangeLog getDwDeviceServicechangeLog() {
		
		DwDeviceServicechangeLog dwDeviceProcessmonitorLog  = new DwDeviceServicechangeLog();
		dwDeviceProcessmonitorLog.setId("111111");
		dwDeviceProcessmonitorLog.setDev_only_id("11234222");
		dwDeviceProcessmonitorLog.setIp("127.0.0.1");
		dwDeviceProcessmonitorLog.setName("11111");
		dwDeviceProcessmonitorLog.setSrc_Ip("192.168.120.105");
		dwDeviceProcessmonitorLog.setSrc_port("8080");
		dwDeviceProcessmonitorLog.setDst_Ip("192.168.117.120");
		dwDeviceProcessmonitorLog.setDst_port("8081");
		dwDeviceProcessmonitorLog.setRelate_ip("192.168.119.120");
		dwDeviceProcessmonitorLog.setAreaName("000000000000");
		dwDeviceProcessmonitorLog.setAreaCode("全国");
		return dwDeviceProcessmonitorLog;
		
		
	}
	
	
public String settingOrgAndArea(){
		
		String[] areaNameArray = {"10.64.0.5","10.64.0.6",
				"10.64.1.5","10.64.2.7",
				"10.64.0.9","10.64.0.123","10.64.0.121","10.64.5.5"};
		int index = (int) (Math.random() * areaNameArray.length);
		return areaNameArray[index];
	}
	
/**
 * 流量探针
 * @return
 */
   private ProbeNetflow getProbNetFlow(){
	   ProbeNetflow probeNetflow = new ProbeNetflow();
	   probeNetflow.setSrc_Ip("0.89.231.95");
	   probeNetflow.setDst_ip("0.89.231.96");
	   probeNetflow.setAll_bytes(Long.valueOf("40000"));
	   probeNetflow.setDownload_bytes(Long.valueOf("4000"));
	   probeNetflow.setMsg_src("流量异常日志");
	return probeNetflow;
   }

	
	
	private DwDeviceProcessmonitorLog getDwDeviceProcessmonitorLog(){
		String string = settingOrgAndArea();
		DwDeviceProcessmonitorLog dwDeviceProcessmonitorLog = new DwDeviceProcessmonitorLog();
		dwDeviceProcessmonitorLog.setId("111111");
		dwDeviceProcessmonitorLog.setDev_only_id("11234222");
		dwDeviceProcessmonitorLog.setIp(string);
		dwDeviceProcessmonitorLog.setName("11111");
		dwDeviceProcessmonitorLog.setSrc_Ip(string);
		dwDeviceProcessmonitorLog.setSrc_port("8080");
		dwDeviceProcessmonitorLog.setDst_Ip("192.168.117.120");
		dwDeviceProcessmonitorLog.setDst_port("8081");
		dwDeviceProcessmonitorLog.setRelate_ip(string);
//		dwDeviceProcessmonitorLog.setAreaCode("450000000000");
//		dwDeviceProcessmonitorLog.setAreaName("广西省");
		dwDeviceProcessmonitorLog.setType(5);
		return dwDeviceProcessmonitorLog;
	}
	

	private DeviceAccess getDeviceAccess() {
		DeviceAccess deviceAccess = new DeviceAccess();
		deviceAccess.setAlarm("alarm");
		deviceAccess.setAreaCode("450000000000");
		deviceAccess.setAreaName("广西省");
		deviceAccess.setDetails("details");
		deviceAccess.setDevid("devid");
		deviceAccess.setDst_IP("127.0.0.1");
		deviceAccess.setDst_port("8080");
		deviceAccess.setEvent_class_id("111111");
		deviceAccess.setEvent_id("0x0101");
		deviceAccess.setRelate_ip("192.168.120.105");
		deviceAccess.setIpaddr("192.168.120.105");
		deviceAccess.setLevel(5);
		deviceAccess.setSrc_Ip("192.168.120.105");
		deviceAccess.setSrc_port("8080");
		deviceAccess.setTime(new Timestamp(System.currentTimeMillis()));
		return deviceAccess;
	}
	
	
	private DwDevicePrintprotectedlog getDwSelflog(){
		DwDevicePrintprotectedlog dwDeviceProcessmonitorLog = new DwDevicePrintprotectedlog();
		dwDeviceProcessmonitorLog.setId("111111");
		dwDeviceProcessmonitorLog.setDev_only_id("11234222");
		dwDeviceProcessmonitorLog.setIp("192.168.120.103");
		dwDeviceProcessmonitorLog.setName("11111");
		//dwDeviceProcessmonitorLog.setSrc_Ip("192.168.120.105");
		dwDeviceProcessmonitorLog.setSrc_port("8080");
		dwDeviceProcessmonitorLog.setDst_Ip("192.168.117.120");
		dwDeviceProcessmonitorLog.setDst_port("8081");
		dwDeviceProcessmonitorLog.setRelate_ip("192.168.119.120");
		dwDeviceProcessmonitorLog.setAreaCode("340000000000");
		dwDeviceProcessmonitorLog.setAreaName("安徽");
		return dwDeviceProcessmonitorLog;
	}
	
	
	public static void main(String[] args) {

//		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//		SwitchVo switchVo = getSwitch();
//		String json = gson.toJson(switchVo);
		for (int i = 0; i < 10; i++) {
			String assetGuid = getAssetGuid();
			System.out.println(assetGuid);			
		}
	
	}

	private static String getJsonMap() {
		Map<String,Object> map = new HashMap<>();
		map.put("related_ips", "10.64.10.27");
		map.put("dst_ips", "127.0.0.1");
		map.put("src_ips", "10.3.3.64");
		map.put("src_ports", "80");
		map.put("dst_ports", "8080");
		map.put("ruleCode", "/safer/failedterminal/virus");
		map.put("resultGuid", UUID.randomUUID().toString());
		Gson gson = new Gson();
		String json = gson.toJson(map);
		return json;
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
		String topic = "IntrusionLog";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("source", "scanner");
		map.put("id", UUID.randomUUID().toString());
		map.put("type", "Tcp-Syn");
		map.put("event_type_display_name", eventType);
		map.put("start_time", new Date().getTime());
		map.put("end_time", new Date().getTime());
		map.put("agent", "displayName");
		map.put("src_ip", "192.168.118.81");
		map.put("relate_ip", "192.168.118.81");
		map.put("src_port", "80");
		map.put("dest_ip", "192.168.118.91");
		map.put("dest_port", "8080");
		while(true){
			String date = DateUtil.format(new Date());
		    map.put("trigger_time", date);
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

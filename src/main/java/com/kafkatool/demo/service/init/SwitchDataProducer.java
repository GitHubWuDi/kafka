package com.kafkatool.demo.service.init;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.kafkatool.demo.model.switch1.IfEntry;
import com.kafkatool.demo.model.switch1.RunningDetails;
import com.kafkatool.demo.model.switch1.SwitchVo;
import com.kafkatool.demo.service.test.KafkaSenderService;
import com.kafkatool.demo.util.DateUtil;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2020年8月14日 上午11:50:35
* 类说明  交换机数据发送
*/
@Component
@Order(value=1)
public class SwitchDataProducer implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(SwitchDataProducer.class);
	
	@Autowired
	private KafkaSenderService kafkaSenderService;
	@Value("${value}")
	private Integer value;
	
	private Gson gson  = new Gson();
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("交换机数据开始模拟发送！");
		new Thread(new SendSwitchDataRunable()).start();
	}
    
	/**
	 * 发送交换机数据
	 * @author wd-pc
	 *
	 */
	class SendSwitchDataRunable implements Runnable{
		@Override
		public void run() {
			String topicName = "SwitchMonitor";
			int count = 0;
			while(true){
				SwitchVo switch1 = getSwitch();
				String json = gson.toJson(switch1);
				kafkaSenderService.send(topicName, json);
				count++;
				if(count%value==0){
					try {
						Thread.sleep(1*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}		
		}
		
	}
	
	
	
	private static SwitchVo getSwitch(){
		SwitchVo switchTestVO = new SwitchVo();
		switchTestVO.setGuid(UUID.randomUUID().toString());
		switchTestVO.setAssetGuid(UUID.randomUUID().toString());
		switchTestVO.setIfNumber(10);
		switchTestVO.setInDate(DateUtil.format(new Date()));
		String assetIp = getAssetIp();
		switchTestVO.setCollectorIp(assetIp);
		RunningDetails runningDetailsTest = new RunningDetails();
		runningDetailsTest.setActiveSessions("1000");
		runningDetailsTest.setBytesReceived("1000");
		runningDetailsTest.setBytesSent("10000");
		runningDetailsTest.setStartTime(DateUtil.format(new Date()));
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
	
	
	public static String getAssetIp() {
		String[] assetIpArray = new String[] {"192.168.118.91","192.168.120.105","192.168.120.119","10.3.3.51","10.4.4.67","10.3.3.68"};
		int count = (int) (Math.random() * assetIpArray.length);
		return assetIpArray[count];
	}
	
	
	public static Float getIfMtu() {
		Float[] mtuArray = new Float[] {1300.0f,1400.0f,1500.0f,1656.0f,1758.0f};
		int count = (int) (Math.random() * mtuArray.length);
		return mtuArray[count];
	}
	
}

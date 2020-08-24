package com.kafkatool.demo.service.init;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.kafkatool.demo.model.IpsEvent;
import com.kafkatool.demo.service.test.KafkaSenderService;
import com.kafkatool.demo.util.DateUtil;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2020年8月14日 上午11:50:35
* 类说明  Ips数据发送
*/
@Component
@Order(value=2)
public class IpsDataProducer implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(IpsDataProducer.class);
	
	@Autowired
	private KafkaSenderService kafkaSenderService;
	@Value("${value}")
	private Integer value;
	
	private Gson gson  = new Gson();
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("IPS数据开始模拟发送！");
		new Thread(new SendIpsDataRunable()).start();
	}
    
	/**
	 * 发送交换机数据
	 * @author wd-pc
	 *
	 */
	class SendIpsDataRunable implements Runnable{
		@Override
		public void run() {
			String topicName = "ips-event"; //安全基线
			int count = 0;
			while(true){
				IpsEvent ipsEvent = getIpsEvent();
				String json = gson.toJson(ipsEvent);
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
	
	public static Long getReportIpNum() {
		Long[] assetIpArray = new Long[] {1000L,200L,1123L,5643L};
		int count = (int) (Math.random() * assetIpArray.length);
		return assetIpArray[count];
	}
	
	
	public static String getAssetIp() {
		String[] assetIpArray = new String[] {"192.168.118.91","192.168.120.105","192.168.120.119","10.3.3.51","10.4.4.67","10.3.3.68"};
		int count = (int) (Math.random() * assetIpArray.length);
		return assetIpArray[count];
	}
	
}

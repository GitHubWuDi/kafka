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
import com.kafkatool.demo.model.IdsEvent2;
import com.kafkatool.demo.service.test.KafkaSenderService;
import com.kafkatool.demo.util.DateUtil;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2020年8月14日 上午11:50:35
* 类说明  Ips数据发送
*/
@Component
@Order(value=3)
public class IdsDataProducer implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(IdsDataProducer.class);
	
	@Autowired
	private KafkaSenderService kafkaSenderService;
	@Value("${value}")
	private Integer value;
	
	private Gson gson  = new Gson();
	
	@Override
	public void run(String... args) throws Exception {
		logger.info("IDS数据开始模拟发送！");
		new Thread(new SendIdsDataRunable()).start();
	}
    
	/**
	 * 发送交换机数据
	 * @author wd-pc
	 *
	 */
	class SendIdsDataRunable implements Runnable{
		@Override
		public void run() {
			String topicName = "ids-event"; //安全基线
			int count = 0;
			while(true){
				IdsEvent2 idsEvent = getIdsEvent();
				String json = gson.toJson(idsEvent);
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
	
	
	
	private static IdsEvent2 getIdsEvent() {
		IdsEvent2 idsEvent = new IdsEvent2();
		idsEvent.setGuid(UUID.randomUUID().toString());
		String disIp = getAssetIp();
		String srcIp = getAssetIp();
		idsEvent.setDst_ip(disIp);
		idsEvent.setSrc_ip(srcIp);
		idsEvent.setMsg_src("failed");
		idsEvent.setStrategy_id(UUID.randomUUID().toString());
		idsEvent.setEvent_name("IDS事件");
		idsEvent.setDevice_name("设备名称");
		idsEvent.setEvent_code(UUID.randomUUID().toString());
		idsEvent.setEvent_time(DateUtil.format(new Date()));
		return idsEvent;
	}
	
	
	public static String getAssetIp() {
		String[] assetIpArray = new String[] {"192.168.118.91","192.168.120.105","192.168.120.119","10.3.3.51","10.4.4.67","10.3.3.68","10.3.3.79","10.3.3.89"};
		int count = (int) (Math.random() * assetIpArray.length);
		return assetIpArray[count];
	}
	
	
	
	
}

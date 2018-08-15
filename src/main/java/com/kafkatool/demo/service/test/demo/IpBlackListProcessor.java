package com.kafkatool.demo.service.test.demo;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

/**
 * @author wudi E-mail:wudi891012@163.com
 * @version 创建时间：2018年8月15日 下午4:31:34 类说明 自定义用于处理黑名单的Processor的具体类 接口恶意访问自动检测
 */
public class IpBlackListProcessor implements Processor<Windowed<String>, Long> {

	@Override
	public void init(ProcessorContext context) {

	}

	@Override
	public void process(Windowed<String> key, Long value) {
		System.out.println("ip:" + key.key() + "被加入到黑名单，请求次数为：" + value);
	}

	@Override
	public void punctuate(long timestamp) {

	}

	@Override
	public void close() {
	}

}

package com.kafkatool.demo.service.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;

/**
 * @author wudi E-mail:wudi891012@163.com
 * @version 创建时间：2018年8月7日 下午2:31:20 类说明 KafKaProducerRunnale(多线程-kafka生产者实现)
 */
public class KafKaProducerRunnale implements Runnable {

	private static final Logger logger = Logger.getLogger(KafKaProducerRunnale.class);
	private KafkaProducer<String, String> producer = null;
	private ProducerRecord<String, String> record = null;

	/**
	 * KafKaProducerRunnale构造函数
	 * 
	 * @param producer
	 * @param record
	 */
	public KafKaProducerRunnale(KafkaProducer<String, String> producer, ProducerRecord<String, String> record) {
		this.producer = producer;
		this.record = record;
	}

	@Override
	public void run() {
		try {
			producer.send(record, new Callback() {
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if (exception != null) {
						logger.error("send Message occurs exception", exception);
					}
					if (metadata != null) {
						logger.info(String.format("offset:%s,partition:%s", metadata.offset(), metadata.partition()));
					}
				}
			});
		} catch (Exception e) {
			logger.error("send Message occurs exception", e);
		}
	}

}

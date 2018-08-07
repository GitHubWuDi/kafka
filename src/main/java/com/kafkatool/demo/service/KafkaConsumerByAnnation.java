package com.kafkatool.demo.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2018年8月7日 下午6:26:39
* 类说明
*/
public interface KafkaConsumerByAnnation {
  /**
  * 通过@KafkaListener进行数据接收
  * 配置为默认配置
  * @param record
  * @return
  */
  public void consumerMessageByAnnation(ConsumerRecord<?, ?> record);
  
}

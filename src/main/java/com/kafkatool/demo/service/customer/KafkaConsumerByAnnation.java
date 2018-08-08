package com.kafkatool.demo.service.customer;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2018年8月7日 下午6:26:39
* 类说明
*/
public interface KafkaConsumerByAnnation {
  /**
  * 通过@KafkaListener进行数据接收
  * 配置为批量消费配置
  * ConsumerRecord<?, ?> record
  * @param record
  * @return
  */
  public void consumerMessageByAnnation(List<ConsumerRecord> records , Acknowledgment ack);
 
}

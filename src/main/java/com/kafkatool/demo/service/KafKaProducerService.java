package com.kafkatool.demo.service;

import java.util.List;
import java.util.Properties;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2018年8月7日 下午3:41:38
* 类说明 :KafKa消息发送类
*/
public interface KafKaProducerService {
    
	 /**
     * 单线程-kafka发送消息(默认配置)
     * @param topicName
     * @param data
     */
    public void send(String topicName,String data);
    
    /**
     * 单线程-kafka发送消息(自定义配置)
     * 必填配置：
     * bootstrap.servers:host/port列表，用于初始化建立和Kafka集群的连接。
     * key.serializer: key的序列化类
     * value.serializer: value的序列化类
     * @param topicName
     * @param content
     * @param properties
     */
    public void send(String topicName,String content,Properties properties);
    
    /**
     * 单线程-kafka发送给默认配置的topic对应的内容
     * @param content
     */
    public void sendDefault(String content);
    
    /**
     * 多线程-kafka-信息发送(用法需要详细研究)
     * @param topicName
     * @param content
     * @param properties
     */
    public void multiThreadSend(String topicName,List<String> contents,Properties properties);
	
}

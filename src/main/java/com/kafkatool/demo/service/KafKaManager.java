package com.kafkatool.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2018年8月6日 下午4:06:36
* 类说明 : kafka底层api接口
*/
public interface KafKaManager {

	/**
	 * 创建主题
	 * @param topicName
	 * @param partitions
	 * @param replication
	 * @param properties(Properties<String,String>)
	 */
	public void createTopic(String topicName, int partitions, int replication,Properties properties);
	
	/**
	 * 获得所有的topicList
	 * @return
	 */
	public List<String> getAllTopicList();
	
	/**
	 * 查询所有的topic信息
	 * @param host_Addr
	 */
	public Map<String,Properties> queryTopicList();
	
	/**
	 * 查询指定topic信息
	 * @param topicName
	 * @return
	 */
	public Map<String, Object> queryTopic(String topicName);
	
	/**
	 * 修改kafka的配置
	 * @param topic
	 * @param properties
	 */
	public void modifyTopicConfig(String topic,Properties properties);
	
	/**
	 * 修改主题分区数(采用默认分区方案，指定分区的总数)
	 * 示例:某主题当前已有一个分区，若希望再为该主题增加两个分区，该参数为3
	 * @param topicName
	 * @param partition
	 */
	public void modifyTopicPartitions(String topicName,int partition);
	
	/**
	 * 修改主题分区数(指定分区方案，指定分区的总数)
	 * 示例:某主题当前已有一个分区，若希望再为该主题增加两个分区，该参数为3
	 * @param topicName
	 * @param partition
	 * @param replicaAssignmentStr
	 */
	public void modifyTopicPartitions(String topicName,int partition,String replicaAssignmentStr);
	
	/**
	 * 分区及副本重配置
	 * @param replication
	 */
	public void modifyTopicPartitionsAndReplication(String topicName,int partition,int replication);
	
	/**
	 * 删除topic
	 * @param topicName
	 */
    public void deleteTopic(String topicName);	
    
   
}

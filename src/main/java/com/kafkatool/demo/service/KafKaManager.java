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
	 * 
	 * @param topic
	 * @param properties
	 */
	public void modifyTopicConfig(String topic,Properties properties);
}

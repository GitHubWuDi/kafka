package com.kafkatool.demo.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.kafka.common.security.JaasUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kafkatool.demo.enums.ResultCodeEnum;
import com.kafkatool.demo.exception.KafKaException;
import com.kafkatool.demo.service.KafKaManager;

import kafka.admin.AdminUtils;
import kafka.admin.BrokerMetadata;
import kafka.server.ConfigType;
import kafka.utils.ZkUtils;
import scala.collection.JavaConversions;
import scala.collection.Seq;

/**
 * @author wudi E-mail:wudi891012@163.com
 * @version 创建时间：2018年8月6日 下午4:08:36 类说明 kafka基础类
 */
@Component
public class KafKaManagerImpl implements KafKaManager {
	private static Logger logger = Logger.getLogger(KafKaManagerImpl.class);

	@Value("${kafka.zookeeper.connection}")
	private String zk_connection;
	@Value("${kafka.zookeeper.session_timeout}")
	private String session_timeout;
	@Value("${kafka.zookeeper.connect_timeout}")
	private String connect_timeout;
	
	

	@Override
	public void createTopic(String topicName, int partitions, int replication, Properties properties) {
		ZkUtils zkUtils = ZkUtils.apply(zk_connection, Integer.valueOf(session_timeout),
				Integer.valueOf(connect_timeout), JaasUtils.isZkSecurityEnabled());
		if (!AdminUtils.topicExists(zkUtils, topicName)) { // 主题不存在则创建
			try {
				AdminUtils.createTopic(zkUtils, topicName, partitions, replication, properties,
						AdminUtils.createTopic$default$6());
				zkUtils.close();
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} else {
			logger.info("kafka topic:" + topicName + "is Exist!!!");
			throw new KafKaException(ResultCodeEnum.ERROR.getCode(), "kafka topic:" + topicName + "is Exist!!!");
		}
	}

	@Override
	public Map<String, Properties> queryTopicList() {
		ZkUtils zkUtils = ZkUtils.apply(zk_connection, Integer.valueOf(session_timeout),
				Integer.valueOf(connect_timeout), JaasUtils.isZkSecurityEnabled());
		Map<String, Properties> mapAsJavaMap = JavaConversions.mapAsJavaMap(AdminUtils.fetchAllTopicConfigs(zkUtils));
		return mapAsJavaMap;
	}

	@Override
	public Map<String, Object> queryTopic(String topicName) {
		ZkUtils zkUtils = ZkUtils.apply(zk_connection, Integer.valueOf(session_timeout),
				Integer.valueOf(connect_timeout), JaasUtils.isZkSecurityEnabled());
		Properties properties = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), topicName);
		Iterator<Entry<Object, Object>> iterator = properties.entrySet().iterator();
		Map<String, Object> map = new HashMap<>();
		while (iterator.hasNext()) {
			Entry<Object, Object> entry = iterator.next();
			String key = entry.getKey().toString();
			Object value = entry.getValue();
			map.put(key, value);
		}
		return map;
	}

	@Override
	public void modifyTopicConfig(String topic, Properties properties) {
		ZkUtils zkUtils = ZkUtils.apply(zk_connection, Integer.valueOf(session_timeout),
				Integer.valueOf(connect_timeout), JaasUtils.isZkSecurityEnabled());
		Properties curProperties = AdminUtils.fetchEntityConfig(zkUtils, ConfigType.Topic(), topic);
		curProperties.putAll(properties);
		try {
			AdminUtils.changeTopicConfig(zkUtils, topic, curProperties);
			zkUtils.close();
		} catch (Exception e) {
			logger.error("error:" + e.getMessage());
		}
		zkUtils.close();
	}

	@Override
	public List<String> getAllTopicList() {
		ZkUtils zkUtils = ZkUtils.apply(zk_connection, Integer.valueOf(session_timeout),
				Integer.valueOf(connect_timeout), JaasUtils.isZkSecurityEnabled());
		List<String> topics = JavaConversions.seqAsJavaList(zkUtils.getAllTopics());
		return topics;
	}

	@Override
	public void modifyTopicPartitions(String topicName, int partition) {
		ZkUtils zkUtils = ZkUtils.apply(zk_connection, Integer.valueOf(session_timeout),
				Integer.valueOf(connect_timeout), JaasUtils.isZkSecurityEnabled());
		AdminUtils.addPartitions(zkUtils, topicName, partition, null, true, AdminUtils.addPartitions$default$6());
	}

	@Override
	public void modifyTopicPartitions(String topicName, int partition, String replicaAssignmentStr) {
		ZkUtils zkUtils = ZkUtils.apply(zk_connection, Integer.valueOf(session_timeout),
				Integer.valueOf(connect_timeout), JaasUtils.isZkSecurityEnabled());
		AdminUtils.addPartitions(zkUtils, topicName, partition, replicaAssignmentStr, true,
				AdminUtils.addPartitions$default$6());
	}

	@Override
	public void modifyTopicPartitionsAndReplication(String topicName, int partition, int replication) {
		ZkUtils zkUtils = ZkUtils.apply(zk_connection, Integer.valueOf(session_timeout),
				Integer.valueOf(connect_timeout), JaasUtils.isZkSecurityEnabled());
		// 获取代理元数据（BrokerMetadata）消息
		Seq<BrokerMetadata> brokerMetadatas = AdminUtils.getBrokerMetadatas(zkUtils,
				AdminUtils.getBrokerMetadatas$default$2(), AdminUtils.getBrokerMetadatas$default$3());
		// 生成分区副本分配的方案
		scala.collection.Map<Object, Seq<Object>> replicaAssgin = AdminUtils.assignReplicasToBrokers(brokerMetadatas,
				partition, replication, AdminUtils.assignReplicasToBrokers$default$4(),
				AdminUtils.assignReplicasToBrokers$default$5());
		// 修改分区副本分配方案
		try {
			AdminUtils.createOrUpdateTopicPartitionAssignmentPathInZK(zkUtils, topicName, replicaAssgin, null, true);
		} catch (KafKaException e) {
			logger.error("修改分区副本失败", e);
		} finally {
			zkUtils.close(); // 释放与ZooKeeper的连接
		}
	}

	@Override
	public void deleteTopic(String topicName) {
		ZkUtils zkUtils = ZkUtils.apply(zk_connection, Integer.valueOf(session_timeout),
				Integer.valueOf(connect_timeout), JaasUtils.isZkSecurityEnabled());
		try {
			AdminUtils.deleteTopic(zkUtils, topicName);
		} catch (KafKaException e) {
			logger.error("topicName:" + topicName + "删除报错", e);
		} finally {
			zkUtils.close();
		}
	}

	

}

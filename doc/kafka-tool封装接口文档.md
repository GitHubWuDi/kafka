Kafka-Client-主题管理
# kafka主题管理
/**
topicName:主题名称
host_addr:主机名称：端口
partitions：分区
replication：副本
**/
public void createTopic(String topicName,String host_addr,int partitions,int replication)
/**
查看所有的主题
**/
public Map<String,Properties> queryTopicList() 
/**
 * 获得所有的topicList
 * @return
     */
public List<String> getAllTopicList();
/**
查看指定的主题
**/
public String  queryTopic(String host_addr,String topicName)
/**
修改主题级别配置
**/
public void modifyTopicConfig(String topic,Properties properties)
    
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
分区副本重配置
**/
public void modifyTopicReplication(String replication) 
 
/**
删除主题
**/
public void deleteTopic(String topicName)    
# Kafka-Client-单线程生产者
/**
 单线程-生产者发送数据(默认配置)
**/
public void send (String topicName,String data)
/**
 单线程-生产者发送数据（选择配置）
**/
public void send(String topicName,Properties properties)
    
  /**
   * 单线程-kafka发送给默认配置的topic对应的内容
   * @param content
 */
 public void sendDefault(String content);    
# Kafka-Client-多线程生产者
多线程Kafka发送消息使用场景：发送数据量比较大同时对消息顺序没有严格要求的情况下
多线程实现的方式：
1. 只实例化一个KafkaProducer对象运行多个线程共享该生产者发送消息。
2. 实例化多个KafKaProducer对象。
结论：由于KafkaProducer为线程安全，多个线程共享一个实例化比各自实例化一个Kafkaproducer在性能上要好很多。
KafKa-Client-消费者
  /**
  * 通过@KafkaListener进行数据接收
  * 配置为默认配置
  * 批量消费
  * @param record
  * @return
  */
  public void consumerMessageByAnnation(List<ConsumerRecord> records , Acknowledgment ack);
/**
      * 自定义配置消费类
      * @param topicName
      * @param properties
      */
   public void consumerMessages(String topicName,Properties properties)

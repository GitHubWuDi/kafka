# kafka生产者API说明
- kafka生产者
(1) 设置KafKa生产者级别配置，有以下三个必须配置的

(2) 根据Properties对象实例化一个KafkaProducer对象
(3) 实例化ProducerRecord对象，每条对象对应一个ProducerRecord对象
(4) 调用KafkaProducer发送消息的方法将ProducerRecord发送到Kafka相应节点。Kafka提供了两个发送消息的方法，即`send(ProducerRecord<String,String>record)`方法和`send(ProducerRecord <String,String> record,Callback callback)`方法。
- 注意：KafkaProducer默认是异步发送消息，会将消息缓存到消息缓冲区中，当消息在消息缓冲区中累计到一定数量后作为一个RecordBatch再发送。
生产者发送消息实质分两个阶段：第一阶段是将消息发送到消息缓冲区；第二阶段是一个Sender线程负责将缓冲区的消息发送到代理，执行真正的I/O操作，
第二阶段是一个Sender线程负责将缓冲区的消息发送到代理，执行真正的I/O操作，而在第一阶段执行完后就返回一个Future对象，根据对Future对象处理方式的不同，KafkaProducer支持两种发送消息方式。
   - 同步方式：通过调用send方法返回的Future对象的get()方法以阻塞式获取执行结果，即等待Sender线程处理的最终结果。
   - 异步方式：两个send方法都返回一个Future<RecordMetadata>对象，即只负责将消息发送到消息缓冲区，并不等待Sender线程处理结果，若希望了解异步方式消息发送成功与否，可以在回调函数中进行相应处理，当消息被Sender线程处理后会回调Callback。
# kafka消费者API说明
- 创建消费者实例
客户端指定消费常用相关配置属性
      `bootstrap.servers= localhost:9092  //配置连接kafka集群地址`
      `group-id=test-consumer-group //配置消费者所属组名，与kafka的consumer配置相同`  
      `enable-auto-commit: true or false //是否自动提交消费偏移量`
      `key-deserializer: org.apache.kafka.common.serialization.StringDeserializer  消息key反序列化的属性`
   `value-deserializer: org.apache.kafka.common.serialization.StringDeserializer 消息value反序列化的属性`
具体详细配置参见：
http://orchome.com/535
- 订阅主题API


- 订阅指定分区
 指定对应的分区进行消费
```java
//指定topic的分区0和分区1进行消费
     String topic = "foo";
     TopicPartition partition0 = new TopicPartition(topic, 0);
     TopicPartition partition1 = new TopicPartition(topic, 1);
     consumer.assign(Arrays.asList(partition0, partition1));
```
- 消费者故障处理
消费者将自动加入到组中。只要持续的调用poll，消费者将一直保持可用，并继续从分配的分区中接收消息。、
此外，消费者向服务器定时发送心跳。 如果消费者崩溃或无法在session.timeout.ms配置的时间内发送心跳，则消费者将被视为死亡，并且其分区将被重新分配。

`max.poll.interval.ms`： 增大poll的间隔，可以为消费者提供更多的时间去处理返回的消息（调用poll(long)返回的消息，通常返回的消息都是一批）。缺点是此值越大将会延迟组重新平衡。
`max.poll.records`：此设置限制每次调用poll返回的消息数，这样可以更容易的预测每次poll间隔要处理的最大值。通过调整此值，可以减少poll间隔，减少重新平衡分组的

- 自动提交偏移量
```java
     Properties props = new Properties();
     props.put("bootstrap.servers", "localhost:9092");
     props.put("group.id", "test");
     props.put("enable.auto.commit", "true");
     props.put("auto.commit.interval.ms", "1000");
     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
     consumer.subscribe(Arrays.asList("foo", "bar"));
     while (true) {
         ConsumerRecords<String, String> records = consumer.poll(100);
         for (ConsumerRecord<String, String> record : records)
             System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
     }


``` 
设置`enable.auto.commit=true`，偏移量由`auto.commit.interval.ms`控制自动提交的频率

- 手动提交偏移量



手动偏移设置
```java
     Properties props = new Properties();
     props.put("bootstrap.servers", "localhost:9092");
     props.put("group.id", "test");
     props.put("enable.auto.commit", "false");
     props.put("auto.commit.interval.ms", "1000");
     props.put("session.timeout.ms", "30000");
     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
     KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
     consumer.subscribe(Arrays.asList("foo", "bar"));
     final int minBatchSize = 200;
     List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
     while (true) {
         ConsumerRecords<String, String> records = consumer.poll(100);
         for (ConsumerRecord<String, String> record : records) {
             buffer.add(record);
         }
         if (buffer.size() >= minBatchSize) {
             insertIntoDb(buffer);
             consumer.commitSync();
             buffer.clear();
         }
     }
```


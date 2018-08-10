package com.kafkatool.demo.service.test;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.apache.log4j.Logger;

import kafka.consumer.ConsumerConfig;


public class KafkaStreamTest {

	private static Logger logger = Logger.getLogger(KafkaStreamTest.class);
	
	private static String filePath = "D:\\tmp\\file\\kafkatest.txt";
	
	
	
	
	public static Properties kafkaStreamConfigMap(){
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-stream-processing-application3");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.118.81:9092");
//		props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "192.168.118.81:2181");
		props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.STATE_DIR_CONFIG,  "D:\\tmp\\file");
//		props.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
//		props.put(StreamsConfig.ROCKSDB_CONFIG_SETTER_CLASS_CONFIG, KafkaStreamsRocksDbConfigSetter.class);
		props.put(ConsumerConfig.AutoOffsetReset(), "earliest");
		props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, "2");
		return props;
	}
	
	
	public static void main(String[] args) {
		Properties properties = kafkaStreamConfigMap();
		//constructKStream(properties);
		//kStreamJoinTest(properties);
		//kTableJoinTest(properties);
		ktableAndkstreamTest(properties);
	}
	
	/**
	 * 	kStream之间的连接操作
	 * test11 test13 只有一个分区
	 *  test12 test14有多个分区
	 * @param properties
	 */
	public static void kStreamJoinTest(Properties properties){
		final Serde<String> stringSerde = Serdes.String();
		KStreamBuilder builder = new KStreamBuilder();
		KStream<String, String> test11Stream = builder.stream(stringSerde, stringSerde, "test12");
		KStream<String, String> test13stream = builder.stream(stringSerde, stringSerde, "test14");
		KStream<String, String> join = test11Stream.join(test13stream, new ValueJoiner<String,String, String>() {
			@Override
			public String apply(String test11Value1, String test13Value2) {
				return "test11:"+test11Value1+",test13:"+test13Value2;
			}
		}, JoinWindows.of(TimeUnit.MINUTES.toMillis(50)),Serdes.String(),Serdes.String(),Serdes.String());
		join.print();
		startKafKaStream(properties, builder);
	}
	
	/**
	 * kTable之间连接测试
	 * @param properties
	 */
	public static void kstreamJoinktableTest(Properties properties) {
		final Serde<String> stringSerde = Serdes.String();
		KStreamBuilder builder = new KStreamBuilder();
		KTable<String, String> table11 = builder.table(stringSerde, stringSerde, "test11", "kTable-tes11");
		KTable<String, String> table13 = builder.table(stringSerde, stringSerde, "test13", "kTable-test13");
		KTable<String, String> kTable = table11.join(table13, new ValueJoiner<String, String, String>() {
			@Override
			public String apply(String table11, String table13) {
				return "test11:"+table11+",test13:"+table13;
			}
		});
		kTable.print();
		startKafKaStream(properties, builder);
	}
	
	/**
	 * kStream与KTable之间join测试
	 * @param properties
	 */
	public static void ktableAndkstreamTest(Properties properties){
		final Serde<String> stringSerde = Serdes.String();
		KStreamBuilder builder = new KStreamBuilder();
		KStream<String, String> stream11 = builder.stream(stringSerde, stringSerde, "test11");
		KTable<String, String> table13 = builder.table(stringSerde, stringSerde, "test13", "kTable-test13");
		KStream<String, String> join = stream11.leftJoin(table13, new ValueJoiner<String, String, String>(){
			@Override
			public String apply(String stream11, String table13) {
				return "test11:"+stream11+",test13:"+table13;
			}
		} , stringSerde, stringSerde);
		join.print();
		startKafKaStream(properties, builder);
	}
	
	/**
	 * 测试Ktable和KStream对应示例
	 * @param properties
	 */
	public  static void constructKStream(Properties properties){
		KStreamBuilder builder = new KStreamBuilder();
	    KStream<String, String> stream = builder.stream("test11");
	   // KTable<String, String> kTable = builder.table(Serdes.String(),Serdes.String(),"test11", "Ktable-test");
	    stream.print();
		startKafKaStream(properties, builder);
	}


	/**
	 * 启动kafka-stream
	 * @param properties
	 * @param builder
	 */
	private static void startKafKaStream(Properties properties, KStreamBuilder builder) {
		KafkaStreams streams = new KafkaStreams(builder, properties);
		streams.start();
		try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		streams.close();
	}
	
	
	
}

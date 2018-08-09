package com.kafkatool.demo.service.test;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.state.RocksDBConfigSetter;
import org.apache.kafka.streams.state.internals.RocksDBStore;
import org.apache.log4j.Logger;
import org.rocksdb.RocksDB;

import com.kafkatool.demo.config.KafkaStreamsRocksDbConfigSetter;

import kafka.consumer.ConsumerConfig;


public class KafkaStreamTest {

	private static Logger logger = Logger.getLogger(KafkaStreamTest.class);
	
	private static String filePath = "D:\\tmp\\file\\kafkatest.txt";
	
	
	public static Properties kafkaStreamConfigMap(){
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-stream-processing-application");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.118.81:9092");
		props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.STATE_DIR_CONFIG,  "D:\\tmp\\file");
		props.put(StreamsConfig.ROCKSDB_CONFIG_SETTER_CLASS_CONFIG, KafkaStreamsRocksDbConfigSetter.class);
		props.put(ConsumerConfig.AutoOffsetReset(), "earliest");
		return props;
	}
	
	
	public static void main(String[] args) {
		Properties properties = kafkaStreamConfigMap();
		constructKStream(properties);
	}
	
	
	public  static void constructKStream(Properties properties){
		KStreamBuilder builder = new KStreamBuilder();
	   //KStream<String, String> stream = builder.stream("test10");
	    KTable<String, String> kTable = builder.table("test10", "Ktable-test");
	    kTable.print();
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

package com.kafkatool.demo.config;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;


public class KafkaStreamConfiguration {

	
	public static Properties kafkaStreamConfigMap(){
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-consumer-group");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.118.81:9092");
		props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		return props;
	}
	
	
	public static void main(String[] args) {
		Properties properties = kafkaStreamConfigMap();
		constructKStream(properties);
	}
	
	
	
	public  static void constructKStream(Properties properties){
		KStreamBuilder builder = new KStreamBuilder();
		KStream<String, String> stream = builder.stream("kafka-test-1");
		stream.print();
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

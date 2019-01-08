package com.kafkatool.demo.service.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Aggregator;
import org.apache.kafka.streams.kstream.Initializer;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kafkatool.demo.service.test.demo.IpBlackListProcessor;

import kafka.consumer.ConsumerConfig;


public class KafkaStreamTest {

	private static Logger logger = Logger.getLogger(KafkaStreamTest.class);
	
	private static String filePath = "d:\\tmp\\file\\kafkatest.txt";
	
	
	
	
	public static Properties kafkaStreamConfigMap(){
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "lijihong-streams");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.118.81:9092");
//		props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "192.168.118.81:2181");
		props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
		props.put(StreamsConfig.STATE_DIR_CONFIG,  "D:\\tmp\\file");
		props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(StreamsConfig.POLL_MS_CONFIG, "10");
//		props.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
//		props.put(StreamsConfig.ROCKSDB_CONFIG_SETTER_CLASS_CONFIG, KafkaStreamsRocksDbConfigSetter.class);
		props.put(ConsumerConfig.AutoOffsetReset(), "earliest");
//		props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, "2");
		
		logger.setLevel(Level.INFO);
		return props;
	}
	
	
	public static void main(String[] args) {
		Properties properties = kafkaStreamConfigMap();
//		constructKStream(properties);
//		kStreamJoinTest(properties);
		//kTableJoinTest(properties);
		//ktableAndkstreamTest(properties);
//		wordcountKstream(properties);
		wordcountKstreamLamda(properties);
		//kafkastreamAggregate(properties);
		// kafkastreamDemoByIP(properties);
//		aggregateList(properties);
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
		KStream<String, String> test13stream = builder.stream(stringSerde, stringSerde, "test50");
		KStream<String, String> join = test11Stream.join(test13stream, new ValueJoiner<String,String, String>() {
			@Override
			public String apply(String test11Value1, String test13Value2) {
				return "test12:"+test11Value1+",test14:"+test13Value2;
			}
		}, JoinWindows.of(TimeUnit.MINUTES.toMillis(50)),Serdes.String(),Serdes.String(),Serdes.String());
		join.print();
		startKafKaStream(properties, builder);
	}
	
	/**
	 * kTable之间连接测试
	 * @param properties
	 */
	public static void kTableJoinTest(Properties properties) {
		final Serde<String> stringSerde = Serdes.String();
		KStreamBuilder builder = new KStreamBuilder();
		KTable<String, String> table12 = builder.table(stringSerde, stringSerde, "kafka-stream-3", "kafka-store-1");
		KTable<String, String> table14 = builder.table(stringSerde, stringSerde, "kafka-stream-4", "kafka-store-2");
		KTable<String, String> kTable = table12.join(table14, new ValueJoiner<String, String, String>() {
			@Override
			public String apply(String table11, String table13) {
				return "test12:"+table12+",test14:"+table14;
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
		KStream<String, String> stream11 = builder.stream(stringSerde, stringSerde, "kafka-stream-3");
		KTable<String, String> table13 = builder.table(stringSerde, stringSerde, "kafka-stream-4", "kafka-store-2");
		KStream<String, String> join = stream11.leftJoin(table13, new ValueJoiner<String, String, String>(){
			@Override
			public String apply(String stream11, String table13) {
				return "kafka-stream-3:"+stream11+",kafka-stream-4:"+table13;
			}
		} , stringSerde, stringSerde);
		join.writeAsText(filePath);
		startKafKaStream(properties, builder);
	}
	
	/**
	 * 测试Ktable和KStream对应示例
	 * @param properties
	 */
	public  static void constructKStream(Properties properties){
		KStreamBuilder builder = new KStreamBuilder();
//	    KStream<String, String> stream = builder.stream("kafka-stream-3");
	    KTable<String, String> kTable = builder.table(Serdes.String(),Serdes.String(),"kafka-stream-3", "Ktable-test1");
	    kTable.print();
		startKafKaStream(properties, builder);
	}

	
	
	/**
	 * wordcount统计lamda形式
	 * @param properties
	 */
	public static void wordcountKstreamLamda(Properties properties){
		KStreamBuilder builder = new KStreamBuilder();
		KStream<String, String> stream = builder.stream("lijihongs2");
		KTable<String, Long> count = stream.filter((key,value) ->{if(StringUtils.isBlank(value)){return false;}return true;})
		      .flatMapValues(value -> Arrays.asList(value.toLowerCase(Locale.getDefault()).split("\\W+")))
		      .map((key,value)->{return new KeyValue<String,String>(value,value); })
		      .groupByKey()
		      .count("word-count");
		count.print();
		startKafKaStream(properties, builder);
	}
	
	/**
	 * 进行wordcount统计
	 * @param properties
	 */
	public static void wordcountKstream(Properties properties) {
		KStreamBuilder builder = new KStreamBuilder();
		KStream<String, String> stream = builder.stream("kafka-stream-3");
		//过滤掉空行
		KStream<String, String> filterStream = stream.filter(new Predicate<String, String>() {
			@Override
			public boolean test(String key, String value) {
				//返回false说明被过滤掉
				if(StringUtils.isBlank(value)){
					logger.info("filter value:"+value);
					return false;
				}
				return true;
			}
		});
		//按行解析出单词，将单词放入到一个迭代器当中
		KStream<String, String> wordCountKstream = filterStream.flatMapValues(new ValueMapper<String, Iterable<String>>() {
			@Override
			public Iterable<String> apply(String value) {
				logger.info("flatMapValues:"+value);
				List<String> list = Arrays.asList(value.toLowerCase(Locale.getDefault()).split(","));
				return list;
			}
		});
		//再通过map方法进行处理，将每个单词构成KeyValue实体
		KStream<String, String> wordPair = wordCountKstream.map(new KeyValueMapper<String, String, KeyValue<String,String>>() {
			@Override
			public KeyValue<String, String> apply(String key, String value) {
				logger.info("mapvalue:"+value);
				return new KeyValue<String,String>(value,value);
			}
		});
		
		//通过groupByKey方法将单词进行分组，
		KGroupedStream<String, String> wordGroup = wordPair.groupByKey();
		KTable<String, Long> words = wordGroup.count("word-count");
		words.print();
		words.writeAsText(filePath);
		startKafKaStream(properties, builder);
	}
	
	
	/**
	 * 使用lamda表达式完成聚合操作(求最大值)
	 * @param properties
	 */
	public static void kafkastreamAggregate(Properties properties){
		KStreamBuilder builder = new KStreamBuilder();
		KStream<String, String> kStream = builder.stream("kafka-stream-3");
		KTable<String, Integer> kTable = kStream.map((key,value)-> {return new KeyValue<String,Integer>(key, Integer.valueOf(value));})
		       .groupByKey(Serdes.String(), Serdes.Integer())
		       .aggregate(()->Integer.MAX_VALUE,(String key,Integer value,Integer aggregate)->{return value>aggregate?value:aggregate;}, Serdes.Integer(), "kafka-stream");
		 kTable.toStream().print();
		 startKafKaStream(properties, builder);
	}
	
	public static void aggregateList(Properties properties) {
		KStreamBuilder builder = new KStreamBuilder();
		KStream<String, String> kStream = builder.stream("lijihongs2");
		KStream<String, Integer> map = kStream.map((key,value)->{
			return new KeyValue<String,Integer>(value.split(" ")[0], Integer.valueOf(value.split(" ")[1]));
		});
		KGroupedStream<String, Integer> groupByKey = map.groupByKey(Serdes.String(), Serdes.Integer());
		Aggregator<String, Integer, List<Integer>> aggregator = new Aggregator<String, Integer, List<Integer>>() {

			@Override
			public List<Integer> apply(String aggKey, Integer value, List<Integer> aggregate) {
				aggregate.add(value);
				return aggregate;
			}
		};
		Initializer<List<Integer>> initializer = new Initializer<List<Integer>>() {
			@Override
			public List<Integer> apply() {
				return new ArrayList<Integer>();
			}
		};
		Serde<List<Integer>> sender = new Serde<List<Integer>>() {
			@Override
			public void configure(Map<String, ?> configs, boolean isKey) {
			}

			@Override
			public void close() {
			}

			@Override
			public Serializer<List<Integer>> serializer() {
				return new Serializer<List<Integer>>() {
					
					@Override
					public byte[] serialize(String topic, List<Integer> data) {
						Gson gson = new Gson();
						String json = gson.toJson(data);
						return json.getBytes();
					}
					
					@Override
					public void configure(Map<String, ?> configs, boolean isKey) {
					}
					
					@Override
					public void close() {
						
					}
				};
			}

			@Override
			public Deserializer<List<Integer>> deserializer() {
				return new Deserializer<List<Integer>>() {
					
					@Override
					public List<Integer> deserialize(String topic, byte[] data) {
						String string = new String(data);
						;
						Gson gson = new Gson();
						List<Integer> fromJson = gson.fromJson(string, new TypeToken<List<Integer>>(){}.getType());
						return fromJson;
					}
					
					@Override
					public void configure(Map<String, ?> configs, boolean isKey) {
					}
					
					@Override
					public void close() {
					}
				};
			}
		};
		TimeWindows of = TimeWindows.of(30*1000L);
		KTable<Windowed<String>, List<Integer>> aggregate = groupByKey.aggregate(initializer , aggregator ,of , sender , "aggregateList");
		KStream<Windowed<String>, List<Integer>> filter = aggregate.toStream().filter((Windowed<String> key, List<Integer> list)->{
			int result = 0;
			for (Integer integer : list) {
				result += integer;
			}
			return result > 10;
		});
		filter.writeAsText(filePath);
		startKafKaStream(properties, builder);
	}
	
	public static void kafkastreamDemoByIP(Properties properties){
		KStreamBuilder builder = new KStreamBuilder();
		KStream<String, String> kStream = builder.stream("kafka-stream-3");
		kStream.map((key,value) ->new KeyValue<>(value, value)).groupByKey()
		       .count(TimeWindows.of(60*1000L), "black-ip")
		       .toStream()
		       .filter((Windowed<String> key,Long value)->{
		    	   System.out.println("请求时间："+DateFormatUtils.format(new Date(), "yyyy-mm-dd HH:mm:ss")+",IP"+key.key()+",请求次数："+value);
		    	   if(null!=value && value.longValue()>2){ return true;}return false;}
		       ).process(() -> {return new IpBlackListProcessor();});
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
//		try {
//			Thread.sleep(5*1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		streams.close();
	}
	
	
	
}

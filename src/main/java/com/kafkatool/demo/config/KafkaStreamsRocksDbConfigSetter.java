package com.kafkatool.demo.config;

import java.util.Map;

import org.apache.kafka.streams.state.RocksDBConfigSetter;
import org.rocksdb.CompressionType;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2018年8月9日 下午7:58:58
* 类说明
*/
public class KafkaStreamsRocksDbConfigSetter implements RocksDBConfigSetter {

	@Override
	public void setConfig(String storeName, Options options, Map<String, Object> configs) {
		 //options.setCompressionType(CompressionType.ZLIB_COMPRESSION);
		try {
			RocksDB db = RocksDB.open(options,"D:\\tmp\\file\\my-stream-processing-application\\0_0\\rocksdb\\Ktable-test");
		} catch (RocksDBException e) {
			e.printStackTrace();
		}
	}

}

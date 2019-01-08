package com.kafkatool.demo.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/** * 
* @author wudi 
* E‐mail:wudi@vrvmail.com.cn 
* @version 创建时间：2018年12月20日 上午11:52:59 
* 类说明   时间处理类
*/
public class DateUtil {

	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * timeStamp转成Date类型
	 * @param time
	 * @return
	 */
	public static Date timeStampTransferDate(Timestamp time) {
		Date date = time;
		return date;
	}
	
	public static String format(Date date) {
		SimpleDateFormat formatTool = new SimpleDateFormat();
		formatTool.applyPattern(DEFAULT_DATE_PATTERN);
		return formatTool.format(date);
	}
	
	public static void main(String[] args) {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date date = timeStampTransferDate(ts);
		System.out.println(date);
	}
	
	
}

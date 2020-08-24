package com.kafkatool.demo.model;

import java.sql.Timestamp;

import lombok.Data;

/** * 
* @author wudi 
* E‐mail:wudi@vrvmail.com.cn 
* @version 创建时间：2019年5月30日 上午10:07:25 
* 类说明  三峡项目-Windows 操作系统密码暴力破解告警
*/
@LogDesc("Windows-操作系统密码暴力破解告警")
@Data
public class WindowsViolence {
      
	@FieldDesc("开始时间")
	private Timestamp start_time;
	@FieldDesc("结束时间")
	private Timestamp end_time;
	@FieldDesc("上报IP")
	private String  report_ip;
	@FieldDesc("上报时间")
	private Timestamp report_time;
	@FieldDesc("日志类型")
	private String type;
	@FieldDesc("时间")
	private Timestamp eventTime;
	@FieldDesc("主机名")
	private String hostname;
	@FieldDesc("关键字")
	private String keywords;
	@FieldDesc("事件类别")
	private String eventType;
	@FieldDesc("安全级别编码")
	private String severityValue;
	@FieldDesc("安全事件")
	private String severity;
	@FieldDesc("事件ID")
	private String eventID;
	@FieldDesc("来源")
	private String sourceName;
	@FieldDesc("来源的唯一编码")
	private String providerGuid;
	@FieldDesc("版本")
	private String version;
	@FieldDesc("任务")
	private String task;
	@FieldDesc("操作码")
	private String opcodeValue;
	@FieldDesc("记录ID")
	private String recordNumber;
	@FieldDesc("进程ID")
	private String processID;
	@FieldDesc("线程ID")
	private String threadID;
	@FieldDesc("类别")
	private String channel;
	@FieldDesc("消息")
	private String message;
	@FieldDesc("事件接收时间")
	private Timestamp eventReceivedTime;
	@FieldDesc("主机IP")
	private String hostip;
	@FieldDesc("数量")
	private Long cnt;
	@FieldDesc("报文")
	private String msg;

}

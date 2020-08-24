package com.kafkatool.demo.model;


import java.sql.Timestamp;

import lombok.Data;

/**
 *  @author zhouwu
 *  @version 创建时间：2019年8月12日 下午10:13:20  
 */
@Data
public class IdsEvent2 {
    @FieldDesc("上报设备IP")
    private String  report_ip;
    @FieldDesc("上报设备IP转换")
    private	String 	report_ip_num;
    @FieldDesc("入库时间")
    private	String 	indate;
    @FieldDesc("发生时间")
    private	String 	event_time;
    @FieldDesc("来源")
    private String msg_src;
    @FieldDesc("原始字段")
    private	String 	report_msg;
    @FieldDesc("安全域关联IP")
    private	String safety_margin_ip;
    @FieldDesc("安全级别")
    private	String 	security_level;
    @FieldDesc("日志类别")
    private	String log_type;
    @FieldDesc("安全域")
    private	String safety_margin;

    @FieldDesc("源IP（客户端IP）")
    private	String 	src_ip;
    @FieldDesc("源区域")
    private String src_area;
    @FieldDesc("目的IP（服务器IP）")
    private	String 	dst_ip;
    @FieldDesc("目标区域")
    private	String dst_area;
    /*  @FieldDesc("发生时间")
  private	String 	event_time;*/
    @FieldDesc("源端口")
    private	String src_port;
    @FieldDesc("目的端口")
    private	String dst_port;
    @FieldDesc("传输层协议")
    private	String transport_protocol;
    @FieldDesc("应用层协议")
    private	String app_protocol;
    @FieldDesc("威胁等级")
    private	String event_level;
    @FieldDesc("事件（类型）统一编码")
    private	String event_code;
    @FieldDesc("事件名称")
    private	String event_name;
    @FieldDesc("设备IP")
    private	String device_ip;
    @FieldDesc("设备名称")
    private	String device_name;
    @FieldDesc("事件摘要")
    private	String event_abstract;
    @FieldDesc("策略id")
    private	String strategy_id;
    @FieldDesc("GUID")
    private	String guid;

    @FieldDesc("处理时间")
    private Timestamp triggerTime;
}

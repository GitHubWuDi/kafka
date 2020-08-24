package com.kafkatool.demo.model;

import java.sql.Timestamp;

import lombok.Data;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2019年6月27日 下午4:03:25
* 类说明
*/
@Data
public class DeviceAccess {
	@FieldDesc("设备ID")
    private String  devid;
    @FieldDesc("日志产生时间戳")
    private	Timestamp time;
    @FieldDesc("设备事件")
    private	String 	event_class_id;
    @FieldDesc("")
    private	String 	event_id;
    @FieldDesc("设备名称")
    private	String 	name;
    @FieldDesc("终端IP地址")
    private	String 	ipaddr;
    @FieldDesc("端口名")
    private	String 	port_name;
    @FieldDesc("标识是否是告警事件")
    private	String 	alarm;
    @FieldDesc("告警级别")
    private	int level;
    @FieldDesc("事件信息")
    private	String 	details;

    @FieldDesc("源ip")
    private	String 	src_Ip;
    @FieldDesc("目标ip")
    private String dst_IP;
    @FieldDesc("源port")
    private	String 	src_port;
    @FieldDesc("目标port")
    private String dst_port;
    @FieldDesc("ip")
    private String relate_ip;
    @FieldDesc("处理时间")
    private Timestamp triggerTime;
    @FieldDesc("地区编码")
    private String areaCode;
    @FieldDesc("地区名称")
    private String areaName;
}

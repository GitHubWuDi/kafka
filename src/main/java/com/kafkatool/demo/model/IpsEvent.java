package com.kafkatool.demo.model;

import java.util.Date;

import lombok.Data;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2020年7月7日 上午10:03:02
* 类说明
*/
@Data
public class IpsEvent {
   
    private String  event_abstract;
    private	String 	src_ip;
    private	String 	event_time;
    private	String 	device_name;
    private String event_level;
    private	String 	dst_ip;
    private	String action_strategy;
    private	String 	src_area;
    private	String event_name;
    private	String dst_area;
    private	String 	dst_port;
    private String transport_protocol;
    private	String 	action_result;
    private	String device_ip;
    private	String guid;
    private	String event_code;
    private	String src_port;
    private	String app_protocol;
    private	String report_msg;
    private	String msg_src;
    private	String indate;
    private	String security_level;
    private	String safety_margin_ip;
    private	String log_type;
    private Long report_ip_num;
    private Date triggerTime; 
    private String report_ip;
    private String vul_attack_type;
    
}

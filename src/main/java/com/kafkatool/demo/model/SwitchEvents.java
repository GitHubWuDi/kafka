package com.kafkatool.demo.model;

import java.util.List;

import lombok.Data;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2020年5月15日 下午3:28:40
* 类说明
*/
@Data
public class SwitchEvents {

	private String guid;
    private String  event_time;  
	private	String	event_level;
	private	String	device_ip;
	private	String	event_name;
	private	String	report_msg;
	private	String	msg_src;
	private	String	indate;
	private	String	security_level;
	private	String	safety_margin_ip;
	private	String	log_type;
	private	Long	report_ip_num;
	private	String	safety_margin;
	private	Long	triggerTime;
	private	String	report_ip;
	private	String	device_name;
	private	String	event_type;
	private	String	event_detail;
	private List<RouteEvent> routeEvent;
	
}

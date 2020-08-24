package com.kafkatool.demo.model;

import lombok.Data;

/**
* @author wudi E-mail:wudi891012@163.com
* @version 创建时间：2020年5月21日 下午7:14:10
* 类说明
*/
@Data
public class RouteEvent {

	private String id;
	private String mac;
	private String ip_add;
	private String route_name;
	private long triggerTime;
}

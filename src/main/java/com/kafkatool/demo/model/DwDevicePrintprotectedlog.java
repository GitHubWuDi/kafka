package com.kafkatool.demo.model;

import java.sql.Timestamp;

import lombok.Data;

/** * 
* @author wudi 
* E‐mail:wudi@vrvmail.com.cn 
* @version 创建时间：2019年6月27日 下午7:08:49 
* 类说明      打印行为
*/
@Data
@LogDesc(value="打印行为")
public class DwDevicePrintprotectedlog {
	private	String	id;
	private	String	dev_only_id;
	private	String	ip;
	private	String	mac;
	private	String	name;
	private	String	org_id;
	private	String	org_name;
	private	String	reg_user_only_id;
	private	String	reg_user_account;
	private	String	os_login_account;
	private	String	user_only_id;
	private	String	user_account;
	private	String	client_time;
	private	String	report_time;
	private	String	policy_id;
	private	String	file_name;
	private	String	file_path;
	private	String	file_type;
	private	int	logic;
	private	String	logic_data;
	private	int	result;
	private	int	page_num;
	private	int	copy_num;
	private	String	printer_name;
	private	String	reg_user_name;
	private	String	user_name;
	private	String	user_org_id;
	private	String	user_org_code_ui;
	private	String	dev_org_code_ui;
	private	String	dev_org_path;
	private	int	ip_type;
	private	String	hold_name;
	private	int	bread;
	private	String	cyr_ip;
	private	String	cur_mac;
	private	long	ip_number;
	private	String	area_code;
	private	String	area_name;
	private	String	dt;
	private	String	province;
	private	String	src_Ip;
	private	String	dst_Ip;
	private	String	src_port;
	private	String	dst_port;
	private	String	relate_ip;
	private Timestamp triggerTime;
    @FieldDesc("地区编码")
    private String areaCode;
    @FieldDesc("地区名称")
    private String areaName;

}

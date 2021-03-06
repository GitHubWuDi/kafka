package com.kafkatool.demo.model.switch1;

import java.sql.Timestamp;

import lombok.Data;

/** * 
* @author wudi 
* E‐mail:wudi@vrvmail.com.cn 
* @version 创建时间：2019年10月12日 下午1:52:54 
* 类说明 
*/
@Data
public class SwitchPOJO {

	private String assetGuid;
    private String inDate;
    private String icmpPing;
    private String collectorIp;
    private String instanceId;
    private String dataPickerPlugin;
    private String event_Table_Name;
    private String runningTime;
    private String avgBusy1;
    private String h3cEntityExtMemUsage;
    private String ifNumber;
    private String ifIndex;
    private String ifDescr;
    private String ifType;
    private Long ifMtu;
    private String ifSpeed;
    private String ifPhysAddress;
    private String ifAdminStatus;
    private String ifOperStatus;
    private String ifLastChange;
    private String ifInOctets;
    private String ifInUcastPkts;
    private String ifInDiscards;
    private Timestamp triggerTime;
    private Long ifMtuMax;


}

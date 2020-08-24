package com.kafkatool.demo.model.switch1;

import java.sql.Timestamp;
import java.util.List;

import com.kafkatool.demo.vo.LogDesc;

import lombok.Data;

/**
 * @author TB
 * @date 2019/10/11 10:00
 */
@Data
public class SwitchVo {

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
    private Integer ifNumber;  //实际端口数
    private RunningDetails runningDetails;
    private List<IfEntry> ifEntryList;
    private String guid;
   // private Timestamp triggerTime;

}

package com.kafkatool.demo.model;

import java.util.List;

import com.kafkatool.demo.model.switch1.IfEntry1;
import com.kafkatool.demo.model.switch1.RunningDetails2;

import lombok.Data;

@Data
public class SwitchTableVO {

    private String assetGuid;
    private String ifNumber;
	private String collectorIp;
	private Long triggerTime;
	private Integer counts;
	private Long speedRate;
	private List<IfEntry1> ifEntryList;
	private RunningDetails2 runningDetail;
	private List<String> portArray;
	private List<Long> countArray; 
	
}

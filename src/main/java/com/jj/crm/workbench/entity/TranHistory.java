package com.jj.crm.workbench.entity;

import lombok.Data;

@Data
public class TranHistory {
	
	private String id;
	private String stage;
	private String money;
	private String expectedDate;
	private String createTime;
	private String createBy;
	private String tranId;

	private String possibility;

	
	
}

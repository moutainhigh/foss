package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.io.Serializable;

public class QueryPositionNumberEntity implements Serializable{
	
	private static final long serialVersionUID = 2075791909076280396L;
	//单号
	private String waybillNo;
	//外场编码
	private String orgCode;
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
  
}

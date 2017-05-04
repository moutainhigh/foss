package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ExceptMsgActionDto implements Serializable {
	private static final long serialVersionUID = 1L;

	//更改单ID
	private String waybillRfcId;
	
	//运单号
	private String waybillNo;
	
	//变更申请部门
	private String darftOrgName;
	
	//
	private String darftOrgCode;
	
	//变更内容
	private String rfcInfo;
	
	//变更申请人
	private String darfter;
	
	//更改受理时间
	private Date todoOperateTime;
	
	private List<String> waybillNoList;
	
	//运单号集合
	public List<String> getWaybillNoList() {
		return waybillNoList;
	}
	
	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}
	
	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	public String getDarftOrgCode() {
		return darftOrgCode;
	}

	public void setDarftOrgCode(String darftOrgCode) {
		this.darftOrgCode = darftOrgCode;
	}

	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getDarftOrgName() {
		return darftOrgName;
	}

	public void setDarftOrgName(String darftOrgName) {
		this.darftOrgName = darftOrgName;
	}

	public String getRfcInfo() {
		return rfcInfo;
	}

	public void setRfcInfo(String rfcInfo) {
		this.rfcInfo = rfcInfo;
	}

	public String getDarfter() {
		return darfter;
	}

	public void setDarfter(String darfter) {
		this.darfter = darfter;
	}

	public Date getTodoOperateTime() {
		return todoOperateTime;
	}

	public void setTodoOperateTime(Date todoOperateTime) {
		this.todoOperateTime = todoOperateTime;
	}
}

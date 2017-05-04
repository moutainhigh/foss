package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class PendingMsgActionDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String pendTodoId;
	private String waybillRfcId;
	//运单号
	private String waybillNo;
	
	//更改单ID
	private String waybillRfc;
	
	//更改起草部门
	private String darftOrgCode;
	
	//更改起草部门
	private String darftOrgName;
	
	//更改处理时间
	private Date pendOperateTime;
	
	//失败原因
	private String failReason;
	
	//更改项
	private String changeItem;

	public String getChangeItem() {
		return changeItem;
	}

	public String getPendTodoId() {
		return pendTodoId;
	}

	public void setPendTodoId(String pendTodoId) {
		this.pendTodoId = pendTodoId;
	}

	public void setChangeItem(String changeItem) {
		this.changeItem = changeItem;
	}

	public Date getPendOperateTime() {
		return pendOperateTime;
	}

	public String getDarftOrgName() {
		return darftOrgName;
	}

	public void setDarftOrgName(String darftOrgName) {
		this.darftOrgName = darftOrgName;
	}

	public void setPendOperateTime(Date pendOperateTime) {
		this.pendOperateTime = pendOperateTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getWaybillRfc() {
		return waybillRfc;
	}

	public void setWaybillRfc(String waybillRfc) {
		this.waybillRfc = waybillRfc;
	}

	public String getDarftOrgCode() {
		return darftOrgCode;
	}

	public void setDarftOrgCode(String darftOrgCode) {
		this.darftOrgCode = darftOrgCode;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}
}

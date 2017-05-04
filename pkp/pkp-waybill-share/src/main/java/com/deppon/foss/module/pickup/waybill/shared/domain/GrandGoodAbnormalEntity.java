package com.deppon.foss.module.pickup.waybill.shared.domain;

public class GrandGoodAbnormalEntity {
	//id
	private String id;
	//单号
	private String waybillNo;
	//出险原因
	private String dangerCause;
	//责任部门
	private String unifiedCode;
	//经手是否有责任
	private String passIsDuty;
	
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDangerCause() {
		return dangerCause;
	}
	public void setDangerCause(String dangerCause) {
		this.dangerCause = dangerCause;
	}
	public String getUnifiedCode() {
		return unifiedCode;
	}
	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}
	public String getPassIsDuty() {
		return passIsDuty;
	}
	public void setPassIsDuty(String passIsDuty) {
		this.passIsDuty = passIsDuty;
	}
	
	

}

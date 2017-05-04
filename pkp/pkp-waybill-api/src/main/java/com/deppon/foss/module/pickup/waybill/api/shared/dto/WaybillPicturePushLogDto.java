package com.deppon.foss.module.pickup.waybill.api.shared.dto;

import java.io.Serializable;

public class WaybillPicturePushLogDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -397291706225557791L;
	
	private String id;
	//运单号
	private String waybillNo;
	//部门编码
	private String orgCode;
	//部门名称
	private String orgName;
	//操作人工号
	private String operatorCode;
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
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}

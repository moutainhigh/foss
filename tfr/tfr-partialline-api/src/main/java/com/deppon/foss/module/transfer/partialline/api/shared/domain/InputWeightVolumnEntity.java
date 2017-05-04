package com.deppon.foss.module.transfer.partialline.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

public class InputWeightVolumnEntity {
	private String id;
	private String waybillNo;
	private String serialNo;
	private BigDecimal weight;
	private BigDecimal volumn;
	private String modifyUserCode;
	private String modifyUserName;
	private String modifyOrgCode;
	private String modifyOrgName;
	private Date modifyTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getVolumn() {
		return volumn;
	}
	public void setVolumn(BigDecimal volumn) {
		this.volumn = volumn;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}
	public String getModifyOrgName() {
		return modifyOrgName;
	}
	public void setModifyOrgName(String modifyOrgName) {
		this.modifyOrgName = modifyOrgName;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
}

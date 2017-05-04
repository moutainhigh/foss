package com.deppon.foss.module.transfer.unload.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class ManualInputGoodsQtyEntity extends BaseEntity {

	private static final long serialVersionUID = 139408690968481333L;
	
	private String transferCenterCode;
	
	private String transferCenterName;
	
	private BigDecimal loadAndUnload;
	
	private BigDecimal dispath;
	
	private BigDecimal stationReceive;
	
	private BigDecimal centralizedReceive;
	
	private BigDecimal selfPickup;
	
	private BigDecimal other;
	
	private BigDecimal total;
	
	private String note;
	
	private Date inputDate;
	
	private String inputMonth;
	
	private Date createTime;
	
	private String createUserCode;
	
	private String createUserName;
	
	private Date modifyTime;
	
	private String modifyUserCode;
	
	private String modifyUserName;

	public String getTransferCenterCode() {
		return transferCenterCode;
	}

	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}

	public String getTransferCenterName() {
		return transferCenterName;
	}

	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}

	public BigDecimal getLoadAndUnload() {
		return loadAndUnload;
	}

	public void setLoadAndUnload(BigDecimal loadAndUnload) {
		this.loadAndUnload = loadAndUnload;
	}

	public BigDecimal getDispath() {
		return dispath;
	}

	public void setDispath(BigDecimal dispath) {
		this.dispath = dispath;
	}

	public BigDecimal getStationReceive() {
		return stationReceive;
	}

	public void setStationReceive(BigDecimal stationReceive) {
		this.stationReceive = stationReceive;
	}

	public BigDecimal getCentralizedReceive() {
		return centralizedReceive;
	}

	public void setCentralizedReceive(BigDecimal centralizedReceive) {
		this.centralizedReceive = centralizedReceive;
	}

	public BigDecimal getSelfPickup() {
		return selfPickup;
	}

	public void setSelfPickup(BigDecimal selfPickup) {
		this.selfPickup = selfPickup;
	}

	public BigDecimal getOther() {
		return other;
	}

	public void setOther(BigDecimal other) {
		this.other = other;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getInputMonth() {
		return inputMonth;
	}

	public void setInputMonth(String inputMonth) {
		this.inputMonth = inputMonth;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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

	@Override
	public String toString() {
		return "ManualInputGoodsQtyEntity [id = " + super.getId() + ", transferCenterCode="
				+ transferCenterCode + ", transferCenterName="
				+ transferCenterName + ", loadAndUnload=" + loadAndUnload
				+ ", dispath=" + dispath + ", stationReceive=" + stationReceive
				+ ", centralizedReceive=" + centralizedReceive
				+ ", selfPickup=" + selfPickup + ", other=" + other
				+ ", total=" + total + ", note=" + note + ", inputDate="
				+ inputDate + ", inputMonth=" + inputMonth + ", createTime="
				+ createTime + ", createUserCode=" + createUserCode
				+ ", createUserName=" + createUserName + ", modifyTime="
				+ modifyTime + ", modifyUserCode=" + modifyUserCode
				+ ", modifyUserName=" + modifyUserName + "]";
	}
	
}

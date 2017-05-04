package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 *租车标记明细表
 **/
public class TempRentalMarkDetailEntity {
	
	private String id;
	private String tempRentalMarkId;//租车id
	private String tempRentalMarkNo;//租车编号
	private String billNo;//单号
	private String billType;//单据类型
	private BigDecimal weight;//总量
	private BigDecimal volume;//体积
	private String createUserName;//创建人
	private String createUserCode;//创建人编码
	private Date createDate;//创建时间
	private String rentalCarUserType;//租车用途
	private String markDepartName;//标记部门
	private String markDepartCode;//标记部门编码
	private String vehicleNo;//车牌号
	private String payableNo;//应付单号
	private String notes;//备注
	private BigDecimal rentCarAmount;//租车金额
	private Date billCreateDate ;//单据（运单、交接单、派送单、配载单）生成时间
	private String consultPriceNo;//询价编号
	
	public String getConsultPriceNo() {
		return consultPriceNo;
	}

	public void setConsultPriceNo(String consultPriceNo) {
		this.consultPriceNo = consultPriceNo;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getTempRentalMarkId() {
		return tempRentalMarkId;
	}
	public void setTempRentalMarkId(String tempRentalMarkId) {
		this.tempRentalMarkId = tempRentalMarkId;
	}
	public String getTempRentalMarkNo() {
		return tempRentalMarkNo;
	}
	public void setTempRentalMarkNo(String tempRentalMarkNo) {
		this.tempRentalMarkNo = tempRentalMarkNo;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRentalCarUserType() {
		return rentalCarUserType;
	}
	public void setRentalCarUserType(String rentalCarUserType) {
		this.rentalCarUserType = rentalCarUserType;
	}
	public String getMarkDepartName() {
		return markDepartName;
	}
	public void setMarkDepartName(String markDepartName) {
		this.markDepartName = markDepartName;
	}
	public String getMarkDepartCode() {
		return markDepartCode;
	}
	public void setMarkDepartCode(String markDepartCode) {
		this.markDepartCode = markDepartCode;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getPayableNo() {
		return payableNo;
	}
	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public BigDecimal getRentCarAmount() {
		return rentCarAmount;
	}
	public void setRentCarAmount(BigDecimal rentCarAmount) {
		this.rentCarAmount = rentCarAmount;
	}

	public Date getBillCreateDate() {
		return billCreateDate;
	}
	public void setBillCreateDate(Date billCreateDate) {
		this.billCreateDate = billCreateDate;
	}
}

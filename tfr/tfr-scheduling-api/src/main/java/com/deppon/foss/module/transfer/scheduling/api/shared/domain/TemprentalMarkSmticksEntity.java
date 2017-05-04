package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import java.util.Date;

public class TemprentalMarkSmticksEntity {

	
	private String id;//id
	private String tempRentalMarkId;//租车id
	private String tempRentalMarkNo;//租车编号
	private String smallTickerNum;//小票单号
	private String createUserName;//创建人
	private String createUserCode;//创建人工号
	private Date createDate;//创建时间
	private String wayBillNo;//运单号
	private String active;//状态
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
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
	public String getSmallTickerNum() {
		return smallTickerNum;
	}
	public void setSmallTickerNum(String smallTickerNum) {
		this.smallTickerNum = smallTickerNum;
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
	
	
}

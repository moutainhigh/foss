package com.deppon.esb.pojo.domain.crm2foss;

import java.util.Date;


/**
 * 
 * 客户圈同步接口请求实体
 * @author 308861 
 * @date 2016-12-21 上午10:34:23
 * @since
 * @version
 */
public class CustomerCircleCrmEntity {
	
	String id;	//客户圈关系ID
	String custCircleCode;	//客户圈编码
	String custCircleName; //客户圈名称
	String custCode; //客户编码
	String custName;//客户名称
	String isMainCust;//是否主客户（0：从客户、1：主客户）
	String isFocusPay;//是否统一结算
	String dunningDeptCode; //催款部门编码
	String dunningDeptName;//催款部门名称
	Date beginTime; //开始时间
	Date endTime; //结束时间
	String isUsed;	//是否启用（0：未启用、1：启用）
	String periodOfAccount;//账期天数
	
	public String getCustCircleCode() {
		return custCircleCode;
	}
	public void setCustCircleCode(String custCircleCode) {
		this.custCircleCode = custCircleCode;
	}
	public String getCustCircleName() {
		return custCircleName;
	}
	public void setCustCircleName(String custCircleName) {
		this.custCircleName = custCircleName;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getIsMainCust() {
		return isMainCust;
	}
	public void setIsMainCust(String isMainCust) {
		this.isMainCust = isMainCust;
	}
	public String getIsFocusPay() {
		return isFocusPay;
	}
	public void setIsFocusPay(String isFocusPay) {
		this.isFocusPay = isFocusPay;
	}
	public String getDunningDeptCode() {
		return dunningDeptCode;
	}
	public void setDunningDeptCode(String dunningDeptCode) {
		this.dunningDeptCode = dunningDeptCode;
	}
	public String getDunningDeptName() {
		return dunningDeptName;
	}
	public void setDunningDeptName(String dunningDeptName) {
		this.dunningDeptName = dunningDeptName;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPeriodOfAccount() {
		return periodOfAccount;
	}
	public void setPeriodOfAccount(String periodOfAccount) {
		this.periodOfAccount = periodOfAccount;
	}
	
}

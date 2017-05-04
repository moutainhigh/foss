package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class FocusRecordManagementEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID =  7946775205146654322L;
	
	private String billingGroupName;
	private String billingGroupCode;
	private String transDepartmentName;
	private String transDepartmentCode;
	private String salesDepartmentName;
	private String salesDepartmentCode;
	private String billingAmount;
	private String  active;
	private Date startDate;
	private Date endDate;
	
	
	public String getBillingGroupName() {
		return billingGroupName;
	}
	public void setBillingGroupName(String billingGroupName) {
		this.billingGroupName = billingGroupName;
	}
	public String getBillingGroupCode() {
		return billingGroupCode;
	}
	public void setBillingGroupCode(String billingGroupCode) {
		this.billingGroupCode = billingGroupCode;
	}
	public String getTransDepartmentName() {
		return transDepartmentName;
	}
	public void setTransDepartmentName(String transDepartmentName) {
		this.transDepartmentName = transDepartmentName;
	}
	public String getTransDepartmentCode() {
		return transDepartmentCode;
	}
	public void setTransDepartmentCode(String transDepartmentCode) {
		this.transDepartmentCode = transDepartmentCode;
	}
	public String getSalesDepartmentName() {
		return salesDepartmentName;
	}
	public void setSalesDepartmentName(String salesDepartmentName) {
		this.salesDepartmentName = salesDepartmentName;
	}
	public String getSalesDepartmentCode() {
		return salesDepartmentCode;
	}
	public void setSalesDepartmentCode(String salesDepartmentCode) {
		this.salesDepartmentCode = salesDepartmentCode;
	}
	public String getBillingAmount() {
		return billingAmount;
	}
	public void setBillingAmount(String billingAmount) {
		this.billingAmount = billingAmount;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}

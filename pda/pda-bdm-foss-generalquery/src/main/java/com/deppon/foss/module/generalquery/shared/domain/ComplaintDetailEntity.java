package com.deppon.foss.module.generalquery.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class ComplaintDetailEntity implements Serializable {
	
	private String errorId;
	  /**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Description 凭证号（凭证号）-订单、运单、其他
	 */
	private String voucherNumber;
	/**
	 * Description 处理编号（处理编号）
	 */
	private String dealNumber;  
	/**
	 * Description 客户等级（客户等级）-来电客户：固定客户、散客、潜客，备注：字符长度20
	 */
	private String callCustLevel;
	/**
	 * Description 客户类型 （客户类型 ）- 来电客户：企业、个人，备注：字符长度20
	 */
	private String callCustType;
	/**
	 * Description 来电客户- 字符长度40
	 */
	private String callCust;
	/**
	 * Description 工单来源（工单来源）-苏州电话=SUZHOU、合肥电话=HEFEI、官网=GUANWANG、企业邮箱=EMAIL
	 */
	private String source;
	/**
	 * Description 客户要求（客户要求）-字符长度 200
	 */
	private String custDemand;
	/**
	 * Description 上报时间(日期)
	 */
	private Date timeReport;
	
	/**
	 * Description 处理事项 （处理语言）- 字符长度 200
	 */
	private String dealMatter;
	/**
	 * Description 责任部门（责任部门）
	 */
	private String dutyDepartment;
	/**
	 * Description 责任人（责任人）
	 */
	private String dutyPerson;	
	/**
	 * Description 工单级别 - 一级=LEVEL_1、二级=LEVEL_2、三级=LEVEL_3、四级=LEVEL_4、五级=LEVEL_5
	 */
	private String compLevel;
	/**
	 * Description 联系电话-字符长度20
	 */
	private String contactPhone;
	/**
	 * Description 业务类型
	 */
	private String bussType;
	
	
	
	
	public String getVoucherNumber() {
		return voucherNumber;
	}
	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}
	public String getDealNumber() {
		return dealNumber;
	}
	public void setDealNumber(String dealNumber) {
		this.dealNumber = dealNumber;
	}
	public String getCallCustLevel() {
		return callCustLevel;
	}
	public void setCallCustLevel(String callCustLevel) {
		this.callCustLevel = callCustLevel;
	}
	public String getCallCustType() {
		return callCustType;
	}
	public void setCallCustType(String callCustType) {
		this.callCustType = callCustType;
	}
	public String getCallCust() {
		return callCust;
	}
	public void setCallCust(String callCust) {
		this.callCust = callCust;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getCustDemand() {
		return custDemand;
	}
	public void setCustDemand(String custDemand) {
		this.custDemand = custDemand;
	}
	public Date getTimeReport() {
		return timeReport;
	}
	public void setTimeReport(Date timeReport) {
		this.timeReport = timeReport;
	}
	public String getDealMatter() {
		return dealMatter;
	}
	public void setDealMatter(String dealMatter) {
		this.dealMatter = dealMatter;
	}
	public String getDutyDepartment() {
		return dutyDepartment;
	}
	public void setDutyDepartment(String dutyDepartment) {
		this.dutyDepartment = dutyDepartment;
	}
	public String getDutyPerson() {
		return dutyPerson;
	}
	public void setDutyPerson(String dutyPerson) {
		this.dutyPerson = dutyPerson;
	}
	public String getCompLevel() {
		return compLevel;
	}
	public void setCompLevel(String compLevel) {
		this.compLevel = compLevel;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getBussType() {
		return bussType;
	}
	public void setBussType(String bussType) {
		this.bussType = bussType;
	}
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	
		

	
}

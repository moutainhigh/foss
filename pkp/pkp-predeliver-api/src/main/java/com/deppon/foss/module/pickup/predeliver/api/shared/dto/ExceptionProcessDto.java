/**
 *  initial comments.
 */
/*
 * PROJECT NAME: pkp-predeliver-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.predeliver.api.shared.dto
 * FILE    NAME: ExceptionProcessDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionProcessDetailEntity;

/**
 * 异常处理返回DTO.
 *
 * @author 043258-foss-zhaobin
 * @date 2012-10-30 上午9:37:09
 */
public class ExceptionProcessDto implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 异常Id. */
	private String exceptionProcessId;
	
	/** 运单号. */
	private String waybillNo;
	
	/** 异常状态. */
	private String status;
	
	/** 收货客户名称. */
	private String receiveCustomerName;
	
	/** 收货客户手机. */
	private String receiveCustomerMobilephone;
	
	/** 收货客户电话. */
	private String receiveCustomerPhone;
	
	/** 异常类型. */
	private String exceptionType;
	
	/** 最新异常原因. */
	private String exceptionReason;

	/** 异常操作. */
	private String exceptionOperate;
	
	/** 通知内容. */
	private String noticeContext;
	
	/** 异常环节. */
	private String exceptionLink;
	
	/** 流水号. */
	private String serialNo;
	
	/** 收货部门. */
	private String receiveOrgCode;
	
	/** 收货部门名称. */
	private String receiveOrgName;
	
	/** 登记人. */
	private String createUserName;
	
	/** 异常生成时间. */
	private Date exceptionTime;
	
	/** 提货网点. */
	private String customerPickupOrgCode;
	
	/** 提货网点名称. */
	private String customerPickupOrgName;
	
	/** 库存天数. */
	private Integer storageDay;
	
	/** 异常处理结果. */
	private String notes;
	
	private String arrivesheetId;
	/** 行政区域  **/
	private String countyName;
	
	/** 异常处理明细. */
	private List<ExceptionProcessDetailEntity> exceptionProcessDetailEntityList;

	/**
	 *  收货人是否大客户.
	 */
	private String receiveBigCustomer;
	/**
	 * 更新时间
	 */
	private Date modifyTime;
	
	/**
	 * 签收记录状态(Y表示含有签收记录)
	 */
	private String signRecord;
	
	/**
	 * Gets the 收货人是否大客户.
	 *
	 * @return the 收货人是否大客户.
	 */
	public String getReceiveBigCustomer() {
		return receiveBigCustomer;
	}

	/**
	 * Sets the 收货人是否大客户.
	 *
	 * @param receiveBigCustomer the 收货人是否大客户.
	 */
	public void setReceiveBigCustomer(String receiveBigCustomer) {
		this.receiveBigCustomer = receiveBigCustomer;
	}
	
	
	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	/**
	 * Gets the exception process id.
	 *
	 * @return the exceptionProcessId
	 */
	public String getExceptionProcessId() {
		return exceptionProcessId;
	}

	/**
	 * Sets the exception process id.
	 *
	 * @param exceptionProcessId the exceptionProcessId to see
	 */
	public void setExceptionProcessId(String exceptionProcessId) {
		this.exceptionProcessId = exceptionProcessId;
	}

	/**
	 * Gets the waybill no.
	 *
	 * @return the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the waybill no.
	 *
	 * @param waybillNo the waybillNo to see
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the status to see
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the receive customer name.
	 *
	 * @return the receiveCustomerName
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * Sets the receive customer name.
	 *
	 * @param receiveCustomerName the receiveCustomerName to see
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * Gets the receive customer mobilephone.
	 *
	 * @return the receiveCustomerMobilephone
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * Sets the receive customer mobilephone.
	 *
	 * @param receiveCustomerMobilephone the receiveCustomerMobilephone to see
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * Gets the receive customer phone.
	 *
	 * @return the receiveCustomerPhone
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * Sets the receive customer phone.
	 *
	 * @param receiveCustomerPhone the receiveCustomerPhone to see
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * Gets the exception type.
	 *
	 * @return the exceptionType
	 */
	public String getExceptionType() {
		return exceptionType;
	}

	/**
	 * Sets the exception type.
	 *
	 * @param exceptionType the exceptionType to see
	 */
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}

	/**
	 * Gets the exception link.
	 *
	 * @return the exceptionLink
	 */
	public String getExceptionLink() {
		return exceptionLink;
	}

	/**
	 * Sets the exception link.
	 *
	 * @param exceptionLink the exceptionLink to see
	 */
	public void setExceptionLink(String exceptionLink) {
		this.exceptionLink = exceptionLink;
	}

	/**
	 * Gets the serial no.
	 *
	 * @return the serialNo
	 */
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * Sets the serial no.
	 *
	 * @param serialNo the serialNo to see
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * Gets the receive org code.
	 *
	 * @return the receiveOrgCode
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * Sets the receive org code.
	 *
	 * @param receiveOrgCode the receiveOrgCode to see
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * Gets the create user name.
	 *
	 * @return the createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * Sets the create user name.
	 *
	 * @param createUserName the createUserName to see
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * Gets the exception time.
	 *
	 * @return the exceptionTime
	 */
	public Date getExceptionTime() {
		return exceptionTime;
	}

	/**
	 * Sets the exception time.
	 *
	 * @param exceptionTime the exceptionTime to see
	 */
	public void setExceptionTime(Date exceptionTime) {
		this.exceptionTime = exceptionTime;
	}

	/**
	 * Gets the customer pickup org code.
	 *
	 * @return the customerPickupOrgCode
	 */
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	/**
	 * Sets the customer pickup org code.
	 *
	 * @param customerPickupOrgCode the customerPickupOrgCode to see
	 */
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	/**
	 * Gets the exception process detail entity list.
	 *
	 * @return the exceptionProcessDetailEntityList
	 */
	public List<ExceptionProcessDetailEntity> getExceptionProcessDetailEntityList() {
		return exceptionProcessDetailEntityList;
	}

	/**
	 * Sets the exception process detail entity list.
	 *
	 * @param exceptionProcessDetailEntityList the
	 * exceptionProcessDetailEntityList to see
	 */
	public void setExceptionProcessDetailEntityList(List<ExceptionProcessDetailEntity> exceptionProcessDetailEntityList) {
		this.exceptionProcessDetailEntityList = exceptionProcessDetailEntityList;
	}

	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the notes.
	 *
	 * @param notes the notes to see
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Gets the storage day.
	 *
	 * @return the storageDay
	 */
	public Integer getStorageDay() {
		return storageDay;
	}

	/**
	 * Sets the storage day.
	 *
	 * @param storageDay the storageDay to see
	 */
	public void setStorageDay(Integer storageDay) {
		this.storageDay = storageDay;
	}

	/**
	 * Gets the receive org name.
	 *
	 * @return the receive org name
	 */
	public String getReceiveOrgName()
	{
		return receiveOrgName;
	}

	/**
	 * Sets the receive org name.
	 *
	 * @param receiveOrgName the new receive org name
	 */
	public void setReceiveOrgName(String receiveOrgName)
	{
		this.receiveOrgName = receiveOrgName;
	}

	/**
	 * Gets the customer pickup org name.
	 *
	 * @return the customer pickup org name
	 */
	public String getCustomerPickupOrgName()
	{
		return customerPickupOrgName;
	}

	/**
	 * Sets the customer pickup org name.
	 *
	 * @param customerPickupOrgName the new customer pickup org name
	 */
	public void setCustomerPickupOrgName(String customerPickupOrgName)
	{
		this.customerPickupOrgName = customerPickupOrgName;
	}

	public String getArrivesheetId() {
		return arrivesheetId;
	}

	public void setArrivesheetId(String arrivesheetId) {
		this.arrivesheetId = arrivesheetId;
	}

	public String getExceptionOperate() {
		return exceptionOperate;
	}

	public void setExceptionOperate(String exceptionOperate) {
		this.exceptionOperate = exceptionOperate;
	}

	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}

	public String getNoticeContext() {
		return noticeContext;
	}

	public void setNoticeContext(String noticeContext) {
		this.noticeContext = noticeContext;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getSignRecord() {
		return signRecord;
	}

	public void setSignRecord(String signRecord) {
		this.signRecord = signRecord;
	}
	
}
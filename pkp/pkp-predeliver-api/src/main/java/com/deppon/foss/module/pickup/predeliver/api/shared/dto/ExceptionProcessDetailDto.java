/**
 *  initial comments.
 */
/*
 * PROJECT NAME: pkp-predeliver-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.predeliver.api.shared.dto
 * FILE    NAME: ExceptionProcessDetailDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ExceptionProcessDetailEntity;

/**
 * 异常处理历史信息Dto.
 *
 * @author 043258-foss-zhaobin
 * @date 2012-11-1 下午2:09:50
 */
public class ExceptionProcessDetailDto implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 异常ID. */
	private String exceptionProcessId;
	
	/** 运单号. */
	private String waybillNo;
	
	/** 异常类型. */
	private String exceptionType;
	
	/** 异常环节. */
	private String exceptionLink;
	
	/** 收货客户名称. */
	private String receiveCustomerName;
	
	/** 收货客户手机. */
	private String receiveCustomerMobilephone;
	
	/** 收货客户电话. */
	private String receiveCustomerPhone;
	
	/** 收货部门. */
	private String receiveOrgCode;
	
	/** 收货部门名称. */
	private String receiveOrgName;
	
	/** 收货部门电话. */
	private String receiveOrgTel;
	
	/** 通知内容. */
	private String noticeContext;
	
	/** 流水号. */
	private String serialNo;
	
	/** 上报人. */
	private String createUserName;
	
	/** 预计送货日期. */
	private Date deliverDate;
	
	private String arrivesheetId;
	
	private String notes;
	
	/**
	 * 付款方式
	 */
	private String paymentType;
	
	/**
	 * 异常原因
	 */
	private String exceptionReason;
	
	/**
	 * 到付金额
	 */
	private BigDecimal toPayAmount;
	private String receiveCustomerContact;// 收货联系人
	private String receiveCustomerCode; //收货客户Code
	private String actualProvCode; //实际收货地址省代码
	private String actualCityCode; //实际收货地址市代码
	private String actualDistrictCode; //实际收货地址区域代码
	private String actualProvN; //实际收货地址省
	private String actualCityN; //实际收货地址市
	private String actualDistrictN; //实际收货地址区域
	private String actualStreetn; //实际收货地址街道
	private String actualAddressDetail; //实际收货地址详细
	private String isExhibition; //是否会展货
	private String isEmptyCar; //是否空车出
	private String invoiceType;  //发票类型
	private String invoiceDetail; //发票类型备注
	private String deliveryTimeInterval; //送货时间段
	private String deliveryTimeStart; //送货时间点(开始)
	private String deliveryTimeOver; //送货时间点(结束)
	private String isPreNotify;      //是否提前通知
	private String isSentRequired;   //是否必送货
	
	private NotifyCustomerDto notifyCustomerDto;
	
	public NotifyCustomerDto getNotifyCustomerDto() {
		return notifyCustomerDto;
	}

	public void setNotifyCustomerDto(NotifyCustomerDto notifyCustomerDto) {
		this.notifyCustomerDto = notifyCustomerDto;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	public String getActualProvCode() {
		return actualProvCode;
	}

	public void setActualProvCode(String actualProvCode) {
		this.actualProvCode = actualProvCode;
	}

	public String getActualCityCode() {
		return actualCityCode;
	}

	public void setActualCityCode(String actualCityCode) {
		this.actualCityCode = actualCityCode;
	}

	public String getActualDistrictCode() {
		return actualDistrictCode;
	}

	public void setActualDistrictCode(String actualDistrictCode) {
		this.actualDistrictCode = actualDistrictCode;
	}

	public String getActualProvN() {
		return actualProvN;
	}

	public void setActualProvN(String actualProvN) {
		this.actualProvN = actualProvN;
	}

	public String getActualCityN() {
		return actualCityN;
	}

	public void setActualCityN(String actualCityN) {
		this.actualCityN = actualCityN;
	}

	public String getActualDistrictN() {
		return actualDistrictN;
	}

	public void setActualDistrictN(String actualDistrictN) {
		this.actualDistrictN = actualDistrictN;
	}

	public String getActualStreetn() {
		return actualStreetn;
	}

	public void setActualStreetn(String actualStreetn) {
		this.actualStreetn = actualStreetn;
	}

	public String getActualAddressDetail() {
		return actualAddressDetail;
	}

	public void setActualAddressDetail(String actualAddressDetail) {
		this.actualAddressDetail = actualAddressDetail;
	}

	public String getIsExhibition() {
		return isExhibition;
	}

	public void setIsExhibition(String isExhibition) {
		this.isExhibition = isExhibition;
	}

	public String getIsEmptyCar() {
		return isEmptyCar;
	}

	public void setIsEmptyCar(String isEmptyCar) {
		this.isEmptyCar = isEmptyCar;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceDetail() {
		return invoiceDetail;
	}

	public void setInvoiceDetail(String invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}

	public String getDeliveryTimeInterval() {
		return deliveryTimeInterval;
	}

	public void setDeliveryTimeInterval(String deliveryTimeInterval) {
		this.deliveryTimeInterval = deliveryTimeInterval;
	}

	public String getDeliveryTimeStart() {
		return deliveryTimeStart;
	}

	public void setDeliveryTimeStart(String deliveryTimeStart) {
		this.deliveryTimeStart = deliveryTimeStart;
	}

	public String getDeliveryTimeOver() {
		return deliveryTimeOver;
	}

	public void setDeliveryTimeOver(String deliveryTimeOver) {
		this.deliveryTimeOver = deliveryTimeOver;
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
	 * Gets the create user name.
	 *
	 * @return the create user name
	 */
	public String getCreateUserName()
	{
		return createUserName;
	}

	/**
	 * Sets the create user name.
	 *
	 * @param createUserName the new create user name
	 */
	public void setCreateUserName(String createUserName)
	{
		this.createUserName = createUserName;
	}

	/**
	 * Gets the notice context.
	 *
	 * @return the noticeContext
	 */
	public String getNoticeContext() {
		return noticeContext;
	}

	/**
	 * Sets the notice context.
	 *
	 * @param noticeContext the noticeContext to see
	 */
	public void setNoticeContext(String noticeContext) {
		this.noticeContext = noticeContext;
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

	public String getArrivesheetId() {
		return arrivesheetId;
	}

	public void setArrivesheetId(String arrivesheetId) {
		this.arrivesheetId = arrivesheetId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getReceiveOrgTel() {
		return receiveOrgTel;
	}

	public void setReceiveOrgTel(String receiveOrgTel) {
		this.receiveOrgTel = receiveOrgTel;
	}

	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public String getIsPreNotify() {
		return isPreNotify;
	}

	public void setIsPreNotify(String isPreNotify) {
		this.isPreNotify = isPreNotify;
	}

	public String getIsSentRequired() {
		return isSentRequired;
	}

	public void setIsSentRequired(String isSentRequired) {
		this.isSentRequired = isSentRequired;
	}

	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}

	
}
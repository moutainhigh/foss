package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 零担电子面单查询实体
 */
public class LTLEWaybillQueryResultDto implements Serializable {
	private static final long serialVersionUID = -7673674692621344129L;

	// 运单状态、
	private String waybillStatus;

	//标签推送状态
	private String labelPushStatus;
	
	// 运单号、
	private String waybillNo;
	
	// 订单号、
	private String orderNo;
	
	//查询运单号集合
	private List<String> waybillNoList;
	
	//查询订单号集合
	private List<String> orderNoList;

	//查询订单号集合
	private List<String> customerCodeList;

	// 收入部门、
	private String receiveOrgName;
	
	// 收入部门编码
	private String receiveOrgCode;
	
	// 开单部门、
	private String createOrgName;

	// 开单部门编码
	private String createOrgCode;

	// 提货网点编码、(目地站)
	private String customerPickupOrgCode;
	
	// 提货网点名称、(目地站)
	private String customerPickupOrgName;
	
	// 下单时间、
	private Date billTime;

	// 扫描时间、
	private Date scanTime;

	// 开单时间、
	private Date createTime;
	
	// 发货客户编码、
	private String deliveryCustomerCode;

	// 发货人联系人
	private String deliveryCustomerContact;

	// 订单状态、
	private String orderStatus;

	// 异常原因
	private String failReason;
	
	//查询开始时间
	private Date startTime;
	
	//查询结束时间
	private Date endTime;
	
	
	public String getLabelPushStatus() {
		return labelPushStatus;
	}

	public void setLabelPushStatus(String labelPushStatus) {
		this.labelPushStatus = labelPushStatus;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getReceiveOrgName() {
		return receiveOrgName;
	}

	public void setReceiveOrgName(String receiveOrgName) {
		this.receiveOrgName = receiveOrgName;
	}

	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getScanTime() {
		return scanTime;
	}

	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}


	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getCustomerPickupOrgName() {
		return customerPickupOrgName;
	}

	public void setCustomerPickupOrgName(String customerPickupOrgName) {
		this.customerPickupOrgName = customerPickupOrgName;
	}


	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getWaybillStatus() {
		return waybillStatus;
	}

	public void setWaybillStatus(String waybillStatus) {
		this.waybillStatus = waybillStatus;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public List<String> getWaybillNoList() {
		return waybillNoList;
	}

	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}

	public List<String> getOrderNoList() {
		return orderNoList;
	}

	public void setOrderNoList(List<String> orderNoList) {
		this.orderNoList = orderNoList;
	}

	public List<String> getCustomerCodeList() {
		return customerCodeList;
	}

	public void setCustomerCodeList(List<String> customerCodeList) {
		this.customerCodeList = customerCodeList;
	}
}
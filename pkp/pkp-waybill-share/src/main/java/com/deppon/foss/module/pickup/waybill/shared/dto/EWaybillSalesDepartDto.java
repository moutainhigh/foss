package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 电子面单查询结果集
 * @author Foss-105888-Zhangxingwang
 * @date 2015-1-13 10:30:31
 */
public class EWaybillSalesDepartDto implements Serializable {
	private static final long serialVersionUID = 1L;

	// 运单号、
	private String waybillNo;

	// 订单号、
	private String orderNo;

	// 开单部门、
	private String createOrgName;

	// 开单部门编码
	private String createOrgCode;

	// 收入部门、
	private String receiveOrgName;

	// 收入部门编码
	private String receiveOrgCode;

	// 下单时间、
	private Date billTime;

	// 开单时间、
	private Date createTime;

	// 扫描时间、
	private Date scanTime;
	
	//分拣转运场
	private String sortDepot;
	
	// 客户编码、
	private String deliveryCustomerCode;

	// 发货人、
	private String deliveryCustomerContact;

	// 收货人、
	private String receiveCustomerContact;

	// 出发城市、
	private String departCity;

	// 目的城市、
	private String destinationCity;
	
	// 提货网点编码、
	private String customerPickupOrgCode;
	
	// 提货网点名称、
	private String customerPickupOrgName;

	// 快递员工号、
	private String driverCode;

	// 订单渠道、
	private String orderChannel;

	// 订单状态、
	private String orderStatus;

	// 运单状态、
	private String waybillStatus;

	// 运输性质编码、
	private String productCode;
	
	//运输性质名称、
	private String productName;

	// 订单重量、
	private BigDecimal orderWeight;

	// 运单重量、
	private BigDecimal waybillWeight;
	
	// 订单重量、
	private BigDecimal orderVolume;

	// 运单重量、
	private BigDecimal waybillVolume;

	// 订单件数、
	Integer orderGoodTotal;
	
	// 运单件数、
	Integer waybillGoodTotal;

	// 付款方式、
	private String paidMethod;

	// 运费、
	private BigDecimal totalFee;

	// 代收货款、
	private BigDecimal codeAmount;

	// 订单退回原因、
	private String orderReturnReason;

	// 运单生成失败原因
	private String createFailReason;
	
	// 库存部门编码
    private String operateorgcode;

	// 库存部门名称
	private String operateorgname;
	
	// 卸车人工号
	 private String clerkcode;
	// 卸车人名称
   private String clerkname;

	// 卸车时间
   private Date clerktime ;
   
   //待补录、待激活状态
   private String pendingType;
   

	//运单激活状态
	private String waybillActive;
	
   
	public String getSortDepot() {
		return sortDepot;
	}

	public void setSortDepot(String sortDepot) {
		this.sortDepot = sortDepot;
	}

	public String getPendingType() {
		return pendingType;
	}

	public void setPendingType(String pendingType) {
		this.pendingType = pendingType;
	}

	public String getWaybillActive() {
		return waybillActive;
	}

	public void setWaybillActive(String waybillActive) {
		this.waybillActive = waybillActive;
	}

public Date getClerktime() {
	return clerktime;
}

public void setClerktime(Date clerktime) {
	this.clerktime = clerktime;
}

public String getOperateorgcode() {
		return operateorgcode;
	}

	public void setOperateorgcode(String operateorgcode) {
		this.operateorgcode = operateorgcode;
	}

	public String getClerkcode() {
		return clerkcode;
	}

	public void setClerkcode(String clerkcode) {
		this.clerkcode = clerkcode;
	}
	
	public String getOperateorgname() {
		return operateorgname;
	}

	public void setOperateorgname(String operateorgname) {
		this.operateorgname = operateorgname;
	}

	public String getClerkname() {
		return clerkname;
	}

	public void setClerkname(String clerkname) {
		this.clerkname = clerkname;
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

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public String getDepartCity() {
		return departCity;
	}

	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}

	public String getDestinationCity() {
		return destinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
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

	public String getDriverCode() {
		return driverCode;
	}

	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getOrderWeight() {
		return orderWeight;
	}

	public void setOrderWeight(BigDecimal orderWeight) {
		this.orderWeight = orderWeight;
	}

	public BigDecimal getWaybillWeight() {
		return waybillWeight;
	}

	public void setWaybillWeight(BigDecimal waybillWeight) {
		this.waybillWeight = waybillWeight;
	}

	public BigDecimal getOrderVolume() {
		return orderVolume;
	}

	public void setOrderVolume(BigDecimal orderVolume) {
		this.orderVolume = orderVolume;
	}

	public BigDecimal getWaybillVolume() {
		return waybillVolume;
	}

	public void setWaybillVolume(BigDecimal waybillVolume) {
		this.waybillVolume = waybillVolume;
	}

	public Integer getOrderGoodTotal() {
		return orderGoodTotal;
	}

	public void setOrderGoodTotal(Integer orderGoodTotal) {
		this.orderGoodTotal = orderGoodTotal;
	}

	public Integer getWaybillGoodTotal() {
		return waybillGoodTotal;
	}

	public void setWaybillGoodTotal(Integer waybillGoodTotal) {
		this.waybillGoodTotal = waybillGoodTotal;
	}

	public String getPaidMethod() {
		return paidMethod;
	}

	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getCodeAmount() {
		return codeAmount;
	}

	public void setCodeAmount(BigDecimal codeAmount) {
		this.codeAmount = codeAmount;
	}

	public String getOrderReturnReason() {
		return orderReturnReason;
	}

	public void setOrderReturnReason(String orderReturnReason) {
		this.orderReturnReason = orderReturnReason;
	}

	public String getCreateFailReason() {
		return createFailReason;
	}

	public void setCreateFailReason(String createFailReason) {
		this.createFailReason = createFailReason;
	}
}
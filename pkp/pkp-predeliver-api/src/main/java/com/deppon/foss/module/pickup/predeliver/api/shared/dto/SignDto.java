/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/dto/SignDto.java
 * 
 * FILE NAME        	: SignDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/***
 * 签收出库Dto
 * @author foss-meiying
 * @date 2012-10-17 上午11:03:31
 * @since
 * @version
 */
public class SignDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/** 
	 * id
	 */
	private String id;
	/** 
	 * 运单号
	 */
	private String waybillNo;
	/** 
	 * 到达联编号
	 */
	private String arrivesheetNo;
	/**
	 *  收货人
	 */
	private String receiveCustomerName;
	/**
	 *  收货人电话
	 */
	private String receiveCustomerPhone;
	/**
	 *  收货人手机号码
	 */
	private String receiveCustomerMobilephone;
	/**
	 *  到达联状态
	 */
	private String status;
	/**
	 *  到达联是否有效
	 */
	private String active;
	/**
	 *  存放多个到达联状态
	 */
	private String[] ArriveSheetstatus;
	/**
	 *  运单结清状态
	 */
	private String settleStatus;
	/**
	 *  结清时间起
	 */
	private Date settleTimeStart;
	/** 
	 * 结清时间止
	 */
	private Date settleTimeEnd;
	/**
	 *  最终配载部门（判断是否为本部门）
	 */
	private String lastLoadOrgCode;
	/**
	 *  是否整车运单
	 */
	private String isWholeVehicle;
	/**
	 *  运输性质
	 */
	private String productCode;
	/**
	 *  提货人名称
	 */
	private String deliverymanName;
	/**
	 *  证件类型
	 */
	private String identifyType;
	/**
	 *  证件号码
	 */
	private String identifyCode;
	/**
	 *  是否作废
	 */
	private String destroyed;
	/**
	 * 到达联件数
	 */
	private Integer arriveSheetGoodsQty; 
	/**
	 * 最后库存code
	 */
	private String endStockOrgCode;
	
	/**
	 * 库区
	 */
	private String goodsAreaCode;
	/**
	 * 快递零担库区
	 */
	private List<String> goodsAreaCodes;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 *  系统时间
	 */
	private Date systemDate;
	/**
	 * 提货方式
	 */
	private String receiveMethod;
	
	/**
	 * 货物总件数
	 */
	private Integer goodsQtyTotal;
	/**
	 * 发货人手机
	 */
	private String deliveryCustomerMobilephone;
	/**
	 * 发货客户联系人
	 */
	private String deliveryCustomerContact;
	/**
	 * 收货客户联系人
	 */
	private String receiveCustomerContact;
	/**
	 * 收货部门(出发部门)
	 */
	private String receiveOrgCode;
	/**
	 * 保价声明价值
	 */
	private BigDecimal insuranceAmount;
	/**
	 * 代收货款
	 */
	private BigDecimal codAmount;
	/**
	 * 总费用
	 */
	private BigDecimal totalFee;
	/**
	 *  运输性质集合
	 */
	private List<String> productCodeList;
	/**
	 * 开单时间
	 */
	private Date billTime;
	/**
	 * 开单到达部门
	 */
	private String customerPickupOrgCode;
	
	/**
	 * 开单提交方式 
	 */
	private String pendingType;

	/**

	 * 是否快递
	 */
	private String isExpress;
	/**

	/**
	 * 发货客户编码
	 */
	private String deliveryCustomerCode;
	/**
	 * 收货客户编码
	 */
	private String receiveCustomerCode;
	/**
	 * 发货客户名称
	 */
	private String deliveryCustomerName;
	/**
	 * 预付金额
	 */
	private BigDecimal prePayAmount;
	/**
	 * 发货省份
	 */
	private String deliveryCustomerProvCode;
	/**
	 * 子母件单号List
	 */
	private List<String> czmWaybillNoList;
	
	/**
	 * 入库时间起(ptp合伙人)
	 */
	private Date inStockTimeStart;
	
	/**
	 * 入库时间止(ptp合伙人)
	 */
	private Date inStockTimeEnd;
	/**
	 * 合伙人初始化数据过滤410几点前后运单
	 */
	private Date conBillTime;
	

	/**
	 * Gets the 最后库存code.
	 *
	 * @return the 最后库存code
	 */
	public String getEndStockOrgCode() {
		return endStockOrgCode;
	}

	/**
	 * Sets the 最后库存code.
	 *
	 * @param endStockOrgCode the new 最后库存code
	 */
	public void setEndStockOrgCode(String endStockOrgCode) {
		this.endStockOrgCode = endStockOrgCode;
	}

	/**
	 * Gets the 库区.
	 *
	 * @return the 库区
	 */
	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	/**
	 * Sets the 库区.
	 *
	 * @param goodsAreaCode the new 库区
	 */
	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	/**
	 * Gets the 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * Sets the 运单号.
	 *
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * Gets the 收货人.
	 *
	 * @return the 收货人
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * Sets the 收货人.
	 *
	 * @param receiveCustomerName the new 收货人
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * Gets the 收货人电话.
	 *
	 * @return the 收货人电话
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * Sets the 收货人电话.
	 *
	 * @param receiveCustomerPhone the new 收货人电话
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * Gets the 结清时间起.
	 *
	 * @return the 结清时间起
	 */
	public Date getSettleTimeStart() {
		return settleTimeStart;
	}

	/**
	 * Sets the 结清时间起.
	 *
	 * @param settleTimeStart the new 结清时间起
	 */
	public void setSettleTimeStart(Date settleTimeStart) {
		this.settleTimeStart = settleTimeStart;
	}

	/**
	 * Gets the 结清时间止.
	 *
	 * @return the 结清时间止
	 */
	public Date getSettleTimeEnd() {
		return settleTimeEnd;
	}

	/**
	 * Sets the 结清时间止.
	 *
	 * @param settleTimeEnd the new 结清时间止
	 */
	public void setSettleTimeEnd(Date settleTimeEnd) {
		this.settleTimeEnd = settleTimeEnd;
	}

	/**
	 * Gets the 收货人手机号码.
	 *
	 * @return the 收货人手机号码
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * Sets the 收货人手机号码.
	 *
	 * @param receiveCustomerMobilephone the new 收货人手机号码
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * Gets the 到达联编号.
	 *
	 * @return the 到达联编号
	 */
	public String getArrivesheetNo() {
		return arrivesheetNo;
	}

	/**
	 * Sets the 到达联编号.
	 *
	 * @param arrivesheetNo the new 到达联编号
	 */
	public void setArrivesheetNo(String arrivesheetNo) {
		this.arrivesheetNo = arrivesheetNo;
	}

	/**
	 * Gets the 到达联状态.
	 *
	 * @return the 到达联状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the 到达联状态.
	 *
	 * @param status the new 到达联状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the 运单结清状态.
	 *
	 * @return the 运单结清状态
	 */
	public String getSettleStatus() {
		return settleStatus;
	}

	/**
	 * Sets the 运单结清状态.
	 *
	 * @param settleStatus the new 运单结清状态
	 */
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}

	/**
	 * Gets the 最终配载部门（判断是否为本部门）.
	 *
	 * @return the 最终配载部门（判断是否为本部门）
	 */
	public String getLastLoadOrgCode() {
		return lastLoadOrgCode;
	}

	/**
	 * Sets the 最终配载部门（判断是否为本部门）.
	 *
	 * @param lastLoadOrgCode the new 最终配载部门（判断是否为本部门）
	 */
	public void setLastLoadOrgCode(String lastLoadOrgCode) {
		this.lastLoadOrgCode = lastLoadOrgCode;
	}

	/**
	 * 
	 *
	 * @return 
	 */
	public String[] getArriveSheetstatus() {
		return ArriveSheetstatus;
	}

	/**
	 * 
	 *
	 * @param arriveSheetstatus 
	 */
	public void setArriveSheetstatus(String[] arriveSheetstatus) {
		ArriveSheetstatus = arriveSheetstatus;
	}

	/**
	 * Gets the 到达联是否有效.
	 *
	 * @return the 到达联是否有效
	 */
	public String getActive() {
		return active;
	}

	/**
	 * Sets the 到达联是否有效.
	 *
	 * @param active the new 到达联是否有效
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the 是否整车运单.
	 *
	 * @return the 是否整车运单
	 */
	public String getIsWholeVehicle() {
		return isWholeVehicle;
	}

	/**
	 * Sets the 是否整车运单.
	 *
	 * @param isWholeVehicle the new 是否整车运单
	 */
	public void setIsWholeVehicle(String isWholeVehicle) {
		this.isWholeVehicle = isWholeVehicle;
	}

	/**
	 * Gets the 运输性质.
	 *
	 * @return the 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * Sets the 运输性质.
	 *
	 * @param productCode the new 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Gets the 提货人名称.
	 *
	 * @return the 提货人名称
	 */
	public String getDeliverymanName() {
		return deliverymanName;
	}

	/**
	 * Sets the 提货人名称.
	 *
	 * @param deliverymanName the new 提货人名称
	 */
	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
	}

	/**
	 * Gets the 证件类型.
	 *
	 * @return the 证件类型
	 */
	public String getIdentifyType() {
		return identifyType;
	}

	/**
	 * Sets the 证件类型.
	 *
	 * @param identifyType the new 证件类型
	 */
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	/**
	 * Gets the 证件号码.
	 *
	 * @return the 证件号码
	 */
	public String getIdentifyCode() {
		return identifyCode;
	}

	/**
	 * Sets the 证件号码.
	 *
	 * @param identifyCode the new 证件号码
	 */
	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	/**
	 * Gets the 是否作废.
	 *
	 * @return the 是否作废
	 */
	public String getDestroyed() {
		return destroyed;
	}

	/**
	 * Sets the 是否作废.
	 *
	 * @param destroyed the new 是否作废
	 */
	public void setDestroyed(String destroyed) {
		this.destroyed = destroyed;
	}

	/**
	 * Gets the 到达联件数.
	 *
	 * @return the 到达联件数
	 */
	public Integer getArriveSheetGoodsQty() {
		return arriveSheetGoodsQty;
	}

	/**
	 * Sets the 到达联件数.
	 *
	 * @param arriveSheetGoodsQty the new 到达联件数
	 */
	public void setArriveSheetGoodsQty(Integer arriveSheetGoodsQty) {
		this.arriveSheetGoodsQty = arriveSheetGoodsQty;
	}

	/**
	 * Gets the 订单号.
	 *
	 * @return the 订单号
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * Sets the 订单号.
	 *
	 * @param orderNo the new 订单号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * Gets the 系统时间.
	 *
	 * @return the 系统时间
	 */
	public Date getSystemDate() {
		return systemDate;
	}

	/**
	 * Sets the 系统时间.
	 *
	 * @param systemDate the new 系统时间
	 */
	public void setSystemDate(Date systemDate) {
		this.systemDate = systemDate;
	}

	/**
	 * Gets the 提货方式.
	 *
	 * @return the 提货方式
	 */
	public String getReceiveMethod() {
		return receiveMethod;
	}

	/**
	 * Sets the 提货方式.
	 *
	 * @param receiveMethod the new 提货方式
	 */
	public void setReceiveMethod(String receiveMethod) {
		this.receiveMethod = receiveMethod;
	}

	/**
	 * Gets the 货物总件数.
	 *
	 * @return the 货物总件数
	 */
	public Integer getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * Sets the 货物总件数.
	 *
	 * @param goodsQtyTotal the new 货物总件数
	 */
	public void setGoodsQtyTotal(Integer goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * Gets the 发货人手机.
	 *
	 * @return the 发货人手机
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	/**
	 * Sets the 发货人手机.
	 *
	 * @param deliveryCustomerMobilephone the new 发货人手机
	 */
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	/**
	 * Gets the 发货客户联系人.
	 *
	 * @return the 发货客户联系人
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * Sets the 发货客户联系人.
	 *
	 * @param deliveryCustomerContact the new 发货客户联系人
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * Gets the 收货客户联系人.
	 *
	 * @return the 收货客户联系人
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * Sets the 收货客户联系人.
	 *
	 * @param receiveCustomerContact the new 收货客户联系人
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * Gets the 收货部门(出发部门).
	 *
	 * @return the 收货部门(出发部门)
	 */
	public String getReceiveOrgCode() {
		return receiveOrgCode;
	}

	/**
	 * Sets the 收货部门(出发部门).
	 *
	 * @param receiveOrgCode the new 收货部门(出发部门)
	 */
	public void setReceiveOrgCode(String receiveOrgCode) {
		this.receiveOrgCode = receiveOrgCode;
	}

	/**
	 * Gets the 保价声明价值.
	 *
	 * @return the 保价声明价值
	 */
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * Sets the 保价声明价值.
	 *
	 * @param insuranceAmount the new 保价声明价值
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	/**
	 * Gets the 代收货款.
	 *
	 * @return the 代收货款
	 */
	public BigDecimal getCodAmount() {
		return codAmount;
	}

	/**
	 * Sets the 代收货款.
	 *
	 * @param codAmount the new 代收货款
	 */
	public void setCodAmount(BigDecimal codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * Gets the 总费用.
	 *
	 * @return the 总费用
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	/**
	 * Sets the 总费用.
	 *
	 * @param totalFee the new 总费用
	 */
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public List<String> getProductCodeList() {
		return productCodeList;
	}

	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	public String getPendingType() {
		return pendingType;
	}

	public void setPendingType(String pendingType) {
		this.pendingType = pendingType;
	}

	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}
	
	public String getIsExpress() {
		return isExpress;
	}

	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}

	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	public BigDecimal getPrePayAmount() {
		return prePayAmount;
	}

	public void setPrePayAmount(BigDecimal prePayAmount) {
		this.prePayAmount = prePayAmount;
	}

	public String getDeliveryCustomerProvCode() {
		return deliveryCustomerProvCode;
	}

	public void setDeliveryCustomerProvCode(String deliveryCustomerProvCode) {
		this.deliveryCustomerProvCode = deliveryCustomerProvCode;
	}
	public List<String> getGoodsAreaCodes() {
		return goodsAreaCodes;
	}

	public void setGoodsAreaCodes(List<String> goodsAreaCodes) {
		this.goodsAreaCodes = goodsAreaCodes;
	}
	public List<String> getCzmWaybillNoList() {
		return czmWaybillNoList;
	}

	public void setCzmWaybillNoList(List<String> czmWaybillNoList) {
		this.czmWaybillNoList = czmWaybillNoList;
	}

	public Date getInStockTimeStart() {
		return inStockTimeStart;
	}

	public void setInStockTimeStart(Date inStockTimeStart) {
		this.inStockTimeStart = inStockTimeStart;
	}

	public Date getInStockTimeEnd() {
		return inStockTimeEnd;
	}

	public void setInStockTimeEnd(Date inStockTimeEnd) {
		this.inStockTimeEnd = inStockTimeEnd;
	}

	public Date getConBillTime() {
		return conBillTime;
	}

	public void setConBillTime(Date conBillTime) {
		this.conBillTime = conBillTime;
	}
	
}
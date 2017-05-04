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
 * PROJECT NAME	: pkp-pickup-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/shared/dto/TrackingWaybillDto.java
 * 
 * FILE NAME        	: TrackingWaybillDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 运单跟踪列表Dto
 * 
 * @author ibm-wangfei
 * @date Dec 29, 2012 1:44:31 PM
 */
public class TrackingWaybillDto implements Serializable {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -1611635601245480763L;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 开单时间 ==收货日期
	 */
	private Date billTime;

	/**
	 * 目的站编码
	 */
	private String targetOrgCode;

	/**
	 * 目的站名称
	 */
	private String targetOrgName;

	/**
	 * 运输性质
	 */
	private String productCode;

	/**
	 * 发货客户手机 == 托运人电话
	 */
	private String deliveryCustomerMobilephone;

	/**
	 * 发货客户电话
	 */
	private String deliveryCustomerPhone;

	/**
	 * 发货客户联系人 == 托运人
	 */
	private String deliveryCustomerContact;

	/**
	 * 发货客户编码 == 页面上 客户编码
	 */
	private String deliveryCustomerCode;

	/**
	 * 收货客户手机 ==收货人电话
	 */
	private String receiveCustomerMobilephone;

	/**
	 * 收货客户电话
	 */
	private String receiveCustomerPhone;

	/**
	 * 收货客户联系人 ==收货人
	 */
	private String receiveCustomerContact;

	/**
	 * 收货客户编码
	 */
	private String receiveCustomerCode;

	/**
	 * 是否配置
	 */
	private String stowageActive;
	
	/**
	 * 货区编号
	 */
	private String goodsAreaCode;
	
	/**
	 * 库存件数
	 */
	private Integer stockGoodsCount;

	/**
	 * 签收时间
	 */
	private Date signTime;

	/**
	 * 库存外场 ==库存专线
	 */
	private String stock;

	/**
	 * 在库天数
	 */
	private String storageDay;

	/**
	 * 签收情况 == 签收状态
	 */
	private String situation;

	/**
	 * 库存状态
	 */
	private String stockStatus;

	/**
	 * 签收人
	 */
	private String deliverymanName;

	/**
	 * 返单类型
	 */
	private String returnBillType;

	/**
	 * 返单状态
	 */
	private String returnBillStatus;

	/**
	 * 返单快递单号
	 */
	private String expressNo;
	
	/**
	 * 运单类型
	 */
	
	private String waybillType;
	/**
	 *  收货人是否大客户.
	 */
	private String receiveBigCustomer; 
	private String deliveryBigCustomer;
	
	// 2016-03-23 add 运单跟踪列表Dto 新的回显字段: 运输状态，出发客户群
	/**
	 * 开单管理--跟踪运单--查询结果回显字段：出发客户群 出发客户群
	 * 
	 * 2016-04-13 by zhangws
	 */
	private String sendCustomerGroup;
	/**
	 * 开单管理--跟踪运单--查询结果回显字段：运输状态 运输状态
	 * 
	 * 2016-04-13 by zhangws
	 */
	private String wayBillStatus;
	/**
	 * 预计到达时间 供中转调用 预计达到时间
	 * 
	 * 2016-04-26 by zhangws
	 */
	private Date preArriveTime;

	public Date getPreArriveTime() {
		return preArriveTime;
	}

	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}

	public String getWayBillStatus() {
		return wayBillStatus;
	}

	public void setWayBillStatus(String wayBillStatus) {
		this.wayBillStatus = wayBillStatus;
	}

	public String getSendCustomerGroup() {
		return sendCustomerGroup;
	}

	public void setSendCustomerGroup(String sendCustomerGroup) {
		this.sendCustomerGroup = sendCustomerGroup;
	}

	public String getWaybillType() {
		return waybillType;
	}

	public void setWaybillType(String waybillType) {
		this.waybillType = waybillType;
	}

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
	/**
	 * 获取 运单号.
	 * 
	 * @return the 运单号
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 运单号.
	 * 
	 * @param waybillNo the new 运单号
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 开单时间.
	 * 
	 * @return the 开单时间
	 */
	public Date getBillTime() {
		return billTime;
	}

	/**
	 * 设置 开单时间.
	 * 
	 * @param billTime the new 开单时间
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	/**
	 * 获取 目的站编码.
	 * 
	 * @return the 目的站编码
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * 设置 目的站编码.
	 * 
	 * @param targetOrgCode the new 目的站编码
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * 获取 运输性质.
	 * 
	 * @return the 运输性质
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * 设置 运输性质.
	 * 
	 * @param productCode the new 运输性质
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * 获取 发货客户手机.
	 * 
	 * @return the 发货客户手机
	 */
	public String getDeliveryCustomerMobilephone() {
		return deliveryCustomerMobilephone;
	}

	/**
	 * 设置 发货客户手机.
	 * 
	 * @param deliveryCustomerMobilephone the new 发货客户手机
	 */
	public void setDeliveryCustomerMobilephone(String deliveryCustomerMobilephone) {
		this.deliveryCustomerMobilephone = deliveryCustomerMobilephone;
	}

	/**
	 * 获取 发货客户电话.
	 * 
	 * @return the 发货客户电话
	 */
	public String getDeliveryCustomerPhone() {
		return deliveryCustomerPhone;
	}

	/**
	 * 设置 发货客户电话.
	 * 
	 * @param deliveryCustomerPhone the new 发货客户电话
	 */
	public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
		this.deliveryCustomerPhone = deliveryCustomerPhone;
	}

	/**
	 * 获取 发货客户联系人.
	 * 
	 * @return the 发货客户联系人
	 */
	public String getDeliveryCustomerContact() {
		return deliveryCustomerContact;
	}

	/**
	 * 设置 发货客户联系人.
	 * 
	 * @param deliveryCustomerContact the new 发货客户联系人
	 */
	public void setDeliveryCustomerContact(String deliveryCustomerContact) {
		this.deliveryCustomerContact = deliveryCustomerContact;
	}

	/**
	 * 获取 发货客户编码.
	 * 
	 * @return the 发货客户编码
	 */
	public String getDeliveryCustomerCode() {
		return deliveryCustomerCode;
	}

	/**
	 * 设置 发货客户编码.
	 * 
	 * @param deliveryCustomerCode the new 发货客户编码
	 */
	public void setDeliveryCustomerCode(String deliveryCustomerCode) {
		this.deliveryCustomerCode = deliveryCustomerCode;
	}

	/**
	 * 获取 收货客户手机.
	 * 
	 * @return the 收货客户手机
	 */
	public String getReceiveCustomerMobilephone() {
		return receiveCustomerMobilephone;
	}

	/**
	 * 设置 收货客户手机.
	 * 
	 * @param receiveCustomerMobilephone the new 收货客户手机
	 */
	public void setReceiveCustomerMobilephone(String receiveCustomerMobilephone) {
		this.receiveCustomerMobilephone = receiveCustomerMobilephone;
	}

	/**
	 * 获取 收货客户电话.
	 * 
	 * @return the 收货客户电话
	 */
	public String getReceiveCustomerPhone() {
		return receiveCustomerPhone;
	}

	/**
	 * 设置 收货客户电话.
	 * 
	 * @param receiveCustomerPhone the new 收货客户电话
	 */
	public void setReceiveCustomerPhone(String receiveCustomerPhone) {
		this.receiveCustomerPhone = receiveCustomerPhone;
	}

	/**
	 * 获取 收货客户联系人.
	 * 
	 * @return the 收货客户联系人
	 */
	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	/**
	 * 设置 收货客户联系人.
	 * 
	 * @param receiveCustomerContact the new 收货客户联系人
	 */
	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	/**
	 * 获取 收货客户编码.
	 * 
	 * @return the 收货客户编码
	 */
	public String getReceiveCustomerCode() {
		return receiveCustomerCode;
	}

	/**
	 * 设置 收货客户编码.
	 * 
	 * @param receiveCustomerCode the new 收货客户编码
	 */
	public void setReceiveCustomerCode(String receiveCustomerCode) {
		this.receiveCustomerCode = receiveCustomerCode;
	}

	/**
	 * 获取 是否配置.
	 * 
	 * @return the 是否配置
	 */
	public String getStowageActive() {
		return stowageActive;
	}

	/**
	 * 设置 是否配置.
	 * 
	 * @param stowageActive the new 是否配置
	 */
	public void setStowageActive(String stowageActive) {
		this.stowageActive = stowageActive;
	}

	/**
	 * 获取 签收时间.
	 * 
	 * @return the 签收时间
	 */
	public Date getSignTime() {
		return signTime;
	}

	/**
	 * 设置 签收时间.
	 * 
	 * @param signTime the new 签收时间
	 */
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	/**
	 * 获取 库存外场.
	 * 
	 * @return the 库存外场
	 */
	public String getStock() {
		return stock;
	}

	/**
	 * 设置 库存外场.
	 * 
	 * @param stock the new 库存外场
	 */
	public void setStock(String stock) {
		this.stock = stock;
	}

	/**
	 * 获取 在库天数.
	 * 
	 * @return the 在库天数
	 */
	public String getStorageDay() {
		return storageDay;
	}

	/**
	 * 设置 在库天数.
	 * 
	 * @param storageDay the new 在库天数
	 */
	public void setStorageDay(String storageDay) {
		this.storageDay = storageDay;
	}

	/**
	 * 获取 签收情况.
	 * 
	 * @return the 签收情况
	 */
	public String getSituation() {
		return situation;
	}

	/**
	 * 设置 签收情况.
	 * 
	 * @param situation the new 签收情况
	 */
	public void setSituation(String situation) {
		this.situation = situation;
	}

	/**
	 * 获取 库存状态.
	 * 
	 * @return the 库存状态
	 */
	public String getStockStatus() {
		return stockStatus;
	}

	/**
	 * 设置 库存状态.
	 * 
	 * @param stockStatus the new 库存状态
	 */
	public void setStockStatus(String stockStatus) {
		this.stockStatus = stockStatus;
	}

	/**
	 * 获取 签收人.
	 * 
	 * @return the 签收人
	 */
	public String getDeliverymanName() {
		return deliverymanName;
	}

	/**
	 * 设置 签收人.
	 * 
	 * @param deliverymanName the new 签收人
	 */
	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
	}

	/**
	 * 获取 返单类型.
	 * 
	 * @return the 返单类型
	 */
	public String getReturnBillType() {
		return returnBillType;
	}

	/**
	 * 设置 返单类型.
	 * 
	 * @param returnBillType the new 返单类型
	 */
	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	/**
	 * 获取 返单快递单号.
	 * 
	 * @return the 返单快递单号
	 */
	public String getExpressNo() {
		return expressNo;
	}

	/**
	 * 设置 返单快递单号.
	 * 
	 * @param expressNo the new 返单快递单号
	 */
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	/**
	 * 获取 目的站名称.
	 * 
	 * @return the 目的站名称
	 */
	public String getTargetOrgName() {
		return targetOrgName;
	}

	/**
	 * 设置 目的站名称.
	 * 
	 * @param targetOrgName the new 目的站名称
	 */
	public void setTargetOrgName(String targetOrgName) {
		this.targetOrgName = targetOrgName;
	}

	public String getReturnBillStatus() {
		return returnBillStatus;
	}

	public void setReturnBillStatus(String returnBillStatus) {
		this.returnBillStatus = returnBillStatus;
	}

	public String getGoodsAreaCode() {
		return goodsAreaCode;
	}

	public void setGoodsAreaCode(String goodsAreaCode) {
		this.goodsAreaCode = goodsAreaCode;
	}

	public Integer getStockGoodsCount() {
		return stockGoodsCount;
	}

	public void setStockGoodsCount(Integer stockGoodsCount) {
		this.stockGoodsCount = stockGoodsCount;
	}

	public String getDeliveryBigCustomer() {
		return deliveryBigCustomer;
	}

	public void setDeliveryBigCustomer(String deliveryBigCustomer) {
		this.deliveryBigCustomer = deliveryBigCustomer;
	}
	
	
}
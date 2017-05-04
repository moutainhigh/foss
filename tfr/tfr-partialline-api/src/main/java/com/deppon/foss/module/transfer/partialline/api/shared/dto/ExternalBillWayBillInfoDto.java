/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 偏线外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/shared/dto/ExternalBillWayBillInfoDto.java
 * 
 *  FILE NAME     :ExternalBillWayBillInfoDto.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.partialline.api.shared.dto;

import java.math.BigDecimal;

/**
 * 外发单运单信息dto
 * 
 * @author dp-zhongyubing
 * @date 2012-12-25 下午6:17:39
 */
public class ExternalBillWayBillInfoDto implements java.io.Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -9129577169797204231L;
	// 运单信息
	/**
	 * 运单号WAYBILL_NO
	 */
	private String waybillNo;
	/**
	 * 付款方式PAID_METHOD
	 */
	private String paidMethod;
	/**
	 * 付款方式CODE
	 */
	private String paidMethodCode;
	/**
	 * 到付金额(运单)TO_PAY_AMOUNT
	 */
	private BigDecimal toPayAmount;
	/**
	 * 目的站//接送货运单TARGET_ORG_CODE
	 */
	private String targetOrgCode;
	/**
	 * 收货日期//开单时间CREATE_TIME
	 */
	private String handGoodsTime;
	/**
	 * 收货部门//开单组织CREATE_ORG_CODE
	 */
	private String createOrgCode;
	/**
	 * 重量//货物总重量GOODS_WEIGHT_TOTAL
	 */
	private BigDecimal goodsWeightTotal;
	/**
	 * 体积//货物总体积GOODS_VOLUME_TOTAL
	 */
	private BigDecimal goodsVolumeTotal;
	/**
	 * 件数//货物总件数GOODS_QTY_TOTAL
	 */
	private BigDecimal goodsQtyTotal;
	/**
	 * 运费//公布价运费TRANSPORT_FEE
	 */
	private BigDecimal transportFee;
	/**
	 * 货物名称//货物名称GOODS_NAME
	 */
	private String goodsName;
	/**
	 * 保险费//保价费INSURANCE__FEE
	 */
	private BigDecimal insuranceFee;
	/**
	 * 包装//货物包装GOODS_PACKAGE
	 */
	private String goodsPackage;
	/**
	 * 托运人姓名//发货客户名称DELIVERY_CUSTOMER_NAME
	 */
	private String deliveryCustomerName;
	/**
	 * 保险价值//保价声明价值INSURANCE_AMOUNT
	 */
	private BigDecimal insuranceAmount;
	/**
	 * 运输事项//对内备注,对外备注合体
	 */
	private String yunshushixiang;
	/**
	 * 开单金额//总费用TOTAL_FEE
	 */
	private BigDecimal totalFee;
	/**
	 * 代收货款//代收货款COD_AMOUNT
	 */
	private String codAmount;
	/**
	 * 收货客户//收货客户名称RECEIVE_CUSTOMER_NAME
	 */
	private String receiveCustomerName;
	/**
	 * 是否自提（提货方式）
	 */
	private String beAutoDelivery;
	/**
	 * 提货网点CUSTOMER_PICKUP_ORG_CODE
	 */
	private String customerPickupOrgCode;
	/**
	 * 币种
	 */
	private String currencyCode;
	
	/**
	 * 外发代理费
	 */
	private BigDecimal externalAgencyFee;
	

	/**
	 * 送货费
	 */
	private BigDecimal deliveryFee;
	
	// 代理信息
	/**
	 * 代理电话//联系电话T_BAS_BUSINESS_PARTNER.CONTACT_PHONE
	 */
	private String partnerContactPhone;
	/**
	 * 外发代理//外发代理公司的名称T_BAS_BUSINESS_PARTNER.AGENT_COMPANY_NAME
	 */
	private String agentCompanyName;
	/**
	 * 到达网点//代理网点名称(综合外部网点)T_BAS_OUTER_BRANCH.AGENT_DEPT_NAME
	 */
	private String agentDeptName;
	/**
	 * 到达网点电话//联系电话(综合外部网点)T_BAS_OUTER_BRANCH.CONTACT_PHONE
	 */
	private String contactPhone;
	/**
	 * 到达网点地址//网点地址(综合外部网点)T_BAS_OUTER_BRANCH.ADDRESS
	 */
	private String address;

	/**
	 * 收货客户联系人
	 */
	private String receiveCustomerContact;

	/**
	 * 其他金额
	 */
	private String otherFee;
	
	/**
	 * 获取 运单号WAYBILL_NO.
	 * 
	 * @return the 运单号WAYBILL_NO
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * 设置 运单号WAYBILL_NO.
	 * 
	 * @param waybillNo
	 *            the new 运单号WAYBILL_NO
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * 获取 付款方式PAID_METHOD.
	 * 
	 * @return the 付款方式PAID_METHOD
	 */
	public String getPaidMethod() {
		return paidMethod;
	}

	/**
	 * 设置 付款方式PAID_METHOD.
	 * 
	 * @param paidMethod
	 *            the new 付款方式PAID_METHOD
	 */
	public void setPaidMethod(String paidMethod) {
		this.paidMethod = paidMethod;
	}

	/**
	 * 获取 到付金额(运单)TO_PAY_AMOUNT.
	 * 
	 * @return the 到付金额(运单)TO_PAY_AMOUNT
	 */
	public BigDecimal getToPayAmount() {
		return toPayAmount;
	}

	/**
	 * 设置 到付金额(运单)TO_PAY_AMOUNT.
	 * 
	 * @param toPayAmount
	 *            the new 到付金额(运单)TO_PAY_AMOUNT
	 */
	public void setToPayAmount(BigDecimal toPayAmount) {
		this.toPayAmount = toPayAmount;
	}

	/**
	 * 获取 目的站//接送货运单TARGET_ORG_CODE.
	 * 
	 * @return the 目的站//接送货运单TARGET_ORG_CODE
	 */
	public String getTargetOrgCode() {
		return targetOrgCode;
	}

	/**
	 * 设置 目的站//接送货运单TARGET_ORG_CODE.
	 * 
	 * @param targetOrgCode
	 *            the new 目的站//接送货运单TARGET_ORG_CODE
	 */
	public void setTargetOrgCode(String targetOrgCode) {
		this.targetOrgCode = targetOrgCode;
	}

	/**
	 * 获取 收货日期//开单时间CREATE_TIME.
	 * 
	 * @return the 收货日期//开单时间CREATE_TIME
	 */
	public String getHandGoodsTime() {
		return handGoodsTime;
	}

	/**
	 * 设置 收货日期//开单时间CREATE_TIME.
	 * 
	 * @param handGoodsTime
	 *            the new 收货日期//开单时间CREATE_TIME
	 */
	public void setHandGoodsTime(String handGoodsTime) {
		this.handGoodsTime = handGoodsTime;
	}

	/**
	 * 获取 收货部门//开单组织CREATE_ORG_CODE.
	 * 
	 * @return the 收货部门//开单组织CREATE_ORG_CODE
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 设置 收货部门//开单组织CREATE_ORG_CODE.
	 * 
	 * @param createOrgCode
	 *            the new 收货部门//开单组织CREATE_ORG_CODE
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取 重量//货物总重量GOODS_WEIGHT_TOTAL.
	 * 
	 * @return the 重量//货物总重量GOODS_WEIGHT_TOTAL
	 */
	public BigDecimal getGoodsWeightTotal() {
		return goodsWeightTotal;
	}

	/**
	 * 设置 重量//货物总重量GOODS_WEIGHT_TOTAL.
	 * 
	 * @param goodsWeightTotal
	 *            the new 重量//货物总重量GOODS_WEIGHT_TOTAL
	 */
	public void setGoodsWeightTotal(BigDecimal goodsWeightTotal) {
		this.goodsWeightTotal = goodsWeightTotal;
	}

	/**
	 * 获取 体积//货物总体积GOODS_VOLUME_TOTAL.
	 * 
	 * @return the 体积//货物总体积GOODS_VOLUME_TOTAL
	 */
	public BigDecimal getGoodsVolumeTotal() {
		return goodsVolumeTotal;
	}

	/**
	 * 设置 体积//货物总体积GOODS_VOLUME_TOTAL.
	 * 
	 * @param goodsVolumeTotal
	 *            the new 体积//货物总体积GOODS_VOLUME_TOTAL
	 */
	public void setGoodsVolumeTotal(BigDecimal goodsVolumeTotal) {
		this.goodsVolumeTotal = goodsVolumeTotal;
	}

	/**
	 * 获取 件数//货物总件数GOODS_QTY_TOTAL.
	 * 
	 * @return the 件数//货物总件数GOODS_QTY_TOTAL
	 */
	public BigDecimal getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	/**
	 * 设置 件数//货物总件数GOODS_QTY_TOTAL.
	 * 
	 * @param goodsQtyTotal
	 *            the new 件数//货物总件数GOODS_QTY_TOTAL
	 */
	public void setGoodsQtyTotal(BigDecimal goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	/**
	 * 获取 运费//公布价运费TRANSPORT_FEE.
	 * 
	 * @return the 运费//公布价运费TRANSPORT_FEE
	 */
	public BigDecimal getTransportFee() {
		return transportFee;
	}

	/**
	 * 设置 运费//公布价运费TRANSPORT_FEE.
	 * 
	 * @param transportFee
	 *            the new 运费//公布价运费TRANSPORT_FEE
	 */
	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}

	/**
	 * 获取 货物名称//货物名称GOODS_NAME.
	 * 
	 * @return the 货物名称//货物名称GOODS_NAME
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 设置 货物名称//货物名称GOODS_NAME.
	 * 
	 * @param goodsName
	 *            the new 货物名称//货物名称GOODS_NAME
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 获取 保险费//保价费INSURANCE__FEE.
	 * 
	 * @return the 保险费//保价费INSURANCE__FEE
	 */
	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	/**
	 * 设置 保险费//保价费INSURANCE__FEE.
	 * 
	 * @param insuranceFee
	 *            the new 保险费//保价费INSURANCE__FEE
	 */
	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	/**
	 * 获取 包装//货物包装GOODS_PACKAGE.
	 * 
	 * @return the 包装//货物包装GOODS_PACKAGE
	 */
	public String getGoodsPackage() {
		return goodsPackage;
	}

	/**
	 * 设置 包装//货物包装GOODS_PACKAGE.
	 * 
	 * @param goodsPackage
	 *            the new 包装//货物包装GOODS_PACKAGE
	 */
	public void setGoodsPackage(String goodsPackage) {
		this.goodsPackage = goodsPackage;
	}

	/**
	 * 获取 托运人姓名//发货客户名称DELIVERY_CUSTOMER_NAME.
	 * 
	 * @return the 托运人姓名//发货客户名称DELIVERY_CUSTOMER_NAME
	 */
	public String getDeliveryCustomerName() {
		return deliveryCustomerName;
	}

	/**
	 * 设置 托运人姓名//发货客户名称DELIVERY_CUSTOMER_NAME.
	 * 
	 * @param deliveryCustomerName
	 *            the new 托运人姓名//发货客户名称DELIVERY_CUSTOMER_NAME
	 */
	public void setDeliveryCustomerName(String deliveryCustomerName) {
		this.deliveryCustomerName = deliveryCustomerName;
	}

	/**
	 * 获取 保险价值//保价声明价值INSURANCE_AMOUNT.
	 * 
	 * @return the 保险价值//保价声明价值INSURANCE_AMOUNT
	 */
	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * 设置 保险价值//保价声明价值INSURANCE_AMOUNT.
	 * 
	 * @param insuranceAmount
	 *            the new 保险价值//保价声明价值INSURANCE_AMOUNT
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	/**
	 * 获取 运输事项//对内备注,对外备注合体.
	 * 
	 * @return the 运输事项//对内备注,对外备注合体
	 */
	public String getYunshushixiang() {
		return yunshushixiang;
	}

	/**
	 * 设置 运输事项//对内备注,对外备注合体.
	 * 
	 * @param yunshushixiang
	 *            the new 运输事项//对内备注,对外备注合体
	 */
	public void setYunshushixiang(String yunshushixiang) {
		this.yunshushixiang = yunshushixiang;
	}

	/**
	 * 获取 开单金额//总费用TOTAL_FEE.
	 * 
	 * @return the 开单金额//总费用TOTAL_FEE
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
	}

	/**
	 * 设置 开单金额//总费用TOTAL_FEE.
	 * 
	 * @param totalFee
	 *            the new 开单金额//总费用TOTAL_FEE
	 */
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * 获取 代收货款//代收货款COD_AMOUNT.
	 * 
	 * @return the 代收货款//代收货款COD_AMOUNT
	 */
	public String getCodAmount() {
		return codAmount;
	}

	/**
	 * 设置 代收货款//代收货款COD_AMOUNT.
	 * 
	 * @param codAmount
	 *            the new 代收货款//代收货款COD_AMOUNT
	 */
	public void setCodAmount(String codAmount) {
		this.codAmount = codAmount;
	}

	/**
	 * 获取 收货客户//收货客户名称RECEIVE_CUSTOMER_NAME.
	 * 
	 * @return the 收货客户//收货客户名称RECEIVE_CUSTOMER_NAME
	 */
	public String getReceiveCustomerName() {
		return receiveCustomerName;
	}

	/**
	 * 设置 收货客户//收货客户名称RECEIVE_CUSTOMER_NAME.
	 * 
	 * @param receiveCustomerName
	 *            the new 收货客户//收货客户名称RECEIVE_CUSTOMER_NAME
	 */
	public void setReceiveCustomerName(String receiveCustomerName) {
		this.receiveCustomerName = receiveCustomerName;
	}

	/**
	 * 获取 是否自提（提货方式）.
	 * 
	 * @return the 是否自提（提货方式）
	 */
	public String getBeAutoDelivery() {
		return beAutoDelivery;
	}

	/**
	 * 设置 是否自提（提货方式）.
	 * 
	 * @param beAutoDelivery
	 *            the new 是否自提（提货方式）
	 */
	public void setBeAutoDelivery(String beAutoDelivery) {
		this.beAutoDelivery = beAutoDelivery;
	}

	/**
	 * 获取 代理电话//联系电话T_BAS_BUSINESS_PARTNER.
	 * 
	 * @return the 代理电话//联系电话T_BAS_BUSINESS_PARTNER
	 */
	public String getPartnerContactPhone() {
		return partnerContactPhone;
	}

	/**
	 * 设置 代理电话//联系电话T_BAS_BUSINESS_PARTNER.
	 * 
	 * @param partnerContactPhone
	 *            the new 代理电话//联系电话T_BAS_BUSINESS_PARTNER
	 */
	public void setPartnerContactPhone(String partnerContactPhone) {
		this.partnerContactPhone = partnerContactPhone;
	}

	/**
	 * 获取 外发代理//外发代理公司的名称T_BAS_BUSINESS_PARTNER.
	 * 
	 * @return the 外发代理//外发代理公司的名称T_BAS_BUSINESS_PARTNER
	 */
	public String getAgentCompanyName() {
		return agentCompanyName;
	}

	/**
	 * 设置 外发代理//外发代理公司的名称T_BAS_BUSINESS_PARTNER.
	 * 
	 * @param agentCompanyName
	 *            the new 外发代理//外发代理公司的名称T_BAS_BUSINESS_PARTNER
	 */
	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}

	/**
	 * 获取 到达网点//代理网点名称(综合外部网点)T_BAS_OUTER_BRANCH.
	 * 
	 * @return the 到达网点//代理网点名称(综合外部网点)T_BAS_OUTER_BRANCH
	 */
	public String getAgentDeptName() {
		return agentDeptName;
	}

	/**
	 * 设置 到达网点//代理网点名称(综合外部网点)T_BAS_OUTER_BRANCH.
	 * 
	 * @param agentDeptName
	 *            the new 到达网点//代理网点名称(综合外部网点)T_BAS_OUTER_BRANCH
	 */
	public void setAgentDeptName(String agentDeptName) {
		this.agentDeptName = agentDeptName;
	}

	/**
	 * 获取 到达网点电话//联系电话(综合外部网点)T_BAS_OUTER_BRANCH.
	 * 
	 * @return the 到达网点电话//联系电话(综合外部网点)T_BAS_OUTER_BRANCH
	 */
	public String getContactPhone() {
		return contactPhone;
	}

	/**
	 * 设置 到达网点电话//联系电话(综合外部网点)T_BAS_OUTER_BRANCH.
	 * 
	 * @param contactPhone
	 *            the new 到达网点电话//联系电话(综合外部网点)T_BAS_OUTER_BRANCH
	 */
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	/**
	 * 获取 到达网点地址//网点地址(综合外部网点)T_BAS_OUTER_BRANCH.
	 * 
	 * @return the 到达网点地址//网点地址(综合外部网点)T_BAS_OUTER_BRANCH
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置 到达网点地址//网点地址(综合外部网点)T_BAS_OUTER_BRANCH.
	 * 
	 * @param address
	 *            the new 到达网点地址//网点地址(综合外部网点)T_BAS_OUTER_BRANCH
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 获取 付款方式CODE.
	 * 
	 * @return the 付款方式CODE
	 */
	public String getPaidMethodCode() {
		return paidMethodCode;
	}

	/**
	 * 设置 付款方式CODE.
	 * 
	 * @param paidMethodCode
	 *            the new 付款方式CODE
	 */
	public void setPaidMethodCode(String paidMethodCode) {
		this.paidMethodCode = paidMethodCode;
	}

	/**
	 * 获取 提货网点CUSTOMER_PICKUP_ORG_CODE.
	 * 
	 * @return the 提货网点CUSTOMER_PICKUP_ORG_CODE
	 */
	public String getCustomerPickupOrgCode() {
		return customerPickupOrgCode;
	}

	/**
	 * 设置 提货网点CUSTOMER_PICKUP_ORG_CODE.
	 * 
	 * @param customerPickupOrgCode
	 *            the new 提货网点CUSTOMER_PICKUP_ORG_CODE
	 */
	public void setCustomerPickupOrgCode(String customerPickupOrgCode) {
		this.customerPickupOrgCode = customerPickupOrgCode;
	}

	/**
	 * 获取 币种.
	 * 
	 * @return the 币种
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * 设置 币种.
	 * 
	 * @param currencyCode
	 *            the new 币种
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getReceiveCustomerContact() {
		return receiveCustomerContact;
	}

	public void setReceiveCustomerContact(String receiveCustomerContact) {
		this.receiveCustomerContact = receiveCustomerContact;
	}

	public String getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}

	public BigDecimal getExternalAgencyFee() {
		return externalAgencyFee;
	}

	public void setExternalAgencyFee(BigDecimal externalAgencyFee) {
		this.externalAgencyFee = externalAgencyFee;
	}

	public BigDecimal getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(BigDecimal deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	
}
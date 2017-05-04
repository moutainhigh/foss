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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/SmsVoiceParamDto.java
 * 
 * FILE NAME        	: SmsVoiceParamDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.dto
 * FILE    NAME: smsVoiceParamDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;


/**
 * 短信语音参数DTO
 * @author 026123-foss-lifengteng
 * @date 2012-12-26 下午4:44:23
 */
public class SmsVoiceParamDto implements Serializable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = -7615007015990737447L;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 发货客户联系人
	 */
	private String deliveryCustomerContact;
	
	/**
	 * 发货市
	 */
	private String deliveryCustomerCity;
	
	/**
	 * 收货客户地址
	 */
	private String receiveCustomerAddress;
	
	/**
	 * 货物总件数（开单时）
	 */
	private String goodsQtyTotal;
	
	/**
	 * 开单总费用 
	 */
	private String totalFee;
	
	/**
	 * 提货网点名称
	 */
	private String customerPickupOrg;
	
	/**
	 * 广告内容
	 */
	private String adContent;
	
	/**
	 * 原运单号
	 */
	private String originalWaybillNo;
	
	/**	 * @项目：新客户体验项目
	 * @功能：保存费用字段（以下绿色注释都是）
	 * @author:218371-foss-zhaoyanjun
	 * @date:2015-06-23下午16:39
	 */
	/*
     * 运费
     */
    private String transportFee;
    
    /*
     * 保价费
     */
    private String insuranceFee;
    
    /*
     * 代收货款
     */
    private String codAmount;
    
    /*
     * 接货费
     */
    private String pickupFee;
    
    /*
     * 送货费
     */
    private String deliveryGoodsFee;
    
    /*
     * 包装费
     */
    private String packageFee;
    
    /*
     * 装卸费
     */
//    private String serviceFee;
    
    /*
     * 其他费用
     */
    private String otherFee;
    
    /*
     * 是否新客户
     */
    private String newOrOld;
    
    /*
     * 部门电话
     */
    private String deptTelephone;
    
    /*
	 * 代收货款手续费
	 */
	private String codFee;
	
	/*
	 * 发货营业部经理手机
	 */
	private String deliverOrgManagerMP;
	
	public String getCodFee() {
		return codFee;
	}

	public void setCodFee(String codFee) {
		this.codFee = codFee;
	}

	public String getDeptTelephone() {
		return deptTelephone;
	}


	public void setDeptTelephone(String deptTelephone) {
		this.deptTelephone = deptTelephone;
	}


	public String getTransportFee() {
		return transportFee;
	}


	public void setTransportFee(String transportFee) {
		this.transportFee = transportFee;
	}


	public String getInsuranceFee() {
		return insuranceFee;
	}


	public void setInsuranceFee(String insuranceFee) {
		this.insuranceFee = insuranceFee;
	}


	public String getCodAmount() {
		return codAmount;
	}


	public void setCodAmount(String codAmount) {
		this.codAmount = codAmount;
	}


	public String getPickupFee() {
		return pickupFee;
	}


	public void setPickupFee(String pickupFee) {
		this.pickupFee = pickupFee;
	}


	public String getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}


	public void setDeliveryGoodsFee(String deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
	}


	public String getPackageFee() {
		return packageFee;
	}


	public void setPackageFee(String packageFee) {
		this.packageFee = packageFee;
	}


//	public String getServiceFee() {
//		return serviceFee;
//	}
//
//
//	public void setServiceFee(String serviceFee) {
//		this.serviceFee = serviceFee;
//	}


	public String getOtherFee() {
		return otherFee;
	}


	public void setOtherFee(String otherFee) {
		this.otherFee = otherFee;
	}


	public String getNewOrOld() {
		return newOrOld;
	}


	public void setNewOrOld(String newOrOld) {
		this.newOrOld = newOrOld;
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
	 * 获取 发货市.
	 *
	 * @return the 发货市
	 */
	public String getDeliveryCustomerCity() {
		return deliveryCustomerCity;
	}

	
	/**
	 * 设置 发货市.
	 *
	 * @param deliveryCustomerCity the new 发货市
	 */
	public void setDeliveryCustomerCity(String deliveryCustomerCity) {
		this.deliveryCustomerCity = deliveryCustomerCity;
	}

	
	/**
	 * 获取 收货客户地址.
	 *
	 * @return the 收货客户地址
	 */
	public String getReceiveCustomerAddress() {
		return receiveCustomerAddress;
	}

	
	/**
	 * 设置 收货客户地址.
	 *
	 * @param receiveCustomerAddress the new 收货客户地址
	 */
	public void setReceiveCustomerAddress(String receiveCustomerAddress) {
		this.receiveCustomerAddress = receiveCustomerAddress;
	}

	
	/**
	 * 获取 货物总件数（开单时）.
	 *
	 * @return the 货物总件数（开单时）
	 */
	public String getGoodsQtyTotal() {
		return goodsQtyTotal;
	}

	
	/**
	 * 设置 货物总件数（开单时）.
	 *
	 * @param goodsQtyTotal the new 货物总件数（开单时）
	 */
	public void setGoodsQtyTotal(String goodsQtyTotal) {
		this.goodsQtyTotal = goodsQtyTotal;
	}

	
	/**
	 * 获取 开单总费用.
	 *
	 * @return the 开单总费用
	 */
	public String getTotalFee() {
		return totalFee;
	}

	
	/**
	 * 设置 开单总费用.
	 *
	 * @param totalFee the new 开单总费用
	 */
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	
	/**
	 * 获取 提货网点名称.
	 *
	 * @return the 提货网点名称
	 */
	public String getCustomerPickupOrg() {
		return customerPickupOrg;
	}

	
	/**
	 * 设置 提货网点名称.
	 *
	 * @param customerPickupOrg the new 提货网点名称
	 */
	public void setCustomerPickupOrg(String customerPickupOrg) {
		this.customerPickupOrg = customerPickupOrg;
	}

	
	/**
	 * 获取 广告内容.
	 *
	 * @return the 广告内容
	 */
	public String getAdContent() {
		return adContent;
	}

	
	/**
	 * 设置 广告内容.
	 *
	 * @param adContent the new 广告内容
	 */
	public void setAdContent(String adContent) {
		this.adContent = adContent;
	}


	public String getOriginalWaybillNo() {
		return originalWaybillNo;
	}


	public void setOriginalWaybillNo(String originalWaybillNo) {
		this.originalWaybillNo = originalWaybillNo;
	}

	public String getDeliverOrgManagerMP() {
		return deliverOrgManagerMP;
	}

	public void setDeliverOrgManagerMP(String deliverOrgManagerMP) {
		this.deliverOrgManagerMP = deliverOrgManagerMP;
	}

	
}
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillRfcEntity.java
 * 
 * FILE NAME        	: WaybillRfcEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 运单更改中转信息
 * 
 * @author foss-206860
 */
public class WaybillRfcTranferEntity extends BaseEntity {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 8554181972428281435L;
	
	/**
	 * id号
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	private String waybillRfcId;

	public String getWaybillRfcId() {
		return waybillRfcId;
	}

	public void setWaybillRfcId(String waybillRfcId) {
		this.waybillRfcId = waybillRfcId;
	}

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 转运或返货出发部门
	 */
	private String sourceTargerOrgCode;

	/**
	 * 转运或返货到达部门
	 */
	private String changeTargerOrgCode;
	
	/**
	 * 转运或返货前目的站
	 */
	private String sourceCustomerOrgCode;

	/**
	 * 转运或返货后目的站
	 */
	private String finalCustomerOrgCode;

	/**
	 * 货物状态
	 */
	private String goodsStatus;


	/**
	 * 操作时间
	 */
	private Date operateTime;
	
	
	/**
	 * 中转费
	 */
	private BigDecimal transportFee;
	
	/**
	 * 中转费率 
	 */
	private BigDecimal transportFeeRate;
	
	/**
	 * 运费计费类型
	 */
	private String billingType;
	
	/**
	 * 运单状态
	 */
	private String active;
	
	/**
	 * 货物范围
	 */
	private String goodsRange;
	
	/**
	 * 是否累加
	 */
	private String isSum;
	
	/**
	 * 变更类型
	 *  **/
	private String rfcType;
	
	/**
	 * 是否手写费率
	 * **/
	private String isTfrHandWrite;
	
	
	
	public String getIsTfrHandWrite() {
		return isTfrHandWrite;
	}

	public void setIsTfrHandWrite(String isTfrHandWrite) {
		this.isTfrHandWrite = isTfrHandWrite;
	}

	public String getRfcType() {
		return rfcType;
	}

	public void setRfcType(String rfcType) {
		this.rfcType = rfcType;
	}

	public String getIsSum() {
		return isSum;
	}

	public void setIsSum(String isSum) {
		this.isSum = isSum;
	}

	public String getGoodsRange() {
		return goodsRange;
	}

	public void setGoodsRange(String goodsRange) {
		this.goodsRange = goodsRange;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getSourceTargerOrgCode() {
		return sourceTargerOrgCode;
	}

	public void setSourceTargerOrgCode(String sourceTargerOrgCode) {
		this.sourceTargerOrgCode = sourceTargerOrgCode;
	}

	public String getChangeTargerOrgCode() {
		return changeTargerOrgCode;
	}

	public void setChangeTargerOrgCode(String changeTargerOrgCode) {
		this.changeTargerOrgCode = changeTargerOrgCode;
	}

	public String getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(String goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public BigDecimal getTransportFee() {
		return transportFee;
	}

	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = transportFee;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public BigDecimal getTransportFeeRate() {
		return transportFeeRate;
	}

	public void setTransportFeeRate(BigDecimal transportFeeRate) {
		this.transportFeeRate = transportFeeRate;
	}

	public String getBillingType() {
		return billingType;
	}

	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}

	public String getSourceCustomerOrgCode() {
		return sourceCustomerOrgCode;
	}

	public void setSourceCustomerOrgCode(String sourceCustomerOrgCode) {
		this.sourceCustomerOrgCode = sourceCustomerOrgCode;
	}

	public String getFinalCustomerOrgCode() {
		return finalCustomerOrgCode;
	}

	public void setFinalCustomerOrgCode(String finalCustomerOrgCode) {
		this.finalCustomerOrgCode = finalCustomerOrgCode;
	}
	
	

}
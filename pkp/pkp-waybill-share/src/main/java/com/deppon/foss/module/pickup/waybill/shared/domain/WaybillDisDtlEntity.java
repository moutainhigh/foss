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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillDisDtlEntity.java
 * 
 * FILE NAME        	: WaybillDisDtlEntity.java
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
 * 
 * 运单折扣明细
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:Bobby,date:2012-10-17 下午5:57:03,
 * </p>
 * 
 * @author Bobby
 * @date 2012-10-17 下午5:57:03
 * @since
 * @version
 */
public class WaybillDisDtlEntity extends BaseEntity {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 4421371228776379117L;

	/**
	 * 折扣方案CODE
	 */
	private String pricingEntryCode;
	
	/**
	 * 折扣方案Name
	 */
	private String pricingEntryName;

	/**
	 * 折扣类型
	 */
	private String type;
	
	/**
	 * 折扣类型名称
	 */
	private String typeName;
	

	/**
	 * 折扣类型
	 */
	private String subType;
	
	/**
	 * 折扣类型名称
	 */
	private String subTypeName;

	/**
	 * 折扣费率
	 */
	private BigDecimal rate;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 折扣ID
	 */
	private String dicountId;

	/**
	 * 运单费用明细ID
	 */
	private String waybillChargeDetailId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 修改时间
	 */
	private Date modifyTime;

	/**
	 * 状态
	 */
	private String active;

	/**
	 * 收货时间
	 */
	private Date billTime;

	/**
	 * 币种
	 */
	private String currencyCode;
	
	/**
	 * 营销活动CODE
	 */
	private String activeCode;
	/**
	 * 营销活动名称
	 */
	private String activeName;
	/**
	 * 营销活动开始时间
	 */
	private Date activeStartTime;
	/**
	 * 营销活动结束时间
	 */
	private Date activeEndTime;
	/**
	 * 营销活动折扣关联的CRM_ID
	 */
	private BigDecimal optionsCrmId;
	
	/**
	 * 折扣费率
	 */
	private BigDecimal expressContinueRate;
	

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public Date getActiveStartTime() {
		return activeStartTime;
	}

	public void setActiveStartTime(Date activeStartTime) {
		this.activeStartTime = activeStartTime;
	}

	public Date getActiveEndTime() {
		return activeEndTime;
	}

	public void setActiveEndTime(Date activeEndTime) {
		this.activeEndTime = activeEndTime;
	}

	public BigDecimal getOptionsCrmId() {
		return optionsCrmId;
	}

	public void setOptionsCrmId(BigDecimal optionsCrmId) {
		this.optionsCrmId = optionsCrmId;
	}

	/**
	 * @return the pricingEntryCode .
	 */
	public String getPricingEntryCode() {
		return pricingEntryCode;
	}

	/**
	 * @param pricingEntryCode
	 *            the pricingEntryCode to set.
	 */
	public void setPricingEntryCode(String pricingEntryCode) {
		this.pricingEntryCode = pricingEntryCode;
	}

	/**
	 * @return the type .
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the rate .
	 */
	public BigDecimal getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            the rate to set.
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	/**
	 * @return the amount .
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set.
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the waybillNo .
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 *            the waybillNo to set.
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return the dicountId .
	 */
	public String getDicountId() {
		return dicountId;
	}

	/**
	 * @param dicountId
	 *            the dicountId to set.
	 */
	public void setDicountId(String dicountId) {
		this.dicountId = dicountId;
	}

	/**
	 * @return the waybillChargeDetailId .
	 */
	public String getWaybillChargeDetailId() {
		return waybillChargeDetailId;
	}

	/**
	 * @param waybillChargeDetailId
	 *            the waybillChargeDetailId to set.
	 */
	public void setWaybillChargeDetailId(String waybillChargeDetailId) {
		this.waybillChargeDetailId = waybillChargeDetailId;
	}

	/**
	 * @return the createTime .
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set.
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the modifyTime .
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            the modifyTime to set.
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the active .
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set.
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the billTime .
	 */
	public Date getBillTime() {
		return billTime;
	}

	/**
	 * @param billTime
	 *            the billTime to set.
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	/**
	 * @return the currencyCode .
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode
	 *            the currencyCode to set.
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPricingEntryName() {
		return pricingEntryName;
	}

	public void setPricingEntryName(String pricingEntryName) {
		this.pricingEntryName = pricingEntryName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	public BigDecimal getExpressContinueRate() {
		return expressContinueRate;
	}

	public void setExpressContinueRate(BigDecimal expressContinueRate) {
		this.expressContinueRate = expressContinueRate;
	}

	

}
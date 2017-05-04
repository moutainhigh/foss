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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WaybillCHDtlPendingEntity.java
 * 
 * FILE NAME        	: WaybillCHDtlPendingEntity.java
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
 * 运单费用明细(PDA、暂存)
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-10-29 上午11:07:44
 */
/**
 *  运单费用明细(PDA、暂存)
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-12-24 下午9:12:54
 */
public class WaybillCHDtlPendingEntity extends BaseEntity {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 424078506737745210L;


	/**
	 * 费用CODE
	 */
	private String pricingEntryCode;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 明细价格ID
	 */
	private String pricingCriDetailId;

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
	 * 开单时间
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
	 * @return the pricingCriDetailId .
	 */
	public String getPricingCriDetailId() {
		return pricingCriDetailId;
	}

	/**
	 * @param pricingCriDetailId
	 *            the pricingCriDetailId to set.
	 */
	public void setPricingCriDetailId(String pricingCriDetailId) {
		this.pricingCriDetailId = pricingCriDetailId;
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

}
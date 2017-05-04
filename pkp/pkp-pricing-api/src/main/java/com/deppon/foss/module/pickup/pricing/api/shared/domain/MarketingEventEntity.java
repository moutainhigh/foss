/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/MarketingEventEntity.java
 * 
 * FILE NAME        	: MarketingEventEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
@SuppressWarnings("serial")
public class MarketingEventEntity extends BaseEntity{

    /**
     * 市场活动CODe
     */
    private String code;

    /**
     * 市场活动名称 
     */
    private String name;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;
    
    /**
     * 开单时间
     */
    private Date businessDate;

    /**
     * 方案描述
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建部门
     */
    private String createOrgCode;

    /**
     * 修改部门
     */
    private String modifyOrgCode;

    /**
     * 版本号
     */
    private Long versionNo;

    /**
     * 价格区域CODE
     */
    private String priceRegionCode;

    /**
     * 价格区域ID
     */
    private String priceRegionId;

    /**
     * 激活状态
     */
    private String active;

    /**
     * 活动类型
     */
    private String type;
    
    /**
     * 计价条目ID 
     */
    private String pricingEntryId;
    
    /**
     * 计价条目CODE
     */
    private String pricingEntryCode;
    
    /**
     * 计价条目NAME
     */
    private String pricingEntryName;
    
    /**
     * 创建人姓名
     */
    private String createUserName;

	/**
	 * 获取 市场活动CODe.
	 *
	 * @return the 市场活动CODe
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置 市场活动CODe.
	 *
	 * @param code the new 市场活动CODe
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取 市场活动名称.
	 *
	 * @return the 市场活动名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 市场活动名称.
	 *
	 * @param name the new 市场活动名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取 开始时间.
	 *
	 * @return the 开始时间
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * 设置 开始时间.
	 *
	 * @param beginTime the new 开始时间
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * 获取 结束时间.
	 *
	 * @return the 结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 设置 结束时间.
	 *
	 * @param endTime the new 结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 获取 开单时间.
	 *
	 * @return the 开单时间
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * 设置 开单时间.
	 *
	 * @param businessDate the new 开单时间
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * 获取 方案描述.
	 *
	 * @return the 方案描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置 方案描述.
	 *
	 * @param description the new 方案描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取 创建部门.
	 *
	 * @return the 创建部门
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 设置 创建部门.
	 *
	 * @param createOrgCode the new 创建部门
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取 修改部门.
	 *
	 * @return the 修改部门
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	/**
	 * 设置 修改部门.
	 *
	 * @param modifyOrgCode the new 修改部门
	 */
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}

	/**
	 * 获取 版本号.
	 *
	 * @return the 版本号
	 */
	public Long getVersionNo() {
		return versionNo;
	}

	/**
	 * 设置 版本号.
	 *
	 * @param versionNo the new 版本号
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	/**
	 * 获取 价格区域CODE.
	 *
	 * @return the 价格区域CODE
	 */
	public String getPriceRegionCode() {
		return priceRegionCode;
	}

	/**
	 * 设置 价格区域CODE.
	 *
	 * @param priceRegionCode the new 价格区域CODE
	 */
	public void setPriceRegionCode(String priceRegionCode) {
		this.priceRegionCode = priceRegionCode;
	}

	/**
	 * 获取 价格区域ID.
	 *
	 * @return the 价格区域ID
	 */
	public String getPriceRegionId() {
		return priceRegionId;
	}

	/**
	 * 设置 价格区域ID.
	 *
	 * @param priceRegionId the new 价格区域ID
	 */
	public void setPriceRegionId(String priceRegionId) {
		this.priceRegionId = priceRegionId;
	}

	/**
	 * 获取 激活状态.
	 *
	 * @return the 激活状态
	 */
	public String getActive() {
		return active;
	}

	/**
	 * 设置 激活状态.
	 *
	 * @param active the new 激活状态
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 获取 活动类型.
	 *
	 * @return the 活动类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置 活动类型.
	 *
	 * @param type the new 活动类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取 计价条目ID.
	 *
	 * @return the 计价条目ID
	 */
	public String getPricingEntryId() {
		return pricingEntryId;
	}

	/**
	 * 设置 计价条目ID.
	 *
	 * @param pricingEntryId the new 计价条目ID
	 */
	public void setPricingEntryId(String pricingEntryId) {
		this.pricingEntryId = pricingEntryId;
	}

	/**
	 * 获取 计价条目CODE.
	 *
	 * @return the 计价条目CODE
	 */
	public String getPricingEntryCode() {
		return pricingEntryCode;
	}

	/**
	 * 设置 计价条目CODE.
	 *
	 * @param pricingEntryCode the new 计价条目CODE
	 */
	public void setPricingEntryCode(String pricingEntryCode) {
		this.pricingEntryCode = pricingEntryCode;
	}

	/**
	 * 获取 创建人姓名.
	 *
	 * @return the 创建人姓名
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * 设置 创建人姓名.
	 *
	 * @param createUserName the new 创建人姓名
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	/**
	 * 获取 价格条目NAME.
	 *
	 * @return the 价格条目NAME
	 */
	public String getPricingEntryName() {
		return pricingEntryName;
	}

	/**
	 * 设置  价格条目NAME.
	 *
	 * @param createUserName the new 价格条目NAME
	 */
	public void setPricingEntryName(String pricingEntryName) {
		this.pricingEntryName = pricingEntryName;
	}

    
}
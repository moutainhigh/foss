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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/MarketingEventChannelEntity.java
 * 
 * FILE NAME        	: MarketingEventChannelEntity.java
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
public class MarketingEventChannelEntity extends BaseEntity{
    
    /**
     * 市场渠道CODE
     */
    private String salesChannelCode;

    /**
     * 市场渠道ID
     */
    private String salesChannelId;

    /**
     * 市场活动ID
     */
    private String marketingEventId;

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 创建部门CODE
     */
    private String createOrgCode;

    /**
     * 修改部门CODE
     */
    private String modifyOrgCode;

    /**
     * 版本号
     */
    private Long versionNo;

    /**
     * 激活状态
     */
    private String active;

	/**
	 * 获取 市场渠道CODE.
	 *
	 * @return the 市场渠道CODE
	 */
	public String getSalesChannelCode() {
		return salesChannelCode;
	}

	/**
	 * 设置 市场渠道CODE.
	 *
	 * @param salesChannelCode the new 市场渠道CODE
	 */
	public void setSalesChannelCode(String salesChannelCode) {
		this.salesChannelCode = salesChannelCode;
	}

	/**
	 * 获取 市场渠道ID.
	 *
	 * @return the 市场渠道ID
	 */
	public String getSalesChannelId() {
		return salesChannelId;
	}

	/**
	 * 设置 市场渠道ID.
	 *
	 * @param salesChannelId the new 市场渠道ID
	 */
	public void setSalesChannelId(String salesChannelId) {
		this.salesChannelId = salesChannelId;
	}

	/**
	 * 获取 市场活动ID.
	 *
	 * @return the 市场活动ID
	 */
	public String getMarketingEventId() {
		return marketingEventId;
	}

	/**
	 * 设置 市场活动ID.
	 *
	 * @param marketingEventId the new 市场活动ID
	 */
	public void setMarketingEventId(String marketingEventId) {
		this.marketingEventId = marketingEventId;
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
	 * 获取 创建部门CODE.
	 *
	 * @return the 创建部门CODE
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * 设置 创建部门CODE.
	 *
	 * @param createOrgCode the new 创建部门CODE
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * 获取 修改部门CODE.
	 *
	 * @return the 修改部门CODE
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}

	/**
	 * 设置 修改部门CODE.
	 *
	 * @param modifyOrgCode the new 修改部门CODE
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


}
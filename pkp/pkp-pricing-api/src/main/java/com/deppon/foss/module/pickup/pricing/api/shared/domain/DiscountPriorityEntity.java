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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/DiscountPriorityEntity.java
 * 
 * FILE NAME        	: DiscountPriorityEntity.java
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
public class DiscountPriorityEntity extends BaseEntity {
	
    /**
     * 折扣优先级CODE
     */
    private String code;
    
    /**
     * 折扣优先级名称
     */
    private String name;
    
    /**
     * 折扣优先级别
     */
    private int level;
    
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
	 * 获取 折扣优先级CODE.
	 *
	 * @return the 折扣优先级CODE
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置 折扣优先级CODE.
	 *
	 * @param code the new 折扣优先级CODE
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取 折扣优先级名称.
	 *
	 * @return the 折扣优先级名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 折扣优先级名称.
	 *
	 * @param name the new 折扣优先级名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取 折扣优先级别.
	 *
	 * @return the 折扣优先级别
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * 设置 折扣优先级别.
	 *
	 * @param level the new 折扣优先级别
	 */
	public void setLevel(int level) {
		this.level = level;
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
    
}
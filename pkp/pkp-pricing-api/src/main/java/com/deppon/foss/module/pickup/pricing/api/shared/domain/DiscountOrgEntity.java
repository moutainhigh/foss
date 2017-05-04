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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/DiscountOrgEntity.java
 * 
 * FILE NAME        	: DiscountOrgEntity.java
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
public class DiscountOrgEntity extends BaseEntity {
	
    /**
     * 起始部门ID
     */
    private String deptOrgId;
    
    /**
     * 起始部门CODE
     */
    private String deptOrgCode;

    /**
     * 到达部门ID
     */
    private String arrvOrgId;
    
    /**
     * 到达部门CODE
     */
    private String arrvOrgCode;
    
    /**
     * 起始部门类型CODE 
     */
    private String deptOrgTypeCode;
    /**
     * 起始部门类型名称 
     */
    private String deptOrgTypeName;
    
    /**
     * 到达部门类型CODE 
     */
    private String arrvOrgTypeCode;
    /**
   	 * 到达部门类型名称 
   	 */
    private String arrvOrgTypeName;

    /**
	 * 起始时间
	 */
	private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 版本号
     */
    private Long versionNo;

    /**
     * 激活状态
     */
    private String active;

    /**
     * 创健人CODE
     */
    private String createUserCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建部门
     */
    private String createOrgCode;

    /**
     * 修改人CODE
     */
    private String modifyUserCode;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 修改部门
     */
    private String modifyOrgCode;

    /**
     * 计费规则ID
     */
    private String tSrvPricingValuationId;
    
    /**
     * 计费规则
     */
    private PriceValuationEntity priceValuationEntity;

	/**
	 * 获取 起始部门ID.
	 *
	 * @return the 起始部门ID
	 */
	public String getDeptOrgId() {
		return deptOrgId;
	}

	/**
	 * 设置 起始部门ID.
	 *
	 * @param deptOrgId the new 起始部门ID
	 */
	public void setDeptOrgId(String deptOrgId) {
		this.deptOrgId = deptOrgId;
	}

	/**
	 * 获取 起始部门CODE.
	 *
	 * @return the 起始部门CODE
	 */
	public String getDeptOrgCode() {
		return deptOrgCode;
	}

	/**
	 * 设置 起始部门CODE.
	 *
	 * @param deptOrgCode the new 起始部门CODE
	 */
	public void setDeptOrgCode(String deptOrgCode) {
		this.deptOrgCode = deptOrgCode;
	}

	/**
	 * 获取 到达部门ID.
	 *
	 * @return the 到达部门ID
	 */
	public String getArrvOrgId() {
		return arrvOrgId;
	}

	/**
	 * 设置 到达部门ID.
	 *
	 * @param arrvOrgId the new 到达部门ID
	 */
	public void setArrvOrgId(String arrvOrgId) {
		this.arrvOrgId = arrvOrgId;
	}

	/**
	 * 获取 到达部门CODE.
	 *
	 * @return the 到达部门CODE
	 */
	public String getArrvOrgCode() {
		return arrvOrgCode;
	}

	/**
	 * 设置 到达部门CODE.
	 *
	 * @param arrvOrgCode the new 到达部门CODE
	 */
	public void setArrvOrgCode(String arrvOrgCode) {
		this.arrvOrgCode = arrvOrgCode;
	}

	/**
	 * 获取 起始部门类型CODE.
	 *
	 * @return the 起始部门类型CODE
	 */
	public String getDeptOrgTypeCode() {
		return deptOrgTypeCode;
	}

	/**
	 * 设置 起始部门类型CODE.
	 *
	 * @param deptOrgTypeCode the new 起始部门类型CODE
	 */
	public void setDeptOrgTypeCode(String deptOrgTypeCode) {
		this.deptOrgTypeCode = deptOrgTypeCode;
	}

	/**
	 * 获取 起始部门类型名称.
	 *
	 * @return the 起始部门类型名称
	 */
	public String getDeptOrgTypeName() {
		return deptOrgTypeName;
	}

	/**
	 * 设置 起始部门类型名称.
	 *
	 * @param deptOrgTypeName the new 起始部门类型名称
	 */
	public void setDeptOrgTypeName(String deptOrgTypeName) {
		this.deptOrgTypeName = deptOrgTypeName;
	}

	/**
	 * 获取 到达部门类型CODE.
	 *
	 * @return the 到达部门类型CODE
	 */
	public String getArrvOrgTypeCode() {
		return arrvOrgTypeCode;
	}

	/**
	 * 设置 到达部门类型CODE.
	 *
	 * @param arrvOrgTypeCode the new 到达部门类型CODE
	 */
	public void setArrvOrgTypeCode(String arrvOrgTypeCode) {
		this.arrvOrgTypeCode = arrvOrgTypeCode;
	}

	/**
	 * 获取 到达部门类型名称.
	 *
	 * @return the 到达部门类型名称
	 */
	public String getArrvOrgTypeName() {
		return arrvOrgTypeName;
	}

	/**
	 * 设置 到达部门类型名称.
	 *
	 * @param arrvOrgTypeName the new 到达部门类型名称
	 */
	public void setArrvOrgTypeName(String arrvOrgTypeName) {
		this.arrvOrgTypeName = arrvOrgTypeName;
	}

	/**
	 * 获取 起始时间.
	 *
	 * @return the 起始时间
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * 设置 起始时间.
	 *
	 * @param beginTime the new 起始时间
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

	/**
	 * 获取 创健人CODE.
	 *
	 * @return the 创健人CODE
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}

	/**
	 * 设置 创健人CODE.
	 *
	 * @param createUserCode the new 创健人CODE
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	/**
	 * 获取 创建时间.
	 *
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置 创建时间.
	 *
	 * @param createTime the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * 获取 修改人CODE.
	 *
	 * @return the 修改人CODE
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * 设置 修改人CODE.
	 *
	 * @param modifyUserCode the new 修改人CODE
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * 获取 修改时间.
	 *
	 * @return the 修改时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * 设置 修改时间.
	 *
	 * @param modifyTime the new 修改时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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
	 * 
	 *
	 * @return 
	 */
	public String gettSrvPricingValuationId() {
		return tSrvPricingValuationId;
	}

	/**
	 * 
	 *
	 * @param tSrvPricingValuationId 
	 */
	public void settSrvPricingValuationId(String tSrvPricingValuationId) {
		this.tSrvPricingValuationId = tSrvPricingValuationId;
	}

	/**
	 * 获取 计费规则.
	 *
	 * @return the 计费规则
	 */
	public PriceValuationEntity getPriceValuationEntity() {
		return priceValuationEntity;
	}

	/**
	 * 设置 计费规则.
	 *
	 * @param priceValuationEntity the new 计费规则
	 */
	public void setPriceValuationEntity(PriceValuationEntity priceValuationEntity) {
		this.priceValuationEntity = priceValuationEntity;
	}
    

}
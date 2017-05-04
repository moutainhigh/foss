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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/PricePlanEntity.java
 * 
 * FILE NAME        	: PricePlanEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * InempDiscountPlanEntity
 * 内部员工折扣方案
 * DP-Foss-dongjialing
 * 2015-04-08 上午8:32:08.
 *
 * @version 1.0.0
 */
public class InempDiscountPlanEntity extends BaseEntity {

    /**
     * 序列化号.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 方案编码
     */
    private String code;
    
    /**
     * 运费折扣
     */
    private BigDecimal chargeRebate;
    
    /**
     * 最高优惠限额
     */
    private BigDecimal highstPreferentialLimit;
    
    /**
     * 优惠生效日期.
     */
    private Date beginTime;
    
    /**
     * 优惠结束日期.
     */
    private Date endTime;
    
    /**
     * 数据状态.
     */
    private String active;
    
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 创建部门
     */
    private String createOrgCode;
    
    /**
     * 更新部门
     */
    private String modifyOrgCode;
    /**
     * 创建人编码
     */
    private String createUserCode;
    /**
     * 创建人姓名
     */
    private String createUserName;
    /**
     * 修改人编码
     */
    private String modifyUserCode;
    /**
     * 修改人姓名.
     */
    private String modifyUserName; 
    
    /**
     * 是否当前版本.
     */
    private String currentUsedVersion;
    /**
     * 业务时间
     */
    private Date billTime;
    
    /**
     * 获取创建时间
     * @return
     */
    public Date getCreateTime() {
		return createTime;
	}
    /**
     * 设置创建时间
     * @param createTime
     */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取修改时间
	 * @return
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * 设置修改时间
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取创建人编码
	 * @return
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	/**
	 * 设置创建人编码
	 * @param createUserCode
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	/**
	 * 获取修改人编码
	 * @return
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	/**
	 * 设置修改人编码
	 * @param modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	/**
     * 获取编码
     * @return
     */
	public String getCode() {
		return code;
	}
    /**
     * 设置编码
     * @param code
     */
	public void setCode(String code) {
		this.code = code;
	}
    /**
     * 获取运费折扣
     * @return
     */
	public BigDecimal getChargeRebate() {
		return chargeRebate;
	}
     /**
      * 设置运费折扣
      * @param chargeRebate
      */
	public void setChargeRebate(BigDecimal chargeRebate) {
		this.chargeRebate = chargeRebate;
	}
    /**
     * 获取最高优惠限额
     * @return
     */
	public BigDecimal getHighstPreferentialLimit() {
		return highstPreferentialLimit;
	}
    /**
     * 设置最高优惠限额
     * @param highstPreferentialLimit
     */
	public void setHighstPreferentialLimit(BigDecimal highstPreferentialLimit) {
		this.highstPreferentialLimit = highstPreferentialLimit;
	}
    /**
     * 获取优惠开始时间
     * @return
     */
	public Date getBeginTime() {
		return beginTime;
	}
    /**
     * 设置优惠开始时间
     * @param beginTime
     */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
    /**
     * 获取优惠结束时间
     * @return
     */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置优惠结束时间
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取数据状态
	 * @return
	 */
	public String getActive() {
		return active;
	}
	/**
	 * 设置数据状态
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * 获取创建人部门
	 * @return
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	/**
	 * 设置创建人部门
	 * @param createOrgCode
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	/**
	 * 获取更新人部门
	 * @return
	 */
	public String getModifyOrgCode() {
		return modifyOrgCode;
	}
	/**
	 * 设置更新人部门
	 * @param modifyOrgCode
	 */
	public void setModifyOrgCode(String modifyOrgCode) {
		this.modifyOrgCode = modifyOrgCode;
	}
	/**
	 * 获取创建人姓名
	 * @return
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 * 设置创建人姓名
	 * @param createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	/**
	 * 获取更新人姓名
	 * @return
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}
	/**
	 * 设置更新人姓名
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	/**
	 * 获取是否当前版本
	 * @return
	 */
	public String getCurrentUsedVersion() {
		return currentUsedVersion;
	}
	/**
	 * 设置是否当前版本
	 * @param currentUsedVersion
	 */
	public void setCurrentUsedVersion(String currentUsedVersion) {
		this.currentUsedVersion = currentUsedVersion;
	}
	/**
	 * 获取备注
	 * @return
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取业务时间
	 * @return
	 */
	public Date getBillTime() {
		return billTime;
	}
	/**
	 * 设置业务时间
	 * @param billTime
	 */
	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
    
	
    
}
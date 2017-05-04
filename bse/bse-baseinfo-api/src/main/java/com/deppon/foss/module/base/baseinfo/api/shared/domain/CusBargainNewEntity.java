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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/CusBargainEntity.java
 * 
 * FILE NAME        	: CusBargainEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * 合同实体（为接送货准备）
 * @author 308861 
 * @date 2017-1-10 下午5:17:19
 * @since
 * @version
 */
public class CusBargainNewEntity implements Serializable{
    /**
	 *序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 客户编码
	 */
	private String cusCode;
	/**
     * 月结方式
     */
    private String chargeType;
    /**
     * 合同适用部门标杆编码.
     */
    private String unifiedCode;
    /**
     * 合同适用部门编码
     */
    private String applicateOrgCode;
    /**
     * 合同适用部门名称
     */
    private String applicateOrgName;
    /**
     * 合同适用部门id
     */
    private String applicateOrgId;
    /**
     * 合同编号.
     */
    private String bargainCode;
    /**
     * 优惠类型.
     */
    private String preferentialType;
    /**
     * 已用额度.
     * 待定
     */
    private Long usedAmount;
    /**
     * 所属客户ID.
     */
    private BigDecimal customerCode;
    /**
     * 异地调货标志.（是否统一结算）
     */
    private String asyntakegoodsCode;
    /**
     * 催款部门标杆编码.
     */
    private String hastenfunddeptCode;
    /**
     * 是否精准包裹
     */
	private String isAccuratePackage;
	 /**
     * 合同起始日期.
     */
    private Date beginTime;
    /**
     * 合同到期日期.
     */
    private Date endTime;
    /**
     * crmId
     */
    private String crmId;
    
	public String getCusCode() {
		return cusCode;
	}
	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getUnifiedCode() {
		return unifiedCode;
	}
	public void setUnifiedCode(String unifiedCode) {
		this.unifiedCode = unifiedCode;
	}
	public String getApplicateOrgCode() {
		return applicateOrgCode;
	}
	public void setApplicateOrgCode(String applicateOrgCode) {
		this.applicateOrgCode = applicateOrgCode;
	}
	public String getApplicateOrgName() {
		return applicateOrgName;
	}
	public void setApplicateOrgName(String applicateOrgName) {
		this.applicateOrgName = applicateOrgName;
	}
	public String getApplicateOrgId() {
		return applicateOrgId;
	}
	public void setApplicateOrgId(String applicateOrgId) {
		this.applicateOrgId = applicateOrgId;
	}
	public String getBargainCode() {
		return bargainCode;
	}
	public void setBargainCode(String bargainCode) {
		this.bargainCode = bargainCode;
	}
	public String getPreferentialType() {
		return preferentialType;
	}
	public void setPreferentialType(String preferentialType) {
		this.preferentialType = preferentialType;
	}
	public Long getUsedAmount() {
		return usedAmount;
	}
	public void setUsedAmount(Long usedAmount) {
		this.usedAmount = usedAmount;
	}
	public BigDecimal getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(BigDecimal customerCode) {
		this.customerCode = customerCode;
	}
	public String getAsyntakegoodsCode() {
		return asyntakegoodsCode;
	}
	public void setAsyntakegoodsCode(String asyntakegoodsCode) {
		this.asyntakegoodsCode = asyntakegoodsCode;
	}
	public String getHastenfunddeptCode() {
		return hastenfunddeptCode;
	}
	public void setHastenfunddeptCode(String hastenfunddeptCode) {
		this.hastenfunddeptCode = hastenfunddeptCode;
	}
	public String getIsAccuratePackage() {
		return isAccuratePackage;
	}
	public void setIsAccuratePackage(String isAccuratePackage) {
		this.isAccuratePackage = isAccuratePackage;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getCrmId() {
		return crmId;
	}
	public void setCrmId(String crmId) {
		this.crmId = crmId;
	}
}
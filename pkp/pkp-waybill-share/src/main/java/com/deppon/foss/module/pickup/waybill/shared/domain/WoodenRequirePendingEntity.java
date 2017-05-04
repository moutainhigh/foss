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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/WoodenRequirePendingEntity.java
 * 
 * FILE NAME        	: WoodenRequirePendingEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.domain
 * FILE    NAME: WoodenRequirementsEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 待处理运单打木架信息
 * 
 * @author 026123-foss-lifengteng
 * @date 2012-11-5 上午8:51:00
 */
public class WoodenRequirePendingEntity extends BaseEntity {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 7176653321845673812L;

	// 运单编号
	private String waybillNo;

	// 代打木架部门
	private String packageOrgCode;

	// 打木架货物件数
	private Integer standGoodsNum;

	// 代打木架要求
	private String standRequirement;

	// 打木架货物尺寸
	private String standGoodsSize;

	// 打木架货物体积
	private BigDecimal standGoodsVolume;

	// 打木箱货物件数
	private Integer boxGoodsNum;

	// 代打木箱要求
	private String boxRequirement;

	// 打木箱货物尺寸
	private String boxGoodsSize;

	// 打木箱货物体积
	private BigDecimal boxGoodsVolume;
	
	// 打木托件数
	private Integer salverGoodsNum;

	// 代打木托要求
	private String salverRequirement;

	// 打木托费用
	private BigDecimal salverGoodsAmount;

	// 是否有效
	private String active;

	// 创建时间
	private Date createTime;

	// 修改时间
	private Date modifyTime;

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
	 * @return the packageOrgCode .
	 */
	public String getPackageOrgCode() {
		return packageOrgCode;
	}

	/**
	 * @param packageOrgCode
	 *            the packageOrgCode to set.
	 */
	public void setPackageOrgCode(String packageOrgCode) {
		this.packageOrgCode = packageOrgCode;
	}

	/**
	 * @return the standGoodsNum .
	 */
	public Integer getStandGoodsNum() {
		return standGoodsNum;
	}

	/**
	 * @param standGoodsNum
	 *            the standGoodsNum to set.
	 */
	public void setStandGoodsNum(Integer standGoodsNum) {
		this.standGoodsNum = standGoodsNum;
	}

	/**
	 * @return the standRequirement .
	 */
	public String getStandRequirement() {
		return standRequirement;
	}

	/**
	 * @param standRequirement
	 *            the standRequirement to set.
	 */
	public void setStandRequirement(String standRequirement) {
		this.standRequirement = standRequirement;
	}

	/**
	 * @return the standGoodsSize .
	 */
	public String getStandGoodsSize() {
		return standGoodsSize;
	}

	/**
	 * @param standGoodsSize
	 *            the standGoodsSize to set.
	 */
	public void setStandGoodsSize(String standGoodsSize) {
		this.standGoodsSize = standGoodsSize;
	}

	/**
	 * @return the standGoodsVolume .
	 */
	public BigDecimal getStandGoodsVolume() {
		return standGoodsVolume;
	}

	/**
	 * @param standGoodsVolume
	 *            the standGoodsVolume to set.
	 */
	public void setStandGoodsVolume(BigDecimal standGoodsVolume) {
		this.standGoodsVolume = standGoodsVolume;
	}

	/**
	 * @return the boxGoodsNum .
	 */
	public Integer getBoxGoodsNum() {
		return boxGoodsNum;
	}

	/**
	 * @param boxGoodsNum
	 *            the boxGoodsNum to set.
	 */
	public void setBoxGoodsNum(Integer boxGoodsNum) {
		this.boxGoodsNum = boxGoodsNum;
	}

	/**
	 * @return the boxRequirement .
	 */
	public String getBoxRequirement() {
		return boxRequirement;
	}

	/**
	 * @param boxRequirement
	 *            the boxRequirement to set.
	 */
	public void setBoxRequirement(String boxRequirement) {
		this.boxRequirement = boxRequirement;
	}

	/**
	 * @return the boxGoodsSize .
	 */
	public String getBoxGoodsSize() {
		return boxGoodsSize;
	}

	/**
	 * @param boxGoodsSize
	 *            the boxGoodsSize to set.
	 */
	public void setBoxGoodsSize(String boxGoodsSize) {
		this.boxGoodsSize = boxGoodsSize;
	}

	/**
	 * @return the boxGoodsVolume .
	 */
	public BigDecimal getBoxGoodsVolume() {
		return boxGoodsVolume;
	}

	/**
	 * @param boxGoodsVolume
	 *            the boxGoodsVolume to set.
	 */
	public void setBoxGoodsVolume(BigDecimal boxGoodsVolume) {
		this.boxGoodsVolume = boxGoodsVolume;
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

	public Integer getSalverGoodsNum() {
		return salverGoodsNum;
	}

	public void setSalverGoodsNum(Integer salverGoodsNum) {
		this.salverGoodsNum = salverGoodsNum;
	}

	public String getSalverRequirement() {
		return salverRequirement;
	}

	public void setSalverRequirement(String salverRequirement) {
		this.salverRequirement = salverRequirement;
	}

	public BigDecimal getSalverGoodsAmount() {
		return salverGoodsAmount;
	}

	public void setSalverGoodsAmount(BigDecimal salverGoodsAmount) {
		this.salverGoodsAmount = salverGoodsAmount;
	}

}
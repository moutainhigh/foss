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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/vo/EffectivePlanVo.java
 * 
 * FILE NAME        	: EffectivePlanVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveExpressPlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveExpressPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;

/**
 * (时效区域VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-张斌,date:2012-11-21 下午15:13:10
 * </p>
 * 
 */
public class EffectiveExpressPlanVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5271842753989503848L;
	/** 
	 * 时效区域实体LIST
	 */
	private List<EffectiveExpressPlanEntity> effectivePlanEntityList;
	/** 
	 * 时效区域实体
	 */
	private EffectiveExpressPlanEntity effectivePlanEntity;
	/** 
	 * 时效区域明细实体LIST
	 */
	private List<EffectiveExpressPlanDetailEntity> effectivePlanDetailEntityList;
	/** 
	 * 时效区域明细实体
	 */
	private EffectiveExpressPlanDetailEntity effectivePlanDetailEntity;
	/** 
	 * 时效方案IDS
	 */
	private List<String> effectivePlans;
	/** 
	 * 产品信息
	 */
	private List<ProductEntity> productEntityList;
	
	/**
	 * 批量立即激活时效方案的生效时间
	 */
	private Date beginTime;
	/**
	 * 批量立即中止时效方案的中止时间
	 */
	private Date endTime;
	/**
	 * 获取 产品信息.
	 *
	 * @return the 产品信息
	 */
	public List<ProductEntity> getProductEntityList() {
		return productEntityList;
	}

	/**
	 * 设置 产品信息.
	 *
	 * @param productEntityList the new 产品信息
	 */
	public void setProductEntityList(List<ProductEntity> productEntityList) {
		this.productEntityList = productEntityList;
	}

	/**
	 * 获取 时效区域实体.
	 *
	 * @return the 时效区域实体
	 */
	public EffectiveExpressPlanEntity getEffectivePlanEntity() {
		return effectivePlanEntity;
	}

	/**
	 * 设置 时效区域实体.
	 *
	 * @param effectivePlanEntity the new 时效区域实体
	 */
	public void setEffectivePlanEntity(EffectiveExpressPlanEntity effectivePlanEntity) {
		this.effectivePlanEntity = effectivePlanEntity;
	}

	/**
	 * 获取 时效区域实体LIST.
	 *
	 * @return the 时效区域实体LIST
	 */
	public List<EffectiveExpressPlanEntity> getEffectivePlanEntityList() {
		return effectivePlanEntityList;
	}

	/**
	 * 设置 时效区域实体LIST.
	 *
	 * @param effectivePlanEntityList the new 时效区域实体LIST
	 */
	public void setEffectivePlanEntityList(
			List<EffectiveExpressPlanEntity> effectivePlanEntityList) {
		this.effectivePlanEntityList = effectivePlanEntityList;
	}

	/**
	 * 获取 时效区域明细实体LIST.
	 *
	 * @return the 时效区域明细实体LIST
	 */
	public List<EffectiveExpressPlanDetailEntity> getEffectivePlanDetailEntityList() {
		return effectivePlanDetailEntityList;
	}

	/**
	 * 设置 时效区域明细实体LIST.
	 *
	 * @param effectivePlanDetailEntityList the new 时效区域明细实体LIST
	 */
	public void setEffectivePlanDetailEntityList(
			List<EffectiveExpressPlanDetailEntity> effectivePlanDetailEntityList) {
		this.effectivePlanDetailEntityList = effectivePlanDetailEntityList;
	}

	/**
	 * 获取 时效区域明细实体.
	 *
	 * @return the 时效区域明细实体
	 */
	public EffectiveExpressPlanDetailEntity getEffectivePlanDetailEntity() {
		return effectivePlanDetailEntity;
	}

	/**
	 * 设置 时效区域明细实体.
	 *
	 * @param effectivePlanDetailEntity the new 时效区域明细实体
	 */
	public void setEffectivePlanDetailEntity(
			EffectiveExpressPlanDetailEntity effectivePlanDetailEntity) {
		this.effectivePlanDetailEntity = effectivePlanDetailEntity;
	}

	/**
	 * 获取 时效方案IDS.
	 *
	 * @return the 时效方案IDS
	 */
	public List<String> getEffectivePlans() {
		return effectivePlans;
	}

	/**
	 * 设置 时效方案IDS.
	 *
	 * @param effectivePlans the new 时效方案IDS
	 */
	public void setEffectivePlans(List<String> effectivePlans) {
		this.effectivePlans = effectivePlans;
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

}
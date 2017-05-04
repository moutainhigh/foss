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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/vo/PricingValuationVo.java
 * 
 * FILE NAME        	: PricingValuationVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;

/**
 * (增值服务VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-张斌,date:2012-10-16 下午15:13:10
 * </p>
 * 
 */
public class PricingValuationVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4480452754031715805L;
	/** 
	 * 增值服务查询结果
	 */
	private List<PriceValuationEntity> priceValuationEntityList;
	/** 
	 * 增值服务(查询条件、新增、修改)
	 */
	private PriceValuationEntity priceValuationEntity;
	/** 
	 * 计价方式明细(新增)
	 */
	private List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityList;
	/** 
	 * 计价方式明细(修改)
	 */
	private List<PriceCriteriaDetailEntity> updatePriceCriteriaDetailEntityList;
	/** 
	 * 计价方式明细(删除)
	 */
	private List<PriceCriteriaDetailEntity> deletePriceCriteriaDetailEntityList;
	
	
	/** 
	 * 增值服务类型
	 */
	private List<PriceEntity> priceEntityList;
	/**
	 * 增值服务
	 */
	private PriceEntity priceEntity;
	/** 
	 * 计价方式明细
	 */
	private String valuationId;
	/** 
	 * 要激活的增值服务ID
	 */
	private List<String> valuationIds;
	/**
	 * 当前服务器时间
	 */
	private Date nowTime;
	//根据MANA-1320修改
	/**
	 * 默认结束时间
	 */
	private Date defaultEndTime;	
    public Date getDefaultEndTime() {
		return defaultEndTime;
	}
	public void setDefaultEndTime(Date defaultEndTime) {
		this.defaultEndTime = defaultEndTime;
	}
    /**
     * 产品条目
     */
	private List<ProductItemEntity> productItemEntityList;
	/**
	 * 基础产品
	 */
	private List<ProductEntity> productEntityList;
	/**
	 * 货物类型
	 */
	private List<GoodsTypeEntity> goodsTypeEntityList;
	/**
	 * 限保物品
	 */
	private List<LimitedWarrantyItemsEntity> limitedWarrantyItemsEntityList;
	
	/**
	 * 获取 限保物品.
	 *
	 * @return the 限保物品
	 */
	public List<LimitedWarrantyItemsEntity> getLimitedWarrantyItemsEntityList() {
		return limitedWarrantyItemsEntityList;
	}

	/**
	 * 设置 限保物品.
	 *
	 * @param limitedWarrantyItemsEntityList the new 限保物品
	 */
	public void setLimitedWarrantyItemsEntityList(
			List<LimitedWarrantyItemsEntity> limitedWarrantyItemsEntityList) {
		this.limitedWarrantyItemsEntityList = limitedWarrantyItemsEntityList;
	}

	/**
	 * 获取 货物类型.
	 *
	 * @return the 货物类型
	 */
	public List<GoodsTypeEntity> getGoodsTypeEntityList() {
		return goodsTypeEntityList;
	}

	/**
	 * 设置 货物类型.
	 *
	 * @param goodsTypeEntityList the new 货物类型
	 */
	public void setGoodsTypeEntityList(List<GoodsTypeEntity> goodsTypeEntityList) {
		this.goodsTypeEntityList = goodsTypeEntityList;
	}

	/**
	 * 获取 基础产品.
	 *
	 * @return the 基础产品
	 */
	public List<ProductEntity> getProductEntityList() {
		return productEntityList;
	}

	/**
	 * 设置 基础产品.
	 *
	 * @param productEntityList the new 基础产品
	 */
	public void setProductEntityList(List<ProductEntity> productEntityList) {
		this.productEntityList = productEntityList;
	}

	/**
	 * 获取 产品条目.
	 *
	 * @return the 产品条目
	 */
	public List<ProductItemEntity> getProductItemEntityList() {
		return productItemEntityList;
	}

	/**
	 * 设置 产品条目.
	 *
	 * @param productItemEntityList the new 产品条目
	 */
	public void setProductItemEntityList(
			List<ProductItemEntity> productItemEntityList) {
		this.productItemEntityList = productItemEntityList;
	}

	/**
	 * 获取 增值服务查询结果.
	 *
	 * @return the 增值服务查询结果
	 */
	public List<PriceValuationEntity> getPriceValuationEntityList() {
		return priceValuationEntityList;
	}

	/**
	 * 设置 增值服务查询结果.
	 *
	 * @param priceValuationEntityList the new 增值服务查询结果
	 */
	public void setPriceValuationEntityList(
			List<PriceValuationEntity> priceValuationEntityList) {
		this.priceValuationEntityList = priceValuationEntityList;
	}

	/**
	 * 获取 增值服务(查询条件、新增、修改).
	 *
	 * @return the 增值服务(查询条件、新增、修改)
	 */
	public PriceValuationEntity getPriceValuationEntity() {
		return priceValuationEntity;
	}

	/**
	 * 设置 增值服务(查询条件、新增、修改).
	 *
	 * @param priceValuationEntity the new 增值服务(查询条件、新增、修改)
	 */
	public void setPriceValuationEntity(
			PriceValuationEntity priceValuationEntity) {
		this.priceValuationEntity = priceValuationEntity;
	}

	/**
	 * 获取 增值服务类型.
	 *
	 * @return the 增值服务类型
	 */
	public List<PriceEntity> getPriceEntityList() {
		return priceEntityList;
	}

	/**
	 * 设置 增值服务类型.
	 *
	 * @param priceEntityList the new 增值服务类型
	 */
	public void setPriceEntityList(List<PriceEntity> priceEntityList) {
		this.priceEntityList = priceEntityList;
	}

	/**
	 * 获取 计价方式明细(新增).
	 *
	 * @return the 计价方式明细(新增)
	 */
	public List<PriceCriteriaDetailEntity> getPriceCriteriaDetailEntityList() {
		return priceCriteriaDetailEntityList;
	}

	/**
	 * 设置 计价方式明细(新增).
	 *
	 * @param priceCriteriaDetailEntityList the new 计价方式明细(新增)
	 */
	public void setPriceCriteriaDetailEntityList(
			List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityList) {
		this.priceCriteriaDetailEntityList = priceCriteriaDetailEntityList;
	}

	/**
	 * 获取 计价方式明细.
	 *
	 * @return the 计价方式明细
	 */
	public String getValuationId() {
		return valuationId;
	}

	/**
	 * 设置 计价方式明细.
	 *
	 * @param valuationId the new 计价方式明细
	 */
	public void setValuationId(String valuationId) {
		this.valuationId = valuationId;
	}

	/**
	 * 获取 要激活的增值服务ID.
	 *
	 * @return the 要激活的增值服务ID
	 */
	public List<String> getValuationIds() {
		return valuationIds;
	}

	/**
	 * 设置 要激活的增值服务ID.
	 *
	 * @param valuationIds the new 要激活的增值服务ID
	 */
	public void setValuationIds(List<String> valuationIds) {
		this.valuationIds = valuationIds;
	}

	/**
	 * 获取 计价方式明细(修改).
	 *
	 * @return the 计价方式明细(修改)
	 */
	public List<PriceCriteriaDetailEntity> getUpdatePriceCriteriaDetailEntityList() {
		return updatePriceCriteriaDetailEntityList;
	}

	/**
	 * 设置 计价方式明细(修改).
	 *
	 * @param updatePriceCriteriaDetailEntityList the new 计价方式明细(修改)
	 */
	public void setUpdatePriceCriteriaDetailEntityList(
			List<PriceCriteriaDetailEntity> updatePriceCriteriaDetailEntityList) {
		this.updatePriceCriteriaDetailEntityList = updatePriceCriteriaDetailEntityList;
	}

	/**
	 * 获取 增值服务.
	 *
	 * @return the 增值服务
	 */
	public PriceEntity getPriceEntity() {
		return priceEntity;
	}

	/**
	 * 设置 增值服务.
	 *
	 * @param priceEntity the new 增值服务
	 */
	public void setPriceEntity(PriceEntity priceEntity) {
		this.priceEntity = priceEntity;
	}

	/**
	 * 获取 当前服务器时间.
	 *
	 * @return the 当前服务器时间
	 */
	public Date getNowTime() {
		return nowTime;
	}

	/**
	 * 设置 当前服务器时间.
	 *
	 * @param nowTime the new 当前服务器时间
	 */
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}

	public List<PriceCriteriaDetailEntity> getDeletePriceCriteriaDetailEntityList() {
		return deletePriceCriteriaDetailEntityList;
	}

	public void setDeletePriceCriteriaDetailEntityList(
			List<PriceCriteriaDetailEntity> deletePriceCriteriaDetailEntityList) {
		this.deletePriceCriteriaDetailEntityList = deletePriceCriteriaDetailEntityList;
	}

}
package com.deppon.foss.module.pickup.pricing.api.shared.vo.pop;

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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pop/api/shared/vo/PricingValuationVo.java
 * 
 * FILE NAME        	: PricingValuationVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerIndustryEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceValueAddedDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceValueAddedEntity;

/**
 * 
 * <p>
 * Description：增值服务vo<br />
 * </p>
 * @title PricingValuationVo.java
 * @package com.deppon.foss.module.pickup.pop.api.shard.vo 
 * @author xx
 * @version 0.1 2014年10月11日
 */
public class PriceValueAddedVo implements Serializable {
	/** 
	 * @fields serialVersionUID 
	 */ 
	
	private static final long serialVersionUID = -3844582929940525875L;
	/**
	 * 增值服务对象
	 */
	private PriceValueAddedEntity priceValuationEntity;
	/**
	 * 增值服务详细列表
	 */
	private List<PriceValueAddedDetailEntity> priceCriteriaDetailEntityList;
	
	/** 
	 * 增值服务查询结果
	 */
	private List<PriceValueAddedEntity> priceValuationEntityList;

	/** 
	 * 计价方式明细(新增)
	 */
	private List<PriceValueAddedDetailEntity> priceValueAddedDetailEntityList;
	/** 
	 * 计价方式明细(修改)
	 */
	private List<PriceValueAddedDetailEntity> updatePriceCriteriaDetailEntityList;
	/** 
	 * 计价方式明细(删除)
	 */
	private List<PriceValueAddedDetailEntity> deletePriceCriteriaDetailEntityList;
	
	
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
	private List<String> valueAddedIds;
	/**
	 * 当前服务器时间
	 */
	private Date nowTime;
	//根据MANA-1320修改
	/**
	 * 默认结束时间
	 */
	private Date defaultEndTime;
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
	 * 二级行业list
	 */
	private List<CustomerIndustryEntity> CustomerIndustryEntityList;
	/**
	 * <p>
	 * Description:productItemEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public List<ProductItemEntity> getProductItemEntityList() {
		return productItemEntityList;
	}
	/**
	 * <p>
	 * Description:productItemEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setProductItemEntityList(List<ProductItemEntity> productItemEntityList) {
		this.productItemEntityList = productItemEntityList;
	}
	/**
	 * <p>
	 * Description:productEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public List<ProductEntity> getProductEntityList() {
		return productEntityList;
	}
	/**
	 * <p>
	 * Description:productEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setProductEntityList(List<ProductEntity> productEntityList) {
		this.productEntityList = productEntityList;
	}
	/**
	 * <p>
	 * Description:goodsTypeEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public List<GoodsTypeEntity> getGoodsTypeEntityList() {
		return goodsTypeEntityList;
	}
	/**
	 * <p>
	 * Description:goodsTypeEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setGoodsTypeEntityList(List<GoodsTypeEntity> goodsTypeEntityList) {
		this.goodsTypeEntityList = goodsTypeEntityList;
	}
	/**
	 * <p>
	 * Description:limitedWarrantyItemsEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public String getValuationId() {
		return valuationId;
	}
	/**
	 * <p>
	 * Description:limitedWarrantyItemsEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setValuationId(String valuationId) {
		this.valuationId = valuationId;
	}
	/**
	 * <p>
	 * Description:limitedWarrantyItemsEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public List<LimitedWarrantyItemsEntity> getLimitedWarrantyItemsEntityList() {
		return limitedWarrantyItemsEntityList;
	}
	/**
	 * <p>
	 * Description:limitedWarrantyItemsEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setLimitedWarrantyItemsEntityList(List<LimitedWarrantyItemsEntity> limitedWarrantyItemsEntityList) {
		this.limitedWarrantyItemsEntityList = limitedWarrantyItemsEntityList;
	}
	/**
	 * <p>
	 * Description:limitedWarrantyItemsEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public PriceValueAddedEntity getPriceValuationEntity() {
		return priceValuationEntity;
	}
	/**
	 * <p>
	 * Description:limitedWarrantyItemsEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setPriceValuationEntity(PriceValueAddedEntity priceValuationEntity) {
		this.priceValuationEntity = priceValuationEntity;
	}
	/**
	 * <p>
	 * Description:priceValueAddedEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public List<PriceValueAddedDetailEntity> getPriceCriteriaDetailEntityList() {
		return priceCriteriaDetailEntityList;
	}
	/**
	 * <p>
	 * Description:priceValueAddedEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setPriceCriteriaDetailEntityList(
			List<PriceValueAddedDetailEntity> priceCriteriaDetailEntityList) {
		this.priceCriteriaDetailEntityList = priceCriteriaDetailEntityList;
	}
	/**
	 * <p>
	 * Description:priceValueAddedDetailEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public List<PriceValueAddedEntity> getPriceValuationEntityList() {
		return priceValuationEntityList;
	}
	/**
	 * <p>
	 * Description:priceValueAddedDetailEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setPriceValuationEntityList(
			List<PriceValueAddedEntity> priceValuationEntityList) {
		this.priceValuationEntityList = priceValuationEntityList;
	}
	/**
	 * <p>
	 * Description:priceValueAddedDetailEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public List<PriceValueAddedDetailEntity> getPriceValueAddedDetailEntityList() {
		return priceValueAddedDetailEntityList;
	}
	/**
	 * <p>
	 * Description:priceValueAddedDetailEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setPriceValueAddedDetailEntityList(List<PriceValueAddedDetailEntity> priceValueAddedDetailEntityList) {
		this.priceValueAddedDetailEntityList = priceValueAddedDetailEntityList;
	}
	/**
	 * <p>
	 * Description:updatePriceValueAddedDetailEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public List<PriceValueAddedDetailEntity> getUpdatePriceCriteriaDetailEntityList() {
		return updatePriceCriteriaDetailEntityList;
	}
	/**
	 * <p>
	 * Description:updatePriceValueAddedDetailEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setUpdatePriceCriteriaDetailEntityList(
			List<PriceValueAddedDetailEntity> updatePriceCriteriaDetailEntityList) {
		this.updatePriceCriteriaDetailEntityList = updatePriceCriteriaDetailEntityList;
	}
	/**
	 * <p>
	 * Description:updatePriceValueAddedDetailEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public List<PriceValueAddedDetailEntity> getDeletePriceCriteriaDetailEntityList() {
		return deletePriceCriteriaDetailEntityList;
	}
	/**
	 * <p>
	 * Description:updatePriceValueAddedDetailEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setDeletePriceCriteriaDetailEntityList(
			List<PriceValueAddedDetailEntity> deletePriceCriteriaDetailEntityList) {
		this.deletePriceCriteriaDetailEntityList = deletePriceCriteriaDetailEntityList;
	}
	/**
	 * <p>
	 * Description:priceEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public List<PriceEntity> getPriceEntityList() {
		return priceEntityList;
	}
	/**
	 * <p>
	 * Description:priceEntityList<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setPriceEntityList(List<PriceEntity> priceEntityList) {
		this.priceEntityList = priceEntityList;
	}
	/**
	 * <p>
	 * Description:priceEntity<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public PriceEntity getPriceEntity() {
		return priceEntity;
	}
	/**
	 * <p>
	 * Description:priceEntity<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setPriceEntity(PriceEntity priceEntity) {
		this.priceEntity = priceEntity;
	}
	/**
	 * <p>
	 * Description:valueAddedIds<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public List<String> getValueAddedIds() {
		return valueAddedIds;
	}
	/**
	 * <p>
	 * Description:valueAddedIds<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setValueAddedIds(List<String> valueAddedIds) {
		this.valueAddedIds = valueAddedIds;
	}
	/**
	 * <p>
	 * Description:nowTime<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public Date getNowTime() {
		return nowTime;
	}
	/**
	 * <p>
	 * Description:nowTime<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setNowTime(Date nowTime) {
		this.nowTime = nowTime;
	}
	/**
	 * <p>
	 * Description:defaultEndTime<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public Date getDefaultEndTime() {
		return defaultEndTime;
	}
	/**
	 * <p>
	 * Description:defaultEndTime<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setDefaultEndTime(Date defaultEndTime) {
		this.defaultEndTime = defaultEndTime;
	}
	public List<CustomerIndustryEntity> getCustomerIndustryEntityList() {
		return CustomerIndustryEntityList;
	}
	public void setCustomerIndustryEntityList(
			List<CustomerIndustryEntity> customerIndustryEntityList) {
		CustomerIndustryEntityList = customerIndustryEntityList;
	}

}
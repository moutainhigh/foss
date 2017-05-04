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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/PriceEntity.java
 * 
 * FILE NAME        	: PriceEntity.java
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
 * @Description: 行政区域与时效、汽运、空运价格区域关系表
 * DistrictRegionEntity.java Create on 2013-4-17 下午2:09:13
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class DistrictRegionExpressEntity extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4851574344338115077L;

    /**
     * 主键
     */
    private String id;
    /**
     * 行政区域CODE
     */
    private String districtCode;
    /**
     * 时效区域ID
     */
    private String effectiveRegionIds;
    /**
     * 时效区域Name
     */
    private String effectiveRegionNames;
    /**
     * 汽运价格区域ID
     */
    private String priceAutoRegionIds;
    /**
     * 汽运价格区域Name
     */
    private String priceAutoRegionNames;
    /**
     * 空运价格区域ID
     */
    private String priceAirRegionIds;
    /**
     * 空运价格区域Name
     */
    private String priceAirRegionNames;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.entity.BaseEntity#getId()
	 */
	public String getId() {
		return id;
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.framework.entity.BaseEntity#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取 行政区域CODE.
	 *
	 * @return the 行政区域CODE
	 */
	public String getDistrictCode() {
		return districtCode;
	}
	
	/**
	 * 设置 行政区域CODE.
	 *
	 * @param districtCode the new 行政区域CODE
	 */
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	
	/**
	 * 获取 时效区域ID.
	 *
	 * @return the 时效区域ID
	 */
	public String getEffectiveRegionIds() {
		return effectiveRegionIds;
	}
	
	/**
	 * 设置 时效区域ID.
	 *
	 * @param effectiveRegionIds the new 时效区域ID
	 */
	public void setEffectiveRegionIds(String effectiveRegionIds) {
		this.effectiveRegionIds = effectiveRegionIds;
	}
	
	/**
	 * 获取 汽运价格区域ID.
	 *
	 * @return the 汽运价格区域ID
	 */
	public String getPriceAutoRegionIds() {
		return priceAutoRegionIds;
	}
	
	/**
	 * 设置 汽运价格区域ID.
	 *
	 * @param priceAutoRegionIds the new 汽运价格区域ID
	 */
	public void setPriceAutoRegionIds(String priceAutoRegionIds) {
		this.priceAutoRegionIds = priceAutoRegionIds;
	}
	
	/**
	 * 获取 空运价格区域ID.
	 *
	 * @return the 空运价格区域ID
	 */
	public String getPriceAirRegionIds() {
		return priceAirRegionIds;
	}
	
	/**
	 * 设置 空运价格区域ID.
	 *
	 * @param priceAirRegionIds the new 空运价格区域ID
	 */
	public void setPriceAirRegionIds(String priceAirRegionIds) {
		this.priceAirRegionIds = priceAirRegionIds;
	}

	public String getEffectiveRegionNames() {
		return effectiveRegionNames;
	}

	public void setEffectiveRegionNames(String effectiveRegionNames) {
		this.effectiveRegionNames = effectiveRegionNames;
	}

	public String getPriceAutoRegionNames() {
		return priceAutoRegionNames;
	}

	public void setPriceAutoRegionNames(String priceAutoRegionNames) {
		this.priceAutoRegionNames = priceAutoRegionNames;
	}

	public String getPriceAirRegionNames() {
		return priceAirRegionNames;
	}

	public void setPriceAirRegionNames(String priceAirRegionNames) {
		this.priceAirRegionNames = priceAirRegionNames;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
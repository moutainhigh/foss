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
public class DistrictRegionEntity extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 8178707383441942728L;
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
     * 汽运价格区域-到达ID
     */
    private String priceArriveRegionIds;
    /**
     * 汽运价格区域-到达 Name
     */
    private String priceArriveRegionNames;
    /**
     * 增值价格区域-到达ID
     */
    private String priceValueAddRegionIds;
    /**
     * 增值价格区域-到达 Name
     */
    private String priceValueAddRegionNames;
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
    
    /**
     * 快递时效区域ID.
     */
    private String expressEffectiveRegionIds;
    
    /**
     * 快递时效区域Name.
     */
    private String expressEffectiveRegionNames;
    
    
    /**
     * 快递价格区域ID.
     */
    private String expressPriceRegionIds;
    
    /**
     * 快递价格区域Name.
     */
    private String expressPriceRegionNames;
    
    
    /**
     * 精准大票价格出发区域ID
     */
    private String priceBigRegionIds;
    /**
     * 精准大票价格出发区域Name
     */
    private String priceBigRegionNames;
    /**
     * 精准大票价格到达区域-到达ID
     */
    private String priceBigArriveRegionIds;
    /**
     * 精准大票价格到达区域-到达 Name
     */
    private String priceBigArriveRegionNames;
    
    
    /**==
     * 首续重价格出发区域-出发ID
     */
    private String priceEcRegionIds;
    /**
     * 首续重价格出发区域-出发 Name
     */
    private String priceEcRegionNames;
    /**
     * 首续重价格到达区域-到达ID
     */
    private String priceEcArriveRegionIds;
    /**
     * 首续重价格到达区域-到达Name
     */
    private String priceEcArriveRegionNames;
    

    /**
     * 获取 快递时效区域ID.
     *
     * @return the 快递时效区域ID
     */
    public String getExpressEffectiveRegionIds() {
	return expressEffectiveRegionIds;
    }

    /**
     * 设置 快递时效区域ID.
     *
     * @param expressEffectiveRegionIds the new 快递时效区域ID
     */
    public void setExpressEffectiveRegionIds(String expressEffectiveRegionIds) {
	this.expressEffectiveRegionIds = expressEffectiveRegionIds;
    }

    /**
     * 获取 快递时效区域Name.
     *
     * @return the 快递时效区域Name
     */
    public String getExpressEffectiveRegionNames() {
	return expressEffectiveRegionNames;
    }

    /**
     * 设置 快递时效区域Name.
     *
     * @param expressEffectiveRegionNames the new 快递时效区域Name
     */
    public void setExpressEffectiveRegionNames(
	    String expressEffectiveRegionNames) {
	this.expressEffectiveRegionNames = expressEffectiveRegionNames;
    }

    /**
     * 获取 快递价格区域ID.
     *
     * @return the 快递价格区域ID
     */
    public String getExpressPriceRegionIds() {
	return expressPriceRegionIds;
    }

    /**
     * 设置 快递价格区域ID.
     *
     * @param expressPriceRegionIds the new 快递价格区域ID
     */
    public void setExpressPriceRegionIds(String expressPriceRegionIds) {
	this.expressPriceRegionIds = expressPriceRegionIds;
    }

    /**
     * 获取 快递价格区域Name.
     *
     * @return the 快递价格区域Name
     */
    public String getExpressPriceRegionNames() {
	return expressPriceRegionNames;
    }

    /**
     * 设置 快递价格区域Name.
     *
     * @param expressPriceRegionNames the new 快递价格区域Name
     */
    public void setExpressPriceRegionNames(String expressPriceRegionNames) {
	this.expressPriceRegionNames = expressPriceRegionNames;
    }
	
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
 

	public String getPriceArriveRegionIds() {
		return priceArriveRegionIds;
	}

	public void setPriceArriveRegionIds(String priceArriveRegionIds) {
		this.priceArriveRegionIds = priceArriveRegionIds;
	}

	public String getPriceArriveRegionNames() {
		return priceArriveRegionNames;
	}

	public void setPriceArriveRegionNames(String priceArriveRegionNames) {
		this.priceArriveRegionNames = priceArriveRegionNames;
	}

	public String getPriceValueAddRegionIds() {
		return priceValueAddRegionIds;
	}

	public void setPriceValueAddRegionIds(String priceValueAddRegionIds) {
		this.priceValueAddRegionIds = priceValueAddRegionIds;
	}

	public String getPriceValueAddRegionNames() {
		return priceValueAddRegionNames;
	}

	public void setPriceValueAddRegionNames(String priceValueAddRegionNames) {
		this.priceValueAddRegionNames = priceValueAddRegionNames;
	}

	public String getPriceBigRegionIds() {
		return priceBigRegionIds;
	}

	public void setPriceBigRegionIds(String priceBigRegionIds) {
		this.priceBigRegionIds = priceBigRegionIds;
	}

	public String getPriceBigRegionNames() {
		return priceBigRegionNames;
	}

	public void setPriceBigRegionNames(String priceBigRegionNames) {
		this.priceBigRegionNames = priceBigRegionNames;
	}

	public String getPriceBigArriveRegionIds() {
		return priceBigArriveRegionIds;
	}

	public void setPriceBigArriveRegionIds(String priceBigArriveRegionIds) {
		this.priceBigArriveRegionIds = priceBigArriveRegionIds;
	}

	public String getPriceBigArriveRegionNames() {
		return priceBigArriveRegionNames;
	}

	public void setPriceBigArriveRegionNames(String priceBigArriveRegionNames) {
		this.priceBigArriveRegionNames = priceBigArriveRegionNames;
	}
	
	
	/*
	 * 设置首续重价格出发到达区域ID，NAME
	 * 
	 */
    public String getPriceEcRegionIds() {
		return priceEcRegionIds;
	}

	public void setPriceEcRegionIds(String priceEcRegionIds) {
		this.priceEcRegionIds = priceEcRegionIds;
	}

	public String getPriceEcRegionNames() {
		return priceEcRegionNames;
	}

	public void setPriceEcRegionNames(String priceEcRegionNames) {
		this.priceEcRegionNames = priceEcRegionNames;
	}

	public String getPriceEcArriveRegionIds() {
		return priceEcArriveRegionIds;
	}

	public void setPriceEcArriveRegionIds(String priceEcArriveRegionIds) {
		this.priceEcArriveRegionIds = priceEcArriveRegionIds;
	}

	public String getPriceEcArriveRegionNames() {
		return priceEcArriveRegionNames;
	}

	public void setPriceEcArriveRegionNames(String priceEcArriveRegionNames) {
		this.priceEcArriveRegionNames = priceEcArriveRegionNames;
	}
}
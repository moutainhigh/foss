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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/PriceValuationDto.java
 * 
 * FILE NAME        	: PriceValuationDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 计费规则DTO
 * @author DP-Foss-YueHongJie
 * @date 2012-11-3 下午6:36:23
 */
public class PriceValuationDto implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 435375649437294519L;
	/**
	 * 货物类型ID
	 */
	private String goodsTypeId;
	/**
	 *  货物类型CODE
	 */
	private String goodsTypeCode;
	/**
	 * 货物类型Name
	 */
	private String goodsTypeName;
	/**
	 *  产品ID
	 */
	private String productId;
	/**
	 *  产品CODE
	 */
	private String productCode;
	/**
	 *  产品名称
	 */
	private String productName;
	/**
	 *  始发区域ID
	 */
	private String deptRegionId;
	/**
	 *  始发区域NAME
	 */
	private String deptRegionName;
	/**
	 *  目的区域ID
	 */
	private String arrvRegionId;
	/**
	 * 目的区域NAME
	 */
	private String arrvRegionName;
	/**
	 * 渠道CODE
	 */
	private String salesChannelCode;
	/**
	 * 渠道ID
	 */
	private String salesChannelId;
	/**
	 * 计价条目ID
	 */
	private String pricingEntryId;
	/**
	 * 计价条目CODE
	 */
	private String pricingEntryCode;
	/**
	 * 计价名称
	 */
	private String pricingEntryName;
	/**
	 * 价格方案主信息ID
	 */
	private String pricePlanId;
	/**
	 *  价格方案主信息CODE
	 */
	private String pricePlanCode;
	/**
	 * 市场活动ID
	 */
	private String marketingEventId;
	/**
	 * 市场活动CODE
	 */
	private String marketingEventCode;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 版本号
	 */
	private Long versionNo;
	/**
	 * 是否激活
	 */
	private String active;
	/**
	 * 有效起始时间
	 */
	private Date beginTime;
	/**
	 * 有效截止时间
	 */
	private Date endTime;
	/**
	 *  长途还是短途
	 */
	private String longOrShort;
	/**
	 * 规则类型
	 */
	private String type;
	/**
	 * 币种
	 */
	private String currencyCode;
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 名称
	 */
	private String name;
	/**
	 *  最后修改人姓名
	 */
	private String modifyUserName;
	/**
	 *  创建人姓名
	 */
	private String createUserName;
	/**
	 *  最后修改人所在部门
	 */
	private String modifyOrgCode;
	/**
	 *  创建人所在部门
	 */
	private String createOrgCode;
	/**
	 *  是否集中接货
	 */
	private String centralizePickup;
	/**
	 *  航班班次
	 */
	private String lightShift;
	/**
	 *  计价方式ID
	 */
	private String criteriaId;
	/**
	 *  计费规则ID集合
	 */
	private List<String> valuationIds;
	/**
	 *  收货日期
	 */
	private Date receiveDate;
	/**
	 *  是否接货
	 */
	private String isReceiveGoods;
	/**
	 *  航班号
	 */
	private String flightShift;
	/**
	 *  子类型
	 */
	private String subType;
	
	/**
	 * 获取 子类型.
	 *
	 * @return the 子类型
	 */
	public String getSubType() {
	    return subType;
	}



	
	/**
	 * 设置 子类型.
	 *
	 * @param subType the new 子类型
	 */
	public void setSubType(String subType) {
	    this.subType = subType;
	}



	/**
	 * 获取 航班号.
	 *
	 * @return the 航班号
	 */
	public String getFlightShift() {
	    return flightShift;
	}


	
	/**
	 * 设置 航班号.
	 *
	 * @param flightShift the new 航班号
	 */
	public void setFlightShift(String flightShift) {
	    this.flightShift = flightShift;
	}


	/**
	 * 获取 是否接货.
	 *
	 * @return the 是否接货
	 */
	public String getIsReceiveGoods() {
	    return isReceiveGoods;
	}

	
	/**
	 * 设置 是否接货.
	 *
	 * @param isReceiveGoods the new 是否接货
	 */
	public void setIsReceiveGoods(String isReceiveGoods) {
	    this.isReceiveGoods = isReceiveGoods;
	}

	/**
	 * 获取 货物类型ID.
	 *
	 * @return the 货物类型ID
	 */
	public String getGoodsTypeId() {
            return goodsTypeId;
        }
        
        /**
         * 设置 货物类型ID.
         *
         * @param goodsTypeId the new 货物类型ID
         */
        public void setGoodsTypeId(String goodsTypeId) {
            this.goodsTypeId = goodsTypeId;
        }
        
        /**
         * 获取 货物类型CODE.
         *
         * @return the 货物类型CODE
         */
        public String getGoodsTypeCode() {
            return goodsTypeCode;
        }
        
        /**
         * 设置 货物类型CODE.
         *
         * @param goodsTypeCode the new 货物类型CODE
         */
        public void setGoodsTypeCode(String goodsTypeCode) {
            this.goodsTypeCode = goodsTypeCode;
        }
        
        /**
         * 获取 货物类型Name.
         *
         * @return the 货物类型Name
         */
        public String getGoodsTypeName() {
            return goodsTypeName;
        }
        
        /**
         * 设置 货物类型Name.
         *
         * @param goodsTypeName the new 货物类型Name
         */
        public void setGoodsTypeName(String goodsTypeName) {
            this.goodsTypeName = goodsTypeName;
        }
        
        /**
         * 获取 产品ID.
         *
         * @return the 产品ID
         */
        public String getProductId() {
            return productId;
        }
        
        /**
         * 设置 产品ID.
         *
         * @param productId the new 产品ID
         */
        public void setProductId(String productId) {
            this.productId = productId;
        }
        
        /**
         * 获取 产品名称.
         *
         * @return the 产品名称
         */
        public String getProductName() {
            return productName;
        }
        
        /**
         * 设置 产品名称.
         *
         * @param productName the new 产品名称
         */
        public void setProductName(String productName) {
            this.productName = productName;
        }
        
        /**
         * 获取 始发区域ID.
         *
         * @return the 始发区域ID
         */
        public String getDeptRegionId() {
            return deptRegionId;
        }
        
        /**
         * 设置 始发区域ID.
         *
         * @param deptRegionId the new 始发区域ID
         */
        public void setDeptRegionId(String deptRegionId) {
            this.deptRegionId = deptRegionId;
        }
        
        /**
         * 获取 始发区域NAME.
         *
         * @return the 始发区域NAME
         */
        public String getDeptRegionName() {
            return deptRegionName;
        }
        
        /**
         * 设置 始发区域NAME.
         *
         * @param deptRegionName the new 始发区域NAME
         */
        public void setDeptRegionName(String deptRegionName) {
            this.deptRegionName = deptRegionName;
        }
        
        /**
         * 获取 目的区域ID.
         *
         * @return the 目的区域ID
         */
        public String getArrvRegionId() {
            return arrvRegionId;
        }
        
        /**
         * 设置 目的区域ID.
         *
         * @param arrvRegionId the new 目的区域ID
         */
        public void setArrvRegionId(String arrvRegionId) {
            this.arrvRegionId = arrvRegionId;
        }
        
        /**
         * 获取 目的区域NAME.
         *
         * @return the 目的区域NAME
         */
        public String getArrvRegionName() {
            return arrvRegionName;
        }
        
        /**
         * 设置 目的区域NAME.
         *
         * @param arrvRegionName the new 目的区域NAME
         */
        public void setArrvRegionName(String arrvRegionName) {
            this.arrvRegionName = arrvRegionName;
        }
        
        /**
         * 获取 渠道CODE.
         *
         * @return the 渠道CODE
         */
        public String getSalesChannelCode() {
            return salesChannelCode;
        }
        
        /**
         * 设置 渠道CODE.
         *
         * @param salesChannelCode the new 渠道CODE
         */
        public void setSalesChannelCode(String salesChannelCode) {
            this.salesChannelCode = salesChannelCode;
        }
        
        /**
         * 获取 渠道ID.
         *
         * @return the 渠道ID
         */
        public String getSalesChannelId() {
            return salesChannelId;
        }
        
        /**
         * 设置 渠道ID.
         *
         * @param salesChannelId the new 渠道ID
         */
        public void setSalesChannelId(String salesChannelId) {
            this.salesChannelId = salesChannelId;
        }
        
        /**
         * 获取 计价条目ID.
         *
         * @return the 计价条目ID
         */
        public String getPricingEntryId() {
            return pricingEntryId;
        }
        
        /**
         * 设置 计价条目ID.
         *
         * @param pricingEntryId the new 计价条目ID
         */
        public void setPricingEntryId(String pricingEntryId) {
            this.pricingEntryId = pricingEntryId;
        }
        
        /**
         * 获取 计价条目CODE.
         *
         * @return the 计价条目CODE
         */
        public String getPricingEntryCode() {
            return pricingEntryCode;
        }
        
        /**
         * 设置 计价条目CODE.
         *
         * @param pricingEntryCode the new 计价条目CODE
         */
        public void setPricingEntryCode(String pricingEntryCode) {
            this.pricingEntryCode = pricingEntryCode;
        }
        
        /**
         * 获取 计价名称.
         *
         * @return the 计价名称
         */
        public String getPricingEntryName() {
            return pricingEntryName;
        }
        
        /**
         * 设置 计价名称.
         *
         * @param pricingEntryName the new 计价名称
         */
        public void setPricingEntryName(String pricingEntryName) {
            this.pricingEntryName = pricingEntryName;
        }
        
        /**
         * 获取 价格方案主信息ID.
         *
         * @return the 价格方案主信息ID
         */
        public String getPricePlanId() {
            return pricePlanId;
        }
        
        /**
         * 设置 价格方案主信息ID.
         *
         * @param pricePlanId the new 价格方案主信息ID
         */
        public void setPricePlanId(String pricePlanId) {
            this.pricePlanId = pricePlanId;
        }
        
        /**
         * 获取 价格方案主信息CODE.
         *
         * @return the 价格方案主信息CODE
         */
        public String getPricePlanCode() {
            return pricePlanCode;
        }
        
        /**
         * 设置 价格方案主信息CODE.
         *
         * @param pricePlanCode the new 价格方案主信息CODE
         */
        public void setPricePlanCode(String pricePlanCode) {
            this.pricePlanCode = pricePlanCode;
        }
        
        /**
         * 获取 市场活动ID.
         *
         * @return the 市场活动ID
         */
        public String getMarketingEventId() {
            return marketingEventId;
        }
        
        /**
         * 设置 市场活动ID.
         *
         * @param marketingEventId the new 市场活动ID
         */
        public void setMarketingEventId(String marketingEventId) {
            this.marketingEventId = marketingEventId;
        }
        
        /**
         * 获取 市场活动CODE.
         *
         * @return the 市场活动CODE
         */
        public String getMarketingEventCode() {
            return marketingEventCode;
        }
        
        /**
         * 设置 市场活动CODE.
         *
         * @param marketingEventCode the new 市场活动CODE
         */
        public void setMarketingEventCode(String marketingEventCode) {
            this.marketingEventCode = marketingEventCode;
        }
        
        /**
         * 获取 描述.
         *
         * @return the 描述
         */
        public String getDescription() {
            return description;
        }
        
        /**
         * 设置 描述.
         *
         * @param description the new 描述
         */
        public void setDescription(String description) {
            this.description = description;
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
         * 获取 是否激活.
         *
         * @return the 是否激活
         */
        public String getActive() {
            return active;
        }
        
        /**
         * 设置 是否激活.
         *
         * @param active the new 是否激活
         */
        public void setActive(String active) {
            this.active = active;
        }
        
        /**
         * 获取 有效起始时间.
         *
         * @return the 有效起始时间
         */
        public Date getBeginTime() {
            return beginTime;
        }
        
        /**
         * 设置 有效起始时间.
         *
         * @param beginTime the new 有效起始时间
         */
        public void setBeginTime(Date beginTime) {
            this.beginTime = beginTime;
        }
        
        /**
         * 获取 有效截止时间.
         *
         * @return the 有效截止时间
         */
        public Date getEndTime() {
            return endTime;
        }
        
        /**
         * 设置 有效截止时间.
         *
         * @param endTime the new 有效截止时间
         */
        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }
        
        /**
         * 获取 长途还是短途.
         *
         * @return the 长途还是短途
         */
        public String getLongOrShort() {
            return longOrShort;
        }
        
        /**
         * 设置 长途还是短途.
         *
         * @param longOrShort the new 长途还是短途
         */
        public void setLongOrShort(String longOrShort) {
            this.longOrShort = longOrShort;
        }
        
        /**
         * 获取 规则类型.
         *
         * @return the 规则类型
         */
        public String getType() {
            return type;
        }
        
        /**
         * 设置 规则类型.
         *
         * @param type the new 规则类型
         */
        public void setType(String type) {
            this.type = type;
        }
        
        /**
         * 获取 币种.
         *
         * @return the 币种
         */
        public String getCurrencyCode() {
            return currencyCode;
        }
        
        /**
         * 设置 币种.
         *
         * @param currencyCode the new 币种
         */
        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }
        
        /**
         * 获取 编码.
         *
         * @return the 编码
         */
        public String getCode() {
            return code;
        }
        
        /**
         * 设置 编码.
         *
         * @param code the new 编码
         */
        public void setCode(String code) {
            this.code = code;
        }
        
        /**
         * 获取 名称.
         *
         * @return the 名称
         */
        public String getName() {
            return name;
        }
        
        /**
         * 设置 名称.
         *
         * @param name the new 名称
         */
        public void setName(String name) {
            this.name = name;
        }
        
        /**
         * 获取 最后修改人姓名.
         *
         * @return the 最后修改人姓名
         */
        public String getModifyUserName() {
            return modifyUserName;
        }
        
        /**
         * 设置 最后修改人姓名.
         *
         * @param modifyUserName the new 最后修改人姓名
         */
        public void setModifyUserName(String modifyUserName) {
            this.modifyUserName = modifyUserName;
        }
        
        /**
         * 获取 创建人姓名.
         *
         * @return the 创建人姓名
         */
        public String getCreateUserName() {
            return createUserName;
        }
        
        /**
         * 设置 创建人姓名.
         *
         * @param createUserName the new 创建人姓名
         */
        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }
        
        /**
         * 获取 最后修改人所在部门.
         *
         * @return the 最后修改人所在部门
         */
        public String getModifyOrgCode() {
            return modifyOrgCode;
        }
        
        /**
         * 设置 最后修改人所在部门.
         *
         * @param modifyOrgCode the new 最后修改人所在部门
         */
        public void setModifyOrgCode(String modifyOrgCode) {
            this.modifyOrgCode = modifyOrgCode;
        }
        
        /**
         * 获取 创建人所在部门.
         *
         * @return the 创建人所在部门
         */
        public String getCreateOrgCode() {
            return createOrgCode;
        }
        
        /**
         * 设置 创建人所在部门.
         *
         * @param createOrgCode the new 创建人所在部门
         */
        public void setCreateOrgCode(String createOrgCode) {
            this.createOrgCode = createOrgCode;
        }
        
        /**
         * 获取 是否集中接货.
         *
         * @return the 是否集中接货
         */
        public String getCentralizePickup() {
            return centralizePickup;
        }
        
        /**
         * 设置 是否集中接货.
         *
         * @param centralizePickup the new 是否集中接货
         */
        public void setCentralizePickup(String centralizePickup) {
            this.centralizePickup = centralizePickup;
        }
        
        /**
         * 获取 航班班次.
         *
         * @return the 航班班次
         */
        public String getLightShift() {
            return lightShift;
        }
        
        /**
         * 设置 航班班次.
         *
         * @param lightShift the new 航班班次
         */
        public void setLightShift(String lightShift) {
            this.lightShift = lightShift;
        }
        
        /**
         * 获取 计价方式ID.
         *
         * @return the 计价方式ID
         */
        public String getCriteriaId() {
            return criteriaId;
        }
        
        /**
         * 设置 计价方式ID.
         *
         * @param criteriaId the new 计价方式ID
         */
        public void setCriteriaId(String criteriaId) {
            this.criteriaId = criteriaId;
        }
        
        /**
         * 获取 计费规则ID集合.
         *
         * @return the 计费规则ID集合
         */
        public List<String> getValuationIds() {
            return valuationIds;
        }
        
        /**
         * 设置 计费规则ID集合.
         *
         * @param valuationIds the new 计费规则ID集合
         */
        public void setValuationIds(List<String> valuationIds) {
            this.valuationIds = valuationIds;
        }
        
        /**
         * 获取 收货日期.
         *
         * @return the 收货日期
         */
        public Date getReceiveDate() {
            return receiveDate;
        }
        
        /**
         * 设置 收货日期.
         *
         * @param receiveDate the new 收货日期
         */
        public void setReceiveDate(Date receiveDate) {
            this.receiveDate = receiveDate;
        }
        
        /**
         * 
         *
         * @return 
         */
        public static long getSerialversionuid() {
            return serialVersionUID;
        }
        
        /**
         * 获取 产品CODE.
         *
         * @return the 产品CODE
         */
        public String getProductCode() {
	    return productCode;
	}

	/**
	 * 设置 产品CODE.
	 *
	 * @param productCode the new 产品CODE
	 */
	public void setProductCode(String productCode) {
	    this.productCode = productCode;
	}
}
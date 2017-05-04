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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/domain/PriceValuationEntity.java
 * 
 * FILE NAME        	: PriceValuationEntity.java
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
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerIndustryEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceIndustryEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceProductEntity;

/**
 * 价格方案计费规则实体类
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-8-6 下午6:24:07
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-8-6 下午6:24:07
 * @since
 * @version
 */
public class PriceValuationEntity extends BaseEntity {

	/**
     * 
     */
	private static final long serialVersionUID = -2292884027172279895L;

	/**
     * 
     */
	private static final String YES = "是";

	/**
     * 
     */
	private static final String NO = "否";

	/**
	 * 是否当前版本.
	 */
	private String currentUsedVersion;

	/**
	 * 货物类型ID.
	 */
	private String goodsTypeId;

	/**
	 * 获取 是否当前版本.
	 *
	 * @return the 是否当前版本
	 */
	public String getCurrentUsedVersion() {
		Date now = new Date();
		if (this.getBeginTime() != null && this.getEndTime() != null) {
			if (now.compareTo(this.getBeginTime()) >= 0
					&& now.compareTo(this.getEndTime()) < 0) {
				currentUsedVersion = YES;
				return currentUsedVersion;
			} else {
				currentUsedVersion = NO;
				return currentUsedVersion;
			}
		} else {
			return "";
		}

	}

	/**
	 * 设置 是否当前版本.
	 *
	 * @param currentUsedVersion
	 *            the new 是否当前版本
	 */
	public void setCurrentUsedVersion(String currentUsedVersion) {
		this.currentUsedVersion = currentUsedVersion;
	}

	/**
	 * 货物类型CODE.
	 */
	private String goodsTypeCode;

	/**
	 * 货物类型Name.
	 */
	private String goodsTypeName;

	/**
	 * 产品ID.
	 */
	private String productId;

	/**
	 * 产品CODE.
	 */
	private String productCode;
	/**
	 * 产品code集合
	 */
	private List<String> productCodes;

	/**
	 * 产品名称.
	 */
	private String productName;

	/**
	 * 始发区域ID.
	 */
	private String deptRegionId;

	/**
	 * 始发区域NAME.
	 */
	private String deptRegionName;

	/**
	 * 目的区域ID.
	 */
	private String arrvRegionId;

	/**
	 * 目的区域NAME.
	 */
	private String arrvRegionName;

	/**
	 * 渠道CODE.
	 */
	private String salesChannelCode;

	/**
	 * 渠道ID.
	 */
	private String salesChannelId;

	/**
	 * 计价条目ID.
	 */
	private String pricingEntryId;

	/**
	 * 计价条目CODE.
	 */
	private String pricingEntryCode;

	/**
	 * 所属行业ID.
	 */
	private String pricingIndustryId;

	/**
	 * 所属行业CODE.
	 */
	private String pricingIndustryCode;

	/**
	 * 计价名称.
	 */
	private String pricingEntryName;

	/**
	 * 价格方案主信息ID.
	 */
	private String pricePlanId;

	/**
	 * 价格方案主信息CODE.
	 */
	private String pricePlanCode;

	/**
	 * 市场活动ID.
	 */
	private String marketingEventId;

	/**
	 * 市场活动CODE.
	 */
	private String marketingEventCode;

	/**
	 * 描述.
	 */
	private String remarks;

	/**
	 * 版本号.
	 */
	private Long versionNo;

	/**
	 * 是否激活.
	 */
	private String active;

	/**
	 * 有效起始时间.
	 */
	private Date beginTime;

	/**
	 * 有效截止时间.
	 */
	private Date endTime;

	/**
	 * 长途还是短途.
	 */
	private String longOrShort;

	/**
	 * 规则类型.
	 */
	private String type;

	/**
	 * 币种.
	 */
	private String currencyCode;

	/**
	 * 编码.
	 */
	private String code;

	/**
	 * 名称.
	 */
	private String name;

	/**
	 * 最后修改人姓名.
	 */
	private String modifyUserName;

	/**
	 * 创建人姓名.
	 */
	private String createUserName;

	/**
	 * 最后修改人所在部门.
	 */
	private String modifyOrgCode;

	/**
	 * 创建人所在部门.
	 */
	private String createOrgCode;

	/**
	 * 是否集中接货.
	 */
	private String centralizePickup;
	
	/**
	 * 是否集中送货(2016.07.08新增 用于首续重计价方案)
	 */
	private String centralizeDelivery;

	/**
	 * 航班班次.
	 */
	private String lightShift;

	/**
	 * 计价方式ID.
	 */
	private String criteriaId;

	/**
	 * 是否自提(Y/N).
	 */
	private String selfPickUp;

	/**
	 * 计费规则ID集合.
	 */
	private List<String> valuationIds;

	/**
	 * 计价方式明细集合.
	 */
	private List<PriceCriteriaDetailEntity> criteriaDetailEntities;

	private String subType;

	/**
	 * 操作类型：仅限新增和升级版本使用
	 */
	private String operateTypeForAddAndUpdateVersion;
	/**
	 * 开始范围
	 */
	private BigDecimal leftRange;
	/**
	 * 结束范围
	 */
	private BigDecimal rightRange;

	/**
	 * 业务发生时间
	 */
	private String businessBeginTime;
	public String getBusinessBeginTime() {
		return businessBeginTime;
	}

	public void setBusinessBeginTime(String businessBeginTime) {
		this.businessBeginTime = businessBeginTime;
	}

	public String getBusinessEndTime() {
		return businessEndTime;
	}

	public void setBusinessEndTime(String businessEndTime) {
		this.businessEndTime = businessEndTime;
	}

	/**
	 * 业务结束时间
	 */
	private String businessEndTime;

	/**
	 * 计费规则 重量或者体积
	 */
	private String caculateType;

	/**
	 * 
	 * 获取 是否自提(Y/N).
	 *
	 * @return the selfPickUp
	 */

	public String getSelfPickUp() {
		return selfPickUp;
	}

	/**
	 * 设置 是否自提(Y/N).
	 *
	 * @param selfPickUp
	 *            the selfPickUp to set
	 */
	public void setSelfPickUp(String selfPickUp) {
		this.selfPickUp = selfPickUp;
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
	 * @param pricingEntryName
	 *            the new 计价名称
	 */
	public void setPricingEntryName(String pricingEntryName) {
		this.pricingEntryName = pricingEntryName;
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
	 * @param modifyUserName
	 *            the new 最后修改人姓名
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
	 * @param createUserName
	 *            the new 创建人姓名
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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
	 * @param goodsTypeId
	 *            the new 货物类型ID
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
	 * @param goodsTypeCode
	 *            the new 货物类型CODE
	 */
	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
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
	 * @param productId
	 *            the new 产品ID
	 */
	public void setProductId(String productId) {
		this.productId = productId;
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
	 * @param deptRegionId
	 *            the new 始发区域ID
	 */
	public void setDeptRegionId(String deptRegionId) {
		this.deptRegionId = deptRegionId;
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
	 * @param arrvRegionId
	 *            the new 目的区域ID
	 */
	public void setArrvRegionId(String arrvRegionId) {
		this.arrvRegionId = arrvRegionId;
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
	 * @param salesChannelCode
	 *            the new 渠道CODE
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
	 * @param salesChannelId
	 *            the new 渠道ID
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
	 * @param pricingEntryId
	 *            the new 计价条目ID
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
	 * @param pricingEntryCode
	 *            the new 计价条目CODE
	 */
	public void setPricingEntryCode(String pricingEntryCode) {
		this.pricingEntryCode = pricingEntryCode;
	}

	/**
	 * 获取 所属行业ID.
	 *
	 * @return the 所属行业ID
	 */
	public String getPricingIndustryId() {
		return pricingIndustryId;
	}

	/**
	 * 设置 所属行业ID.
	 *
	 * @param pricingIndustryId
	 *            the new 所属行业ID
	 */
	public void setPricingIndustryId(String pricingIndustryId) {
		this.pricingIndustryId = pricingIndustryId;
	}

	/**
	 * 获取 所属行业CODE.
	 *
	 * @return the 所属行业CODE
	 */
	public String getPricingIndustryCode() {
		return pricingIndustryCode;
	}

	/**
	 * 设置 所属行业CODE.
	 *
	 * @param pricingIndustryCode
	 *            the new 所属行业CODE
	 */
	public void setPricingIndustryCode(String pricingIndustryCode) {
		this.pricingIndustryCode = pricingIndustryCode;
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
	 * @param pricePlanId
	 *            the new 价格方案主信息ID
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
	 * @param pricePlanCode
	 *            the new 价格方案主信息CODE
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
	 * @param marketingEventId
	 *            the new 市场活动ID
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
	 * @param marketingEventCode
	 *            the new 市场活动CODE
	 */
	public void setMarketingEventCode(String marketingEventCode) {
		this.marketingEventCode = marketingEventCode;
	}

	/**
	 * 获取 描述.
	 *
	 * @return the 描述
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * 设置 描述.
	 *
	 * @param reamark
	 *            the new 描述
	 */
	public void setRemarks(String reamark) {
		this.remarks = reamark;
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
	 * @param versionNo
	 *            the new 版本号
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
	 * @param active
	 *            the new 是否激活
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
	 * @param beginTime
	 *            the new 有效起始时间
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
	 * @param endTime
	 *            the new 有效截止时间
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
	 * @param longOrShort
	 *            the new 长途还是短途
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
	 * @param type
	 *            the new 规则类型
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
	 * @param currencyCode
	 *            the new 币种
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
	 * @param code
	 *            the new 编码
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
	 * @param name
	 *            the new 名称
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @param modifyOrgCode
	 *            the new 最后修改人所在部门
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
	 * @param createOrgCode
	 *            the new 创建人所在部门
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
	 * @param centralizePickup
	 *            the new 是否集中接货
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
	 * @param lightShift
	 *            the new 航班班次
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
	 * @param criteriaId
	 *            the new 计价方式ID
	 */
	public void setCriteriaId(String criteriaId) {
		this.criteriaId = criteriaId;
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
	 * @param productName
	 *            the new 产品名称
	 */
	public void setProductName(String productName) {
		this.productName = productName;
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
	 * @param valuationIds
	 *            the new 计费规则ID集合
	 */
	public void setValuationIds(List<String> valuationIds) {
		this.valuationIds = valuationIds;
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
	 * @param deptRegionName
	 *            the new 始发区域NAME
	 */
	public void setDeptRegionName(String deptRegionName) {
		this.deptRegionName = deptRegionName;
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
	 * @param arrvRegionName
	 *            the new 目的区域NAME
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
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
	 * @param goodsTypeName
	 *            the new 货物类型Name
	 */
	public void setGoodsTypeName(String goodsTypeName) {
		this.goodsTypeName = goodsTypeName;
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
	 * @param productCode
	 *            the new 产品CODE
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * 获取 计价方式明细集合.
	 *
	 * @return the 计价方式明细集合
	 */
	public List<PriceCriteriaDetailEntity> getCriteriaDetailEntities() {
		return criteriaDetailEntities;
	}

	/**
	 * 设置 计价方式明细集合.
	 *
	 * @param criteriaDetailEntities
	 *            the new 计价方式明细集合
	 */
	public void setCriteriaDetailEntities(
			List<PriceCriteriaDetailEntity> criteriaDetailEntities) {
		this.criteriaDetailEntities = criteriaDetailEntities;
	}

	/**
	 * 
	 * 筛选计费规则
	 * 
	 * @author DP-Foss-YueHongJie
	 * @date 2012-11-7 下午1:21:38
	 * 
	 *       public int getPriority(){
	 *       if(!PricingConstants.ALL.equals(this.getDeptRegionId()) &&
	 *       !PricingConstants.ALL.equals(this.getArrvRegionId())){ return 4;
	 *       }else if(!PricingConstants.ALL.equals(this.getDeptRegionId()) &&
	 *       PricingConstants.ALL.equals(this.getArrvRegionId())){ return 3;
	 *       }else if(PricingConstants.ALL.equals(this.getDeptRegionId()) &&
	 *       !PricingConstants.ALL.equals(this.getArrvRegionId())){ return 2;
	 *       }else{ return 1; } }
	 */

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getOperateTypeForAddAndUpdateVersion() {
		return operateTypeForAddAndUpdateVersion;
	}

	public void setOperateTypeForAddAndUpdateVersion(
			String operateTypeForAddAndUpdateVersion) {
		this.operateTypeForAddAndUpdateVersion = operateTypeForAddAndUpdateVersion;
	}

	public List<String> getProductCodes() {
		return productCodes;
	}

	public void setProductCodes(List<String> productCodes) {
		this.productCodes = productCodes;
	}

	public BigDecimal getRightRange() {
		return rightRange;
	}

	public void setRightRange(BigDecimal rightRange) {
		this.rightRange = rightRange;
	}

	public String getCaculateType() {
		return caculateType;
	}

	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}

	public BigDecimal getLeftRange() {
		return leftRange;
	}

	public void setLeftRange(BigDecimal leftRange) {
		this.leftRange = leftRange;
	}
	public List<PriceProductEntity> getProductList() {
		return productList;
	}

	public void setProductList(List<PriceProductEntity> productList) {
		this.productList = productList;
	}

	public List<PriceIndustryEntity> getIndustryList() {
		return industryList;
	}

	public void setIndustryList(List<PriceIndustryEntity> industryList) {
		this.industryList = industryList;
	}

	public List<CustomerIndustryEntity> getCustomerIndustryEntityList() {
		return customerIndustryEntityList;
	}

	public void setCustomerIndustryEntityList(
			List<CustomerIndustryEntity> customerIndustryEntityList) {
		this.customerIndustryEntityList = customerIndustryEntityList;
	}

	/**
	 * 产品列表
	 */
	private List<PriceProductEntity> productList;
	/**
	 * 行业列表
	 */
	private List<PriceIndustryEntity> industryList;
	/**
	 * 行业列表
	 */
	private List<CustomerIndustryEntity> customerIndustryEntityList;
	/**价格方案名称
     * 
     */
     
    private String pricePlanName;
		public String getPricePlanName() {
		return pricePlanName;
	}


	public void setPricePlanName(String pricePlanName) {
		this.pricePlanName = pricePlanName;
	}
	
	/**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 获取客户名称
     * @return
     */
    public String getCustomerName() {
		return customerName;
	}

    /**
     * 设置客户名称
     * @param customerName
     */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * 获取客户编码
	 * @return
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * 设置客户名称
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCentralizeDelivery() {
		return centralizeDelivery;
	}

	public void setCentralizeDelivery(String centralizeDelivery) {
		this.centralizeDelivery = centralizeDelivery;
	}
}
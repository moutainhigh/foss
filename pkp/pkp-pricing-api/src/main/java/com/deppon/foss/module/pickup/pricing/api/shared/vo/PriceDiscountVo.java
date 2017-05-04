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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/vo/PriceDiscountVo.java
 * 
 * FILE NAME        	: PriceDiscountVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountOrgEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventChannelEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;

/**
 * 折扣方案VO
 * (描述类的职责)
 * @author sz
 * @date 2012-12-6 下午3:56:48
 * @since
 * @version
 */
public class PriceDiscountVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4480452754031715805L;
	
	/**
	 * 市场活动实体类
	 */
	private MarketingEventEntity marketingEventEntity;
	
	/**
	 * 市场活动实体类LIST
	 */
	private List<MarketingEventEntity> marketingEventEntityList;
	
	/**
	 * 渠道实体类
	 */
	private MarketingEventChannelEntity channelEntity;
	
	/**
	 * 渠道实体类
	 */
	private List<MarketingEventChannelEntity> channelEntityList;
	
	/**
	 * 折扣起始目的组织网点实体类
	 */
	private DiscountOrgEntity discountOrgEntity;
	
	/**
	 * 折扣起始目的组织网点实体类LIST(起始)
	 */
	private List<DiscountOrgEntity> startDiscountOrgEntityList;
	
	/**
	 * 折扣起始目的组织网点实体类LIST（结束）
	 */
	private List<DiscountOrgEntity> endDiscountOrgEntityList;
	
	/**
	 * 计费规则实体类
	 */
	private PriceValuationEntity valuationEntity;
	
	/**
	 * 计价方式明细
	 */
	private PriceCriteriaDetailEntity criteriaDetailEntity;
	
    /**
     * 查询条件
     */
	private PriceDiscountDto priceDiscountDto;
	/**
	 * 查询结果集
	 */
	private List<PriceDiscountDto> priceDiscountDtoList;
	/**
	 * 出发区域结果集
	 */
	private List<DiscountOrgEntity> startRegionList;

	/**
	 * 到达区域结果集
	 */
	private List<DiscountOrgEntity> arrvRegionList;
	/**
	 * 删除和激活
	 */
    private List<String> priceDiscountIds;
    /**
     * 增值服务LIST
     */
    private List<PriceEntity> priceEntityList;
    
    /**
     * 查询城市条件
     */
    private AdministrativeRegionsEntity administrativeRegionsEntity;
    /**
     * 返回结果城市
     */
    private List<AdministrativeRegionsEntity> administrativeRegionsEntityList;
    /**
     * 出发全网
     */
    private String startAllNet;
    /**
     * 到达全网
     */
    private String arrvAllNet;
	/**
	 * @return the startAllNet
	 */
	public String getStartAllNet() {
		return startAllNet;
	}

	/**
	 * @param startAllNet the startAllNet to set
	 */
	public void setStartAllNet(String startAllNet) {
		this.startAllNet = startAllNet;
	}

	/**
	 * @return the arrvAllNet
	 */
	public String getArrvAllNet() {
		return arrvAllNet;
	}

	/**
	 * @param arrvAllNet the arrvAllNet to set
	 */
	public void setArrvAllNet(String arrvAllNet) {
		this.arrvAllNet = arrvAllNet;
	}

	/**
	 * 获取 查询城市条件.
	 *
	 * @return the 查询城市条件
	 */
	public AdministrativeRegionsEntity getAdministrativeRegionsEntity() {
		return administrativeRegionsEntity;
	}

	/**
	 * 设置 查询城市条件.
	 *
	 * @param administrativeRegionsEntity the new 查询城市条件
	 */
	public void setAdministrativeRegionsEntity(
			AdministrativeRegionsEntity administrativeRegionsEntity) {
		this.administrativeRegionsEntity = administrativeRegionsEntity;
	}

	/**
	 * 获取 返回结果城市.
	 *
	 * @return the 返回结果城市
	 */
	public List<AdministrativeRegionsEntity> getAdministrativeRegionsEntityList() {
		return administrativeRegionsEntityList;
	}

	/**
	 * 设置 返回结果城市.
	 *
	 * @param administrativeRegionsEntityList the new 返回结果城市
	 */
	public void setAdministrativeRegionsEntityList(
			List<AdministrativeRegionsEntity> administrativeRegionsEntityList) {
		this.administrativeRegionsEntityList = administrativeRegionsEntityList;
	}

	/**
	 * 获取 增值服务LIST.
	 *
	 * @return the 增值服务LIST
	 */
	public List<PriceEntity> getPriceEntityList() {
		return priceEntityList;
	}

	/**
	 * 设置 增值服务LIST.
	 *
	 * @param priceEntityList the new 增值服务LIST
	 */
	public void setPriceEntityList(List<PriceEntity> priceEntityList) {
		this.priceEntityList = priceEntityList;
	}

	/**
	 * 获取 市场活动实体类LIST.
	 *
	 * @return the 市场活动实体类LIST
	 */
	public List<MarketingEventEntity> getMarketingEventEntityList() {
		return marketingEventEntityList;
	}

	/**
	 * 设置 市场活动实体类LIST.
	 *
	 * @param marketingEventEntityList the new 市场活动实体类LIST
	 */
	public void setMarketingEventEntityList(
			List<MarketingEventEntity> marketingEventEntityList) {
		this.marketingEventEntityList = marketingEventEntityList;
	}

	/**
	 * 获取 删除和激活.
	 *
	 * @return the 删除和激活
	 */
	public List<String> getPriceDiscountIds() {
		return priceDiscountIds;
	}

	/**
	 * 设置 删除和激活.
	 *
	 * @param priceDiscountIds the new 删除和激活
	 */
	public void setPriceDiscountIds(List<String> priceDiscountIds) {
		this.priceDiscountIds = priceDiscountIds;
	}

	/**
	 * 获取 市场活动实体类.
	 *
	 * @return the 市场活动实体类
	 */
	public MarketingEventEntity getMarketingEventEntity() {
		return marketingEventEntity;
	}

	/**
	 * 设置 市场活动实体类.
	 *
	 * @param marketingEventEntity the new 市场活动实体类
	 */
	public void setMarketingEventEntity(MarketingEventEntity marketingEventEntity) {
		this.marketingEventEntity = marketingEventEntity;
	}

	/**
	 * 获取 渠道实体类.
	 *
	 * @return the 渠道实体类
	 */
	public MarketingEventChannelEntity getChannelEntity() {
		return channelEntity;
	}

	/**
	 * 设置 渠道实体类.
	 *
	 * @param channelEntity the new 渠道实体类
	 */
	public void setChannelEntity(MarketingEventChannelEntity channelEntity) {
		this.channelEntity = channelEntity;
	}

	/**
	 * 获取 折扣起始目的组织网点实体类.
	 *
	 * @return the 折扣起始目的组织网点实体类
	 */
	public DiscountOrgEntity getDiscountOrgEntity() {
		return discountOrgEntity;
	}

	/**
	 * 设置 折扣起始目的组织网点实体类.
	 *
	 * @param discountOrgEntity the new 折扣起始目的组织网点实体类
	 */
	public void setDiscountOrgEntity(DiscountOrgEntity discountOrgEntity) {
		this.discountOrgEntity = discountOrgEntity;
	}

	/**
	 * 获取 计费规则实体类.
	 *
	 * @return the 计费规则实体类
	 */
	public PriceValuationEntity getValuationEntity() {
		return valuationEntity;
	}

	/**
	 * 设置 计费规则实体类.
	 *
	 * @param valuationEntity the new 计费规则实体类
	 */
	public void setValuationEntity(PriceValuationEntity valuationEntity) {
		this.valuationEntity = valuationEntity;
	}

	/**
	 * 获取 计价方式明细.
	 *
	 * @return the 计价方式明细
	 */
	public PriceCriteriaDetailEntity getCriteriaDetailEntity() {
		return criteriaDetailEntity;
	}

	/**
	 * 设置 计价方式明细.
	 *
	 * @param criteriaDetailEntity the new 计价方式明细
	 */
	public void setCriteriaDetailEntity(
			PriceCriteriaDetailEntity criteriaDetailEntity) {
		this.criteriaDetailEntity = criteriaDetailEntity;
	}

	/**
	 * 获取 查询条件.
	 *
	 * @return the 查询条件
	 */
	public PriceDiscountDto getPriceDiscountDto() {
		return priceDiscountDto;
	}

	/**
	 * 设置 查询条件.
	 *
	 * @param priceDiscountDto the new 查询条件
	 */
	public void setPriceDiscountDto(PriceDiscountDto priceDiscountDto) {
		this.priceDiscountDto = priceDiscountDto;
	}

	/**
	 * 获取 出发区域结果集.
	 *
	 * @return the 出发区域结果集
	 */
	public List<DiscountOrgEntity> getStartRegionList() {
		return startRegionList;
	}

	/**
	 * 设置 出发区域结果集.
	 *
	 * @param startRegionList the new 出发区域结果集
	 */
	public void setStartRegionList(List<DiscountOrgEntity> startRegionList) {
		this.startRegionList = startRegionList;
	}

	/**
	 * 获取 到达区域结果集.
	 *
	 * @return the 到达区域结果集
	 */
	public List<DiscountOrgEntity> getArrvRegionList() {
		return arrvRegionList;
	}

	/**
	 * 设置 到达区域结果集.
	 *
	 * @param arrvRegionList the new 到达区域结果集
	 */
	public void setArrvRegionList(List<DiscountOrgEntity> arrvRegionList) {
		this.arrvRegionList = arrvRegionList;
	}

	/**
	 * 获取 查询结果集.
	 *
	 * @return the 查询结果集
	 */
	public List<PriceDiscountDto> getPriceDiscountDtoList() {
		return priceDiscountDtoList;
	}

	/**
	 * 设置 查询结果集.
	 *
	 * @param priceDiscountDtoList the new 查询结果集
	 */
	public void setPriceDiscountDtoList(List<PriceDiscountDto> priceDiscountDtoList) {
		this.priceDiscountDtoList = priceDiscountDtoList;
	}

	/**
	 * 获取 渠道实体类.
	 *
	 * @return the 渠道实体类
	 */
	public List<MarketingEventChannelEntity> getChannelEntityList() {
		return channelEntityList;
	}

	/**
	 * 设置 渠道实体类.
	 *
	 * @param channelEntityList the new 渠道实体类
	 */
	public void setChannelEntityList(
			List<MarketingEventChannelEntity> channelEntityList) {
		this.channelEntityList = channelEntityList;
	}

	/**
	 * 获取 折扣起始目的组织网点实体类LIST(起始).
	 *
	 * @return the 折扣起始目的组织网点实体类LIST(起始)
	 */
	public List<DiscountOrgEntity> getStartDiscountOrgEntityList() {
		return startDiscountOrgEntityList;
	}

	/**
	 * 设置 折扣起始目的组织网点实体类LIST(起始).
	 *
	 * @param startDiscountOrgEntityList the new 折扣起始目的组织网点实体类LIST(起始)
	 */
	public void setStartDiscountOrgEntityList(
			List<DiscountOrgEntity> startDiscountOrgEntityList) {
		this.startDiscountOrgEntityList = startDiscountOrgEntityList;
	}

	/**
	 * 获取 折扣起始目的组织网点实体类LIST（结束）.
	 *
	 * @return the 折扣起始目的组织网点实体类LIST（结束）
	 */
	public List<DiscountOrgEntity> getEndDiscountOrgEntityList() {
		return endDiscountOrgEntityList;
	}

	/**
	 * 设置 折扣起始目的组织网点实体类LIST（结束）.
	 *
	 * @param endDiscountOrgEntityList the new 折扣起始目的组织网点实体类LIST（结束）
	 */
	public void setEndDiscountOrgEntityList(
			List<DiscountOrgEntity> endDiscountOrgEntityList) {
		this.endDiscountOrgEntityList = endDiscountOrgEntityList;
	}


	
}
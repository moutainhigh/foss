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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/PdaQueryBillCalculateDto.java
 * 
 * FILE NAME        	: PdaQueryBillCalculateDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MarkActivitiesQueryConditionDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * @Description: 提供PAD客户端开单服务-查询产品价格计算的DTO
 * PdaQueryBillCalculateDto.java Create on 2013-1-14 上午10:11:47
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PdaQueryBillCalculateDto implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7053344817924117244L;
    /**
     * 出发部门
     */
    private String originalOrgCode;
    /**
     *  到达部门
     */
    private String destinationOrgCode;
    /**
     *  产品编号
     */
    private String productCode;
    /** 
     * 货物类型（目前只有空运价格才和货物类型有关，汽运价格与货物类型没有关系的）
     */
    private String goodsCode;
    /** 
     * 营业部收货日期（可选，无则表示当前日期）,即开单日期
     */
    private Date receiveDate;
    /** 
     * 是否接货
     */
    private String isReceiveGoods;
    
    
    
    /**
     * 是否自提
     */
    private String isSelfPickUp;
    
    /** 
     * 重量 
     */
    private BigDecimal weight;
    /** 
     * 体积
     */
    private BigDecimal volume;
    /**
     * 航班班次
     */
    private String flightShift;
    /**
     * 币种
     */
    private String currencyCdoe;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 所属行业编码
     */
    private String industrulCode;
    /**
     * 渠道code
     */
    private String channelCode;
    /**
     * 计价条目列表
     */
	private List<PdaQueryBillCalculateSubDto> priceEntities;
    //***********************************************
    /**
     * 出发区域ID
     */
    private String deptRegionId;
    /**
     * 到达区域ID
     */
    private String arrvRegionId;
    /**
     * 长短途
     */
    private String longOrShort;
    /**
     * 计价条目
     */
    private String pricingEntryCode;
    /**
     * 子类型
     */
    private String subType;
    /**
     * 原始费用
     */
    private BigDecimal originnalCost; 
    /**
     * 打木架体积
     */
    private BigDecimal woodenVolume; 
    
    /**
     * 经济自提件
     */
    @SuppressWarnings("unused")
	private String economySince;
    

    //营销活动DTO
    private MarkActivitiesQueryConditionDto activeDto;
    
    //是否要计算营销活动折扣（默认为否）
    private boolean isCalActiveDiscount = false;
    
    //线路出发外场
  	private String startOutFieldCode;
  	
  	//线路到达外场
  	private String arriveOutFieldCode;
  	
  	//开单金额
  	private BigDecimal billlingAmount;
    
	public String getStartOutFieldCode() {
		return startOutFieldCode;
	}

	public void setStartOutFieldCode(String startOutFieldCode) {
		this.startOutFieldCode = startOutFieldCode;
	}

	public String getArriveOutFieldCode() {
		return arriveOutFieldCode;
	}

	public void setArriveOutFieldCode(String arriveOutFieldCode) {
		this.arriveOutFieldCode = arriveOutFieldCode;
	}

	public BigDecimal getBilllingAmount() {
		return billlingAmount;
	}

	public void setBilllingAmount(BigDecimal billlingAmount) {
		this.billlingAmount = billlingAmount;
	}

	public boolean isCalActiveDiscount() {
		return isCalActiveDiscount;
	}

	public void setCalActiveDiscount(boolean isCalActiveDiscount) {
		this.isCalActiveDiscount = isCalActiveDiscount;
	}
    
	public MarkActivitiesQueryConditionDto getActiveDto() {
		return activeDto;
	}

	public void setActiveDto(MarkActivitiesQueryConditionDto activeDto) {
		this.activeDto = activeDto;
	}
    
    /**
     * 大礼包方案CODE
     */
    private String cityMarketCode;
    /**
     * 经济自提件
     */
	public String getEconomySince() {
//		return economySince;
		//自提件取消上线，设置为不显示
		return FossConstants.NO;
	}

	   
    public String getCityMarketCode() {
		return cityMarketCode;
	}


	public void setCityMarketCode(String cityMarketCode) {
		this.cityMarketCode = cityMarketCode;
	}


	/**
     * 经济自提件
     */
	public void setEconomySince(String economySince) {
//		this.economySince = economySince;
		//自提件取消上线，设置为不显示
		this.economySince = FossConstants.NO;
	}

	/**
	 * 获取 出发部门.
	 *
	 * @return the 出发部门
	 */
	public String getOriginalOrgCode() {
		return originalOrgCode;
	}
	
	/**
	 * 设置 出发部门.
	 *
	 * @param originalOrgCode the new 出发部门
	 */
	public void setOriginalOrgCode(String originalOrgCode) {
		this.originalOrgCode = originalOrgCode;
	}
	
	/**
	 * 获取 到达部门.
	 *
	 * @return the 到达部门
	 */
	public String getDestinationOrgCode() {
		return destinationOrgCode;
	}
	
	/**
	 * 设置 到达部门.
	 *
	 * @param destinationOrgCode the new 到达部门
	 */
	public void setDestinationOrgCode(String destinationOrgCode) {
		this.destinationOrgCode = destinationOrgCode;
	}
	
	/**
	 * 获取 产品编号.
	 *
	 * @return the 产品编号
	 */
	public String getProductCode() {
		return productCode;
	}
	
	/**
	 * 设置 产品编号.
	 *
	 * @param productCode the new 产品编号
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	/**
	 * 获取 货物类型（目前只有空运价格才和货物类型有关，汽运价格与货物类型没有关系的）.
	 *
	 * @return the 货物类型（目前只有空运价格才和货物类型有关，汽运价格与货物类型没有关系的）
	 */
	public String getGoodsCode() {
		return goodsCode;
	}
	
	/**
	 * 设置 货物类型（目前只有空运价格才和货物类型有关，汽运价格与货物类型没有关系的）.
	 *
	 * @param goodsCode the new 货物类型（目前只有空运价格才和货物类型有关，汽运价格与货物类型没有关系的）
	 */
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	
	/**
	 * 获取 营业部收货日期（可选，无则表示当前日期）,即开单日期.
	 *
	 * @return the 营业部收货日期（可选，无则表示当前日期）,即开单日期
	 */
	public Date getReceiveDate() {
		return receiveDate;
	}
	
	/**
	 * 设置 营业部收货日期（可选，无则表示当前日期）,即开单日期.
	 *
	 * @param receiveDate the new 营业部收货日期（可选，无则表示当前日期）,即开单日期
	 */
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
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
	 * 获取 重量.
	 *
	 * @return the 重量
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	
	/**
	 * 设置 重量.
	 *
	 * @param weight the new 重量
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	/**
	 * 获取 体积.
	 *
	 * @return the 体积
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	
	/**
	 * 设置 体积.
	 *
	 * @param volume the new 体积
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	
	/**
	 * 获取 航班班次.
	 *
	 * @return the 航班班次
	 */
	public String getFlightShift() {
		return flightShift;
	}
	
	/**
	 * 设置 航班班次.
	 *
	 * @param flightShift the new 航班班次
	 */
	public void setFlightShift(String flightShift) {
		this.flightShift = flightShift;
	}
	
	/**
	 * 获取 币种.
	 *
	 * @return the 币种
	 */
	public String getCurrencyCdoe() {
		return currencyCdoe;
	}
	
	/**
	 * 设置 币种.
	 *
	 * @param currencyCdoe the new 币种
	 */
	public void setCurrencyCdoe(String currencyCdoe) {
		this.currencyCdoe = currencyCdoe;
	}
	
	/**
	 * 获取 客户编码.
	 *
	 * @return the 客户编码
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	
	/**
	 * 设置 客户编码.
	 *
	 * @param customerCode the new 客户编码
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	
	/**
	 * 获取 所属行业编码.
	 *
	 * @return the 所属行业编码
	 */
	public String getIndustrulCode() {
		if(StringUtil.isEmpty(industrulCode)) {
			 return PricingConstants.ALL;
		} else {
			return industrulCode;
		}
	}
	
	/**
	 * 设置 所属行业编码.
	 *
	 * @param industrulCode the new 所属行业编码
	 */
	public void setIndustrulCode(String industrulCode) {
		this.industrulCode = industrulCode;
	}
	
	/**
	 * 获取 渠道code.
	 *
	 * @return the 渠道code
	 */
	public String getChannelCode() {
		return channelCode;
	}
	
	/**
	 * 设置 渠道code.
	 *
	 * @param channelCode the new 渠道code
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * 获取 计价条目列表.
	 *
	 * @return the 计价条目列表
	 */
	public List<PdaQueryBillCalculateSubDto> getPriceEntities() {
		return priceEntities;
	}

	/**
	 * 设置 计价条目列表.
	 *
	 * @param priceEntities the new 计价条目列表
	 */
	public void setPriceEntities(List<PdaQueryBillCalculateSubDto> priceEntities) {
		this.priceEntities = priceEntities;
	}

	/**
	 * 获取 出发区域ID.
	 *
	 * @return the 出发区域ID
	 */
	public String getDeptRegionId() {
		return deptRegionId;
	}

	/**
	 * 设置 出发区域ID.
	 *
	 * @param deptRegionId the new 出发区域ID
	 */
	public void setDeptRegionId(String deptRegionId) {
		this.deptRegionId = deptRegionId;
	}

	/**
	 * 获取 到达区域ID.
	 *
	 * @return the 到达区域ID
	 */
	public String getArrvRegionId() {
		return arrvRegionId;
	}

	/**
	 * 设置 到达区域ID.
	 *
	 * @param arrvRegionId the new 到达区域ID
	 */
	public void setArrvRegionId(String arrvRegionId) {
		this.arrvRegionId = arrvRegionId;
	}

	/**
	 * 获取 长短途.
	 *
	 * @return the 长短途
	 */
	public String getLongOrShort() {
		return longOrShort;
	}

	/**
	 * 设置 长短途.
	 *
	 * @param longOrShort the new 长短途
	 */
	public void setLongOrShort(String longOrShort) {
		this.longOrShort = longOrShort;
	}

	/**
	 * 获取 计价条目.
	 *
	 * @return the 计价条目
	 */
	public String getPricingEntryCode() {
		return pricingEntryCode;
	}

	/**
	 * 设置 计价条目.
	 *
	 * @param pricingEntryCode the new 计价条目
	 */
	public void setPricingEntryCode(String pricingEntryCode) {
		this.pricingEntryCode = pricingEntryCode;
	}

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
	 * 获取 原始费用.
	 *
	 * @return the 原始费用
	 */
	public BigDecimal getOriginnalCost() {
		return originnalCost;
	}

	/**
	 * 设置 原始费用.
	 *
	 * @param originnalCost the new 原始费用
	 */
	public void setOriginnalCost(BigDecimal originnalCost) {
		this.originnalCost = originnalCost;
	}

	/**
	 * 获取 打木架体积.
	 *
	 * @return the 打木架体积
	 */
	public BigDecimal getWoodenVolume() {
		return woodenVolume;
	}

	/**
	 * 设置 打木架体积.
	 *
	 * @param woodenVolume the new 打木架体积
	 */
	public void setWoodenVolume(BigDecimal woodenVolume) {
		this.woodenVolume = woodenVolume;
	}

	
	public String getIsSelfPickUp() {
	    return isSelfPickUp;
	}

	
	public void setIsSelfPickUp(String isSelfPickUp) {
	    this.isSelfPickUp = isSelfPickUp;
	}
	
	
	
}
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/DiscountParmDto.java
 * 
 * FILE NAME        	: DiscountParmDto.java
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


/**
 * 提供GUI客户端开单服务-查询产品价格增值服务计算的DTO
 * @author IBMDP-sz
 * @date 2012-12-18 下午6:33:52
 */

public class DiscountParmDto implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 7369429955549354376L;
    /**
     * 计费明细ID
     */
    private String criteriaDetailId;
    /** 折扣渠道 **/
    private String saleChannelCode;
    /** 出发部门CODE **/
    private String originalOrgCode;
    /** 到达部门CODE **/
    private String destinationOrgCode;
    /** 始发营业部所属城市Code **/
    private String deptCityCode;
    /** 到达营业部所属城市Code **/
    private String arrvCityCode;
    /** 始发价格区域 ID **/
    private String deptRegionId;
    /** 到达价格区域 ID **/
    private String arrvRegionId;
    /** 客户编码 **/
    private String customCode;
    /**  营业部收货日期（可选，无则表示当前日期） **/
    private Date receiveDate;
    /** 计价条目CODE  增值服务费用代码  请参考 PricingConstants 常量定义 **/
    private String pricingEntryCode;
    /**计价名称**/
    private String pricingEntryName;
    /**子类型**/
    private String subType;
    /** 产品CODE **/
    private String productCode;
    /** 货物类型code */
    private String goodsTypeCode;
    /** 所属行业*/
    private String industryCode;
    /** 重量 **/
    private BigDecimal weight;
    /** 体积 **/
    private BigDecimal volume;
    /** 原始费用 **/
    private BigDecimal originnalCost; 
    /** 币种 **/
    private String currencyCdoe;
    
    /**
     * 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；
     */
    private String caculateType;
    
    /**
     * 最低费用
     */
    private BigDecimal minFee;
    
    
    /**
     * 最高费用
     */
    private BigDecimal maxFee;
    
    
	/**
	 * 获取 折扣渠道 *.
	 *
	 * @return the 折扣渠道 *
	 */
	public String getSaleChannelCode() {
		return saleChannelCode;
	}
	
	/**
	 * 设置 折扣渠道 *.
	 *
	 * @param saleChannelCode the new 折扣渠道 *
	 */
	public void setSaleChannelCode(String saleChannelCode) {
		this.saleChannelCode = saleChannelCode;
	}
	
	/**
	 * 获取 出发部门CODE *.
	 *
	 * @return the 出发部门CODE *
	 */
	public String getOriginalOrgCode() {
		return originalOrgCode;
	}
	
	/**
	 * 设置 出发部门CODE *.
	 *
	 * @param originalOrgCode the new 出发部门CODE *
	 */
	public void setOriginalOrgCode(String originalOrgCode) {
		this.originalOrgCode = originalOrgCode;
	}
	
	/**
	 * 获取 到达部门CODE *.
	 *
	 * @return the 到达部门CODE *
	 */
	public String getDestinationOrgCode() {
		return destinationOrgCode;
	}
	
	/**
	 * 设置 到达部门CODE *.
	 *
	 * @param destinationOrgCode the new 到达部门CODE *
	 */
	public void setDestinationOrgCode(String destinationOrgCode) {
		this.destinationOrgCode = destinationOrgCode;
	}
	
	/**
	 * 获取 始发营业部所属城市Code *.
	 *
	 * @return the 始发营业部所属城市Code *
	 */
	public String getDeptCityCode() {
		return deptCityCode;
	}
	
	/**
	 * 设置 始发营业部所属城市Code *.
	 *
	 * @param deptCityCode the new 始发营业部所属城市Code *
	 */
	public void setDeptCityCode(String deptCityCode) {
		this.deptCityCode = deptCityCode;
	}
	
	/**
	 * 获取 到达营业部所属城市Code *.
	 *
	 * @return the 到达营业部所属城市Code *
	 */
	public String getArrvCityCode() {
		return arrvCityCode;
	}
	
	/**
	 * 设置 到达营业部所属城市Code *.
	 *
	 * @param arrvCityCode the new 到达营业部所属城市Code *
	 */
	public void setArrvCityCode(String arrvCityCode) {
		this.arrvCityCode = arrvCityCode;
	}
	
	/**
	 * 获取 始发价格区域 ID *.
	 *
	 * @return the 始发价格区域 ID *
	 */
	public String getDeptRegionId() {
		return deptRegionId;
	}
	
	/**
	 * 设置 始发价格区域 ID *.
	 *
	 * @param deptRegionId the new 始发价格区域 ID *
	 */
	public void setDeptRegionId(String deptRegionId) {
		this.deptRegionId = deptRegionId;
	}
	
	/**
	 * 获取 到达价格区域 ID *.
	 *
	 * @return the 到达价格区域 ID *
	 */
	public String getArrvRegionId() {
		return arrvRegionId;
	}
	
	/**
	 * 设置 到达价格区域 ID *.
	 *
	 * @param arrvRegionId the new 到达价格区域 ID *
	 */
	public void setArrvRegionId(String arrvRegionId) {
		this.arrvRegionId = arrvRegionId;
	}
	
	/**
	 * 获取 计价条目CODE  增值服务费用代码  请参考 PricingConstants 常量定义 *.
	 *
	 * @return the 计价条目CODE  增值服务费用代码  请参考 PricingConstants 常量定义 *
	 */
	public String getPricingEntryCode() {
		return pricingEntryCode;
	}
	
	/**
	 * 设置 计价条目CODE  增值服务费用代码  请参考 PricingConstants 常量定义 *.
	 *
	 * @param pricingEntryCode the new 计价条目CODE  增值服务费用代码  请参考 PricingConstants 常量定义 *
	 */
	public void setPricingEntryCode(String pricingEntryCode) {
		this.pricingEntryCode = pricingEntryCode;
	}
	
	/**
	 * 获取 计价名称*.
	 *
	 * @return the 计价名称*
	 */
	public String getPricingEntryName() {
		return pricingEntryName;
	}
	
	/**
	 * 设置 计价名称*.
	 *
	 * @param pricingEntryName the new 计价名称*
	 */
	public void setPricingEntryName(String pricingEntryName) {
		this.pricingEntryName = pricingEntryName;
	}
	
	/**
	 * 获取 子类型*.
	 *
	 * @return the 子类型*
	 */
	public String getSubType() {
		return subType;
	}
	
	/**
	 * 设置 子类型*.
	 *
	 * @param subType the new 子类型*
	 */
	public void setSubType(String subType) {
		this.subType = subType;
	}
	
	/**
	 * 获取 产品CODE *.
	 *
	 * @return the 产品CODE *
	 */
	public String getProductCode() {
		return productCode;
	}
	
	/**
	 * 设置 产品CODE *.
	 *
	 * @param productCode the new 产品CODE *
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	/**
	 * 获取 货物类型code.
	 *
	 * @return the 货物类型code
	 */
	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}
	
	/**
	 * 设置 货物类型code.
	 *
	 * @param goodsTypeCode the new 货物类型code
	 */
	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}
	
	/**
	 * 获取 所属行业.
	 *
	 * @return the 所属行业
	 */
	public String getIndustryCode() {
		return industryCode;
	}
	
	/**
	 * 设置 所属行业.
	 *
	 * @param industryCode the new 所属行业
	 */
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	
	/**
	 * 获取 重量 *.
	 *
	 * @return the 重量 *
	 */
	public BigDecimal getWeight() {
		return weight;
	}
	
	/**
	 * 设置 重量 *.
	 *
	 * @param weight the new 重量 *
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	
	/**
	 * 获取 体积 *.
	 *
	 * @return the 体积 *
	 */
	public BigDecimal getVolume() {
		return volume;
	}
	
	/**
	 * 设置 体积 *.
	 *
	 * @param volume the new 体积 *
	 */
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	
	/**
	 * 获取 原始费用 *.
	 *
	 * @return the 原始费用 *
	 */
	public BigDecimal getOriginnalCost() {
		return originnalCost;
	}
	
	/**
	 * 设置 原始费用 *.
	 *
	 * @param originnalCost the new 原始费用 *
	 */
	public void setOriginnalCost(BigDecimal originnalCost) {
		this.originnalCost = originnalCost;
	}
	
	/**
	 * 获取 币种 *.
	 *
	 * @return the 币种 *
	 */
	public String getCurrencyCdoe() {
		return currencyCdoe;
	}
	
	/**
	 * 设置 币种 *.
	 *
	 * @param currencyCdoe the new 币种 *
	 */
	public void setCurrencyCdoe(String currencyCdoe) {
		this.currencyCdoe = currencyCdoe;
	}
	
	/**
	 * 获取 营业部收货日期（可选，无则表示当前日期） *.
	 *
	 * @return the 营业部收货日期（可选，无则表示当前日期） *
	 */
	public Date getReceiveDate() {
		return receiveDate;
	}
	
	/**
	 * 设置 营业部收货日期（可选，无则表示当前日期） *.
	 *
	 * @param receiveDate the new 营业部收货日期（可选，无则表示当前日期） *
	 */
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}
	
	/**
	 * 获取 客户编码 *.
	 *
	 * @return the 客户编码 *
	 */
	public String getCustomCode() {
		return customCode;
	}
	
	/**
	 * 设置 客户编码 *.
	 *
	 * @param customCode the new 客户编码 *
	 */
	public void setCustomCode(String customCode) {
		this.customCode = customCode;
	}
	
	/**
	 * 获取 最低费用.
	 *
	 * @return the 最低费用
	 */
	public BigDecimal getMinFee() {
		return minFee;
	}
	
	/**
	 * 设置 最低费用.
	 *
	 * @param minFee the new 最低费用
	 */
	public void setMinFee(BigDecimal minFee) {
		this.minFee = minFee;
	}
	
	/**
	 * 获取 最高费用.
	 *
	 * @return the 最高费用
	 */
	public BigDecimal getMaxFee() {
		return maxFee;
	}
	
	/**
	 * 设置 最高费用.
	 *
	 * @param maxFee the new 最高费用
	 */
	public void setMaxFee(BigDecimal maxFee) {
		this.maxFee = maxFee;
	}
	
	/**
	 * 获取 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；.
	 *
	 * @return the 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；
	 */
	public String getCaculateType() {
		return caculateType;
	}
	
	/**
	 * 设置 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；.
	 *
	 * @param caculateType the new 计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；
	 */
	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}

	/**
	 * 获取 计费明细ID.
	 *
	 * @return the 计费明细ID
	 */
	public String getCriteriaDetailId() {
		return criteriaDetailId;
	}

	/**
	 * 设置 计费明细ID.
	 *
	 * @param criteriaDetailId the new 计费明细ID
	 */
	public void setCriteriaDetailId(String criteriaDetailId) {
		this.criteriaDetailId = criteriaDetailId;
	}
	
	
	//菜鸟新需求返货折扣
    private Boolean isCainiao;//是否满足条件
    private String returnWaybillNo;//原始单号
    private String oldreceiveCustomerCode;//原收货人的客户编码
    private BigDecimal returnTransportFee;//原单公布价费
    private BigDecimal returnInsuranceFee;//原单保价费
    private Date returnbilltime;//原始开单时间
    private String OriginalReceiveOrgCode;//原单号收货部门编码
 
	
	public Date getReturnbilltime() {
		return returnbilltime;
	}

	public void setReturnbilltime(Date returnbilltime) {
		this.returnbilltime = returnbilltime;
	}

	public BigDecimal getReturnTransportFee() {
		return returnTransportFee;
	}

	public void setReturnTransportFee(BigDecimal returnTransportFee) {
		this.returnTransportFee = returnTransportFee;
	}

	

	public BigDecimal getReturnInsuranceFee() {
		return returnInsuranceFee;
	}

	public void setReturnInsuranceFee(BigDecimal returnInsuranceFee) {
		this.returnInsuranceFee = returnInsuranceFee;
	}
    
   	public String getOldreceiveCustomerCode() {
   		return oldreceiveCustomerCode;
   	}

   	public void setOldreceiveCustomerCode(String oldreceiveCustomerCode) {
   		this.oldreceiveCustomerCode = oldreceiveCustomerCode;
   	}
    
	public Boolean getIsCainiao() {
		return isCainiao;
	}


	public void setIsCainiao(Boolean isCainiao) {
		this.isCainiao = isCainiao;
	}

	public String getReturnWaybillNo() {
		return returnWaybillNo;
	}

	public void setReturnWaybillNo(String returnWaybillNo) {
		this.returnWaybillNo = returnWaybillNo;
	}

	public String getOriginalReceiveOrgCode() {
		return OriginalReceiveOrgCode;
	}

	public void setOriginalReceiveOrgCode(String originalReceiveOrgCode) {
		OriginalReceiveOrgCode = originalReceiveOrgCode;
	}

	
}
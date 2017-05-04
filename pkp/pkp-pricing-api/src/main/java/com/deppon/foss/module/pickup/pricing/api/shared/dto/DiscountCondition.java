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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/ResultProductPriceDto.java
 * 
 * FILE NAME        	: ResultProductPriceDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 *****************************************************************************
 */
package com.deppon.foss.module.pickup.pricing.api.shared.dto;

import java.io.Serializable;

/**
 * 
 * @Description: 
 * ResultProductPriceDto.java Create on 2013-3-17 上午10:25:54
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
 
public class DiscountCondition implements Serializable{
    
    /** 
     * The Constant serialVersionUID. 
     */
    private static final long serialVersionUID = 1627828191943242023L;
	
	/**
     * 当前日期
     */
    private String currentDate;
    
    /**
     * 调用系统 FOSS，CRM 等
     */
    private String callSystem;
    
    /**
     * 规则类型 （增值折扣、运费折扣）
     */
    private String ruleTypeCode;
    
    /**
     * 规则库编码（不需输入）
     */
    private String ruleRepositoryCode;
    
    
    /**
     * 出发城市
     */
    private String departCity;
    
    /**
     * 出发区域
     */
    private String departArea;
    
    /**
     * 出发网点
     */
    private String departBranch;
    
    /**
     * 到达城市
     */
    private String destCity;
    
    /**
     * 到达区域
     */
    private String destArea;
    
    /**
     * 到达网点
     */
    private String destBranch;
    
    /**
     * 折扣规则（重货轻货）
     */
    private String discountBase;
    
    /**
     * 重货：重量 轻货：体积
     */
    private String amount;
    
    /**
     * 是否是月结客户
     */
    private String clientCode;
    
  
	/**
     * 产品名称
     */
    private String product;
    
    /**
     * 渠道名称
     */
    private String channel;
    
    /**
     * 行业名称
     */
    private String industry;
    
    /**
     * 货物类型
     */
    private String goodsType;
    
    /**
     * 开单日期(YYYY-MM-DD HH:mm:SS)
     */
    private String bizDate;
    
    /**
     * 开单时间（HH:mm:SS）
     */
    private String bizTime;
    
    /**
     * 是否享受过合同优惠  (未享受:1  享受过:2)
     */
    private String isOverLay;
    
    /**
     * 是否接货
     */
    private String isReceive;
    
    /**
     * 接货费金额
     */
    private String receiveCount;
    
    /**
     * 送货费金额
     */
    private String deliveryCount;
    
    /**
     * 保价费金额
     */
    private String supportCount;
    
    /**
     * 代收货款费金额
     */
    private String collectionCount;
    
    /**
     * 包装费金额
     */
    private String packageCount;
    
    /**
     * 总价格
     */
    private String amountbeforediscount;
    
    /**
     * 折后金额
     */
    private String amountafterdiscount;
    
    /**
     * 异常标示
     */
    private String error;
    
    /**
     * 异常内容
     */
    private String errorContent;
    /**
     * 折扣率
     */
	private String discoutPercent;
	
	/**
	 * 直接减免金额
	 */
	private String discoutDecrease;
	
	/**
	 * 折扣标示 0：无匹配规则       1：折扣率      2：直接减免
	 */
	private String discountFlag;
	
	/**
	 * 规则方案id
	 */
	private String ruleDefHeaderId;
	/**
	 * 规则方案名称
	 */
	private String ruleDefHeaderName;
	/**
	 * 增值服务类型
	 * */
	private String valueAddType;
	
	
	/**
	 * FOSS20150924 DP-特惠活动客户折扣新增
	 * @author foss-206860
	 * */
	//DP-特惠活动客户折扣新增--begin
	/**
	 * 客户特惠组编码
	 */
	private String customerPreferencesCode;
	/**
	 * 客户特惠组名称
	 */
	private String customerPreferencesName;
	
	public String getCustomerPreferencesCode() {
		return customerPreferencesCode;
	}
	
	public void setCustomerPreferencesCode(String customerPreferencesCode) {
		this.customerPreferencesCode = customerPreferencesCode;
	}
	
	public String getCustomerPreferencesName() {
		return customerPreferencesName;
	}
	
	public void setCustomerPreferencesName(String customerPreferencesName) {
		this.customerPreferencesName = customerPreferencesName;
	}
	//DP-特惠活动客户折扣新增--end

	public String getValueAddType() {
		return valueAddType;
	}

	public void setValueAddType(String valueAddType) {
		this.valueAddType = valueAddType;
	}

	/**
     * 当前日期
     */
	public String getCurrentDate() {
		return currentDate;
	}
	
	/**
     * 当前日期
     */
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	
	/**
	 * 出发网点
	 * @return
	 */
	public String getDepartBranch() {
		return departBranch;
	}

	/**
	 * 出发网点
	 * @return
	 */
	public void setDepartBranch(String departBranch) {
		this.departBranch = departBranch;
	}

	/**
	 * 到达网点
	 * @return
	 */
	public String getDestBranch() {
		return destBranch;
	}
	
	/**
	 * 到达网点
	 * @return
	 */
	public void setDestBranch(String destBranch) {
		this.destBranch = destBranch;
	}

	/**
	 * 客户编码
	 * @return
	 */
	  public String getClientCode() {
			return clientCode;
		}
	/**
	   * 客户编码
	   * @return
	*/
	 public void setClientCode(String clientCode) {
			this.clientCode = clientCode;
	 }

	/**
	 * 折扣率
	 * @return
	 */
	public String getDiscoutPercent() {
		return discoutPercent;
	}

	/**
	 * 折扣率
	 * @return
	 */
	public void setDiscoutPercent(String discoutPercent) {
		this.discoutPercent = discoutPercent;
	}

	/**
	 * 直接减免金额
	 * @return
	 */
	public String getDiscoutDecrease() {
		return discoutDecrease;
	}

	/**
	 * 直接减免金额
	 * @return
	 */
	public void setDiscoutDecrease(String discoutDecrease) {
		this.discoutDecrease = discoutDecrease;
	}

	/**
	 * 折扣标示 0：无匹配规则       1：折扣率      2：直接减免
	 * @return
	 */
	public String getDiscountFlag() {
		return discountFlag;
	}

	/**
	 * 折扣标示 0：无匹配规则       1：折扣率      2：直接减免
	 * @return
	 */
	public void setDiscountFlag(String discountFlag) {
		this.discountFlag = discountFlag;
	}

	/**
	 * 方案ＩＤ
	 * @return
	 */
	public String getRuleDefHeaderId() {
		return ruleDefHeaderId;
	}

	/**
	 * 方案ＩＤ
	 * @return
	 */
	public void setRuleDefHeaderId(String ruleDefHeaderId) {
		this.ruleDefHeaderId = ruleDefHeaderId;
	}

	/**
	 * 方案名称
	 * @return
	 */
	public String getRuleDefHeaderName() {
		return ruleDefHeaderName;
	}

	/**
	 * 方案名称
	 * @return
	 */
	public void setRuleDefHeaderName(String ruleDefHeaderName) {
		this.ruleDefHeaderName = ruleDefHeaderName;
	}

	/**
     * 调用系统
     */
	public String getCallSystem() {
		return callSystem;
	}
	/**
     * 调用系统
     */
	public void setCallSystem(String callSystem) {
		this.callSystem = callSystem;
	}
	/**
     * 规则类型
     */
	public String getRuleTypeCode() {
		return ruleTypeCode;
	}
	/**
     * 规则类型
     */
	public void setRuleTypeCode(String ruleTypeCode) {
		this.ruleTypeCode = ruleTypeCode;
	}
	/**
	 * 规则库编码
	 * @return
	 */
	public String getRuleRepositoryCode() {
		return ruleRepositoryCode;
	}
	/**
	 * 规则库编码
	 * @return
	 */
	public void setRuleRepositoryCode(String ruleRepositoryCode) {
		this.ruleRepositoryCode = ruleRepositoryCode;
	}
	/**
	 * 出发城市
	 */
	public String getDepartCity() {
		return departCity;
	}
	/**
	 * 出发城市
	 */
	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}
	/**
	 * 出发区域
	 */
	public String getDepartArea() {
		return departArea;
	}
	/**
	 * 出发区域
	 */
	public void setDepartArea(String departArea) {
		this.departArea = departArea;
	}
	/**
	 * 到达城市
	 */
	public String getDestCity() {
		return destCity;
	}
	/**
	 * 到达城市
	 */
	public void setDestCity(String destCity) {
		this.destCity = destCity;
	}
	/**
	 * 到达区域
	 */
	public String getDestArea() {
		return destArea;
	}
	/**
	 * 到达区域
	 */
	public void setDestArea(String destArea) {
		this.destArea = destArea;
	}
	/**
	 * 折扣规则
	 */
	public String getDiscountBase() {
		return discountBase;
	}
	/**
	 * 折扣规则
	 */
	public void setDiscountBase(String discountBase) {
		this.discountBase = discountBase;
	}
	/**
	 * 货物重量或体积
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * 货物重量或体积
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * 产品名称
	 */
	public String getProduct() {
		return product;
	}
	/**
	 * 产品名称
	 */
	public void setProduct(String product) {
		this.product = product;
	}
	/**
	 * 渠道名称
	 */
	public String getChannel() {
		return channel;
	}
	/**
	 * 渠道名称
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}
	/**
	 * 行业名称
	 */
	public String getIndustry() {
		return industry;
	}
	/**
	 * 行业名称
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}

	/**
	 * 货物类型
	 */
	public String getGoodsType() {
		return goodsType;
	}
	/**
	 * 货物类型
	 */
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	/**
	 * 开单日期
	 */
	public String getBizDate() {
		return bizDate;
	}
	/**
	 * 开单日期
	 */
	public void setBizDate(String bizDate) {
		this.bizDate = bizDate;
	}
	/**
	 * 开单时间
	 */
	public String getBizTime() {
		return bizTime;
	}
	/**
	 * 开单时间
	 */
	public void setBizTime(String bizTime) {
		this.bizTime = bizTime;
	}
	/**
	 * 享受过合同折扣
	 */
	public String getIsOverLay() {
		return isOverLay;
	}
	/**
	 * 享受过合同折扣
	 */
	public void setIsOverLay(String isOverLay) {
		this.isOverLay = isOverLay;
	}
	/**
	 * 是否接货
	 */
	public String getIsReceive() {
		return isReceive;
	}
	/**
	 * 是否接货
	 */
	public void setIsReceive(String isReceive) {
		this.isReceive = isReceive;
	}
	/**
	 * 接货费金额
	 */
	public String getReceiveCount() {
		return receiveCount;
	}
	/**
	 * 接货费金额
	 */
	public void setReceiveCount(String receiveCount) {
		this.receiveCount = receiveCount;
	}
	/**
	 * 送货费金额
	 */
	public String getDeliveryCount() {
		return deliveryCount;
	}
	/**
	 * 送货费金额
	 */
	public void setDeliveryCount(String deliveryCount) {
		this.deliveryCount = deliveryCount;
	}
	/**
	 * 保价金额
	 */
	public String getSupportCount() {
		return supportCount;
	}
	/**
	 * 保价金额
	 */
	public void setSupportCount(String supportCount) {
		this.supportCount = supportCount;
	}
	/**
	 * 代收费金额
	 */
	public String getCollectionCount() {
		return collectionCount;
	}
	/**
	 * 代收费金额
	 */
	public void setCollectionCount(String collectionCount) {
		this.collectionCount = collectionCount;
	}
	/**
	 * 包装费金额
	 */
	public String getPackageCount() {
		return packageCount;
	}
	/**
	 * 包装费金额
	 */
	public void setPackageCount(String packageCount) {
		this.packageCount = packageCount;
	}
	/**
	 * 总价格
	 */
	public String getAmountbeforediscount() {
		return amountbeforediscount;
	}
	/**
	 * 总价格
	 */
	public void setAmountbeforediscount(String amountbeforediscount) {
		this.amountbeforediscount = amountbeforediscount;
	}
	/**
	 * 折后总额
	 */
	public String getAmountafterdiscount() {
		return amountafterdiscount;
	}
	/**
	 * 折后总额
	 */
	public void setAmountafterdiscount(String amountafterdiscount) {
		this.amountafterdiscount = amountafterdiscount;
	}
	/**
	 * 异常标示
	 */
	public String getError() {
		return error;
	}
	/**
	 * 异常标示
	 */
	public void setError(String error) {
		this.error = error;
	}
	/**
	 * 异常内容
	 */
	public String getErrorContent() {
		return errorContent;
	}
	/**
	 * 异常内容
	 */
	public void setErrorContent(String errorContent) {
		this.errorContent = errorContent;
	}
    
}
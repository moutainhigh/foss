/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/vo/WaybillDiscountVo.java
 * 
 * FILE NAME        	: WaybillDiscountVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;

/**
 * 
 * 折扣优惠
 * @author 025000-FOSS-helong
 * @date 2012-12-27 下午02:11:13
 */
public class WaybillDiscountVo {
	
	/**
	 * 折扣ID
	 */
	private String discountId;
	
	/**
	 * 费用类型id
	 */
	private String chargeDetailId;
	
	/**
	 * 优惠项目名称
	 */
	private String favorableItemName;
	
	/**
	 * 优惠项目CODE	
	 */
	private String favorableItemCode;
	
	/**
	 * 优惠项目子类别code	
	 */
	private String favorableItemSubTypeCode;
	
	public String getFavorableItemSubTypeCode() {
		return favorableItemSubTypeCode;
	}

	public void setFavorableItemSubTypeCode(String favorableItemSubTypeCode) {
		this.favorableItemSubTypeCode = favorableItemSubTypeCode;
	}

	/**
	 * 优惠折扣类别名称
	 */
	private String favorableTypeName;
	
	/**
	 * 优惠折扣类别CODE
	 */
	private String favorableTypeCode;
	/**
	 * 优惠折扣子类别名称
	 */
	private String favorableSubTypeName;
	
	/**
	 * 优惠折扣子类别CODE
	 */
	private String favorableSubTypeCode;
	
	/**
	 * 优惠折扣率
	 */
	private String favorableDiscount;
	
	/**
	 * 优惠金额
	 */
	private String favorableAmount;
	
	/**
	 * 原始金额
	 * */
	private String favorableBeforeAmount;
	
	public String getFavorableBeforeAmount() {
		return favorableBeforeAmount;
	}

	public void setFavorableBeforeAmount(String favorableBeforeAmount) {
		this.favorableBeforeAmount = favorableBeforeAmount;
	}

	/**
	 * 营销活动CODE
	 */
	private String activeCode;
	/**
	 * 营销活动名称
	 */
	private String activeName;
	/**
	 * 营销活动开始时间
	 */
	private Date activeStartTime;
	/**
	 * 营销活动结束时间
	 */
	private Date activeEndTime;
	/**
	 * 营销活动折扣关联的CRM_ID
	 */
	private BigDecimal optionsCrmId;
	
	/**
	 * 快递续重优惠折扣率
	 */
	private String continueFavorableDiscount;
	
	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public Date getActiveStartTime() {
		return activeStartTime;
	}

	public void setActiveStartTime(Date activeStartTime) {
		this.activeStartTime = activeStartTime;
	}

	public Date getActiveEndTime() {
		return activeEndTime;
	}

	public void setActiveEndTime(Date activeEndTime) {
		this.activeEndTime = activeEndTime;
	}

	public BigDecimal getOptionsCrmId() {
		return optionsCrmId;
	}

	public void setOptionsCrmId(BigDecimal optionsCrmId) {
		this.optionsCrmId = optionsCrmId;
	}

	/**
	 * @return  the discountId
	 */
	public String getDiscountId() {
		return discountId;
	}

	/**
	 * @param discountId the discountId to set
	 */
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	/**
	 * @return  the chargeDetailId
	 */
	public String getChargeDetailId() {
		return chargeDetailId;
	}

	
	/**
	 * @param chargeDetailId the chargeDetailId to set
	 */
	public void setChargeDetailId(String chargeDetailId) {
		this.chargeDetailId = chargeDetailId;
	}

	
	/**
	 * @return  the favorableItemName
	 */
	public String getFavorableItemName() {
		return favorableItemName;
	}

	
	/**
	 * @param favorableItemName the favorableItemName to set
	 */
	public void setFavorableItemName(String favorableItemName) {
		this.favorableItemName = favorableItemName;
	}


	
	/**
	 * @return  the favorableDiscount
	 */
	public String getFavorableDiscount() {
		return favorableDiscount;
	}

	
	/**
	 * @param favorableDiscount the favorableDiscount to set
	 */
	public void setFavorableDiscount(String favorableDiscount) {
		//四舍五入保留两位小数
		this.favorableDiscount = CalculateFeeTotalUtils.formatNumberTwoDecimal(favorableDiscount);
	}

	
	/**
	 * @return  the favorableAmount
	 */
	public String getFavorableAmount() {
		return favorableAmount;
	}

	
	/**
	 * @param favorableAmount the favorableAmount to set
	 */
	public void setFavorableAmount(String favorableAmount) {
		//四舍五入取整数
		this.favorableAmount = CalculateFeeTotalUtils.formatNumberInteger(favorableAmount);
	}


	public String getFavorableItemCode() {
		return favorableItemCode;
	}


	public void setFavorableItemCode(String favorableItemCode) {
		this.favorableItemCode = favorableItemCode;
	}


	public String getFavorableTypeName() {
		return favorableTypeName;
	}


	public void setFavorableTypeName(String favorableTypeName) {
		this.favorableTypeName = favorableTypeName;
	}


	public String getFavorableTypeCode() {
		return favorableTypeCode;
	}


	public void setFavorableTypeCode(String favorableTypeCode) {
		this.favorableTypeCode = favorableTypeCode;
	}


	public String getFavorableSubTypeName() {
		return favorableSubTypeName;
	}


	public void setFavorableSubTypeName(String favorableSubTypeName) {
		this.favorableSubTypeName = favorableSubTypeName;
	}


	public String getFavorableSubTypeCode() {
		return favorableSubTypeCode;
	}


	public void setFavorableSubTypeCode(String favorableSubTypeCode) {
		this.favorableSubTypeCode = favorableSubTypeCode;
	}

	public String getContinueFavorableDiscount() {
		return continueFavorableDiscount;
	}

	public void setContinueFavorableDiscount(String continueFavorableDiscount) {
		//四舍五入保留两位小数
		this.continueFavorableDiscount = CalculateFeeTotalUtils.formatNumberTwoDecimal(continueFavorableDiscount);
	}
	
}
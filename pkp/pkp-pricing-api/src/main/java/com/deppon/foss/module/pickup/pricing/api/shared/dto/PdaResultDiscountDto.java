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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/dto/PdaResultDiscountDto.java
 * 
 * FILE NAME        	: PdaResultDiscountDto.java
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
 * 
 * @Description: PAD客户端产品价格计算结果打折情况的DTO
 * PdaQueryBillCalculateDto.java Create on 2013-1-14 上午10:11:47
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PdaResultDiscountDto implements Serializable {
    
	/**
	 * 序列ID
	 */
	private static final long serialVersionUID = 2725068018399891129L;
	/**
	 * 市场NAME
	 */
	private String marketName;
	/**
	 * 方案渠道NAME
	 */
	private String saleChannelName;
	/**
	 * 折扣率
	 */
	private BigDecimal discountRate;
	/**
	 * 减免的费用
	 */
	private BigDecimal reduceFee;
	
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
	 * 获取 市场NAME.
	 *
	 * @return the 市场NAME
	 */
	public String getMarketName() {
		return marketName;
	}
	
	/**
	 * 设置 市场NAME.
	 *
	 * @param marketName the new 市场NAME
	 */
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	
	/**
	 * 获取 方案渠道NAME.
	 *
	 * @return the 方案渠道NAME
	 */
	public String getSaleChannelName() {
		return saleChannelName;
	}
	
	/**
	 * 设置 方案渠道NAME.
	 *
	 * @param saleChannelName the new 方案渠道NAME
	 */
	public void setSaleChannelName(String saleChannelName) {
		this.saleChannelName = saleChannelName;
	}
	
	/**
	 * 获取 折扣率.
	 *
	 * @return the 折扣率
	 */
	public BigDecimal getDiscountRate() {
		return discountRate;
	}
	
	/**
	 * 设置 折扣率.
	 *
	 * @param discountRate the new 折扣率
	 */
	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}
	
	/**
	 * 获取 减免的费用.
	 *
	 * @return the 减免的费用
	 */
	public BigDecimal getReduceFee() {
		return reduceFee;
	}
	
	/**
	 * 设置 减免的费用.
	 *
	 * @param reduceFee the new 减免的费用
	 */
	public void setReduceFee(BigDecimal reduceFee) {
		this.reduceFee = reduceFee;
	} 
	
	
}
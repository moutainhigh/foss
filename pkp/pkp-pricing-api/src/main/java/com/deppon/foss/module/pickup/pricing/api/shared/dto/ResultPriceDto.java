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
import java.math.BigDecimal;

/**
 * 
 * @Description: 
 * ResultProductPriceDto.java Create on 2013-3-17 上午10:25:54
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
 
public class ResultPriceDto implements Serializable{
    
    /** 
     * The Constant serialVersionUID. 
     */
    private static final long serialVersionUID = 1627828191943242023L;

 
    
    /** 
     * 是否集中接货.   
     */
    private String centralizePickup;
    
    /**
     * 是否集中送货 2016.07.08新增 首续重价格方案用
     */
    private String centralizeDelivery;
    /** 
     * 计费类别.   
     */
    private String caculateType;
    
    /**
     *  费用单价.   
     */
    private BigDecimal fee; 
   
    
    /** 
     * 最低费用.   
     */
    private Long minFee;
    
 
    
    /**
     * 临界值
     * 
     */
    private long criticalValue;
    
  
    
    /**
     * 分段数
     */
    private String sectionId;



	public String getCentralizePickup() {
		return centralizePickup;
	}



	public void setCentralizePickup(String centralizePickup) {
		this.centralizePickup = centralizePickup;
	}



	public String getCaculateType() {
		return caculateType;
	}



	public void setCaculateType(String caculateType) {
		this.caculateType = caculateType;
	}






	public BigDecimal getFee() {
		return fee;
	}



	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}



	public Long getMinFee() {
		return minFee;
	}



	public void setMinFee(Long minFee) {
		this.minFee = minFee;
	}



	public long getCriticalValue() {
		return criticalValue;
	}



	public void setCriticalValue(long criticalValue) {
		this.criticalValue = criticalValue;
	}



	public String getSectionId() {
		return sectionId;
	}



	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}



	public String getCentralizeDelivery() {
		return centralizeDelivery;
	}



	public void setCentralizeDelivery(String centralizeDelivery) {
		this.centralizeDelivery = centralizeDelivery;
	}
    
    
}
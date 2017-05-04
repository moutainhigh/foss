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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/dto/PreferentialInfoDto.java
 * 
 * FILE NAME        	: PreferentialInfoDto.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.api.shared.dto;

import java.util.Date;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity;


/**
 * 客户合同优惠信息封装类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-2-22 下午3:33:50 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-2-22 下午3:33:50
 * @since
 * @version
 */
public class PreferentialInfoDto extends PreferentialEntity {

    
    /**
     * 
     */
    private static final long serialVersionUID = 7928258184596893454L;
    
    /**
     * 合同起始日期.
     */
    private Date beginTime;
    
    /**
     * 合同到期日期.
     */
    private Date endTime;
    
    /**
     *价格版本时间.
     */
    private Date priceVersionDate;
    /**
	 * 快递价格版本时间
	 */
	private Date exPriceVersionDate;
	/**
	 * 快递优惠类型
	 */
	 private String exPreferentialType;
	 
	 /**
	  * 快递结款方式
	  */
	 private String exPayWay;
	 
	 /**
	  * 是否单独报价
	  */
	 private String isAloneQuotation;
	 /**
	  * 重泡比
	  */
    private String HeavyBubbleRatio ;
	public String getHeavyBubbleRatio() {
		return HeavyBubbleRatio;
	}


	public void setHeavyBubbleRatio(String heavyBubbleRatio) {
		HeavyBubbleRatio = heavyBubbleRatio;
	}


	public Date getExPriceVersionDate() {
		return exPriceVersionDate;
	}


	public void setExPriceVersionDate(Date exPriceVersionDate) {
		this.exPriceVersionDate = exPriceVersionDate;
	}


	public String getExPreferentialType() {
		return exPreferentialType;
	}


	public void setExPreferentialType(String exPreferentialType) {
		this.exPreferentialType = exPreferentialType;
	}


	public String getExPayWay() {
		return exPayWay;
	}


	public void setExPayWay(String exPayWay) {
		this.exPayWay = exPayWay;
	}


	/**
     * 获取 合同起始日期.
     *
     * @return  the beginTime
     */


	public void setPriceVersionDate(Date priceVersionDate) {
		this.priceVersionDate = priceVersionDate;
	}


	/**
     * 设置 合同起始日期.
     *
     * @param beginTime the beginTime to set
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    
    /**
     * 获取 合同到期日期.
     *
     * @return  the endTime
     */
    public Date getEndTime() {
        return endTime;
    }

    
    /**
     * 设置 合同到期日期.
     *
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    /**
     * 获取价格版本时间
     * @return
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置价格版本时间
     * @return
     */
    public Date getPriceVersionDate() {
		return priceVersionDate;
	}

    /**
     * 获取是否单独报价
     * @return
     */
	public String getIsAloneQuotation() {
		return isAloneQuotation;
	}

	/**
	 * 设置是否单独报价
	 * @param isAloneQuotation
	 */
	public void setIsAloneQuotation(String isAloneQuotation) {
		this.isAloneQuotation = isAloneQuotation;
	}
    

}

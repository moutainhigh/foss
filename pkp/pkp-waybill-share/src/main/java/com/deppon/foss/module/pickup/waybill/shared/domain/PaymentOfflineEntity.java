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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/domain/PaymentOfflineEntity.java
 * 
 * FILE NAME        	: PaymentOfflineEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http:/**  www.deppon.com
 *
 */ 
package com.deppon.foss.module.pickup.waybill.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 定义“离线运单客户付款明细”实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:李凤腾,date:2012-10-11 上午10:10:54, </p>
 * @author 李凤腾
 * @date 2012-10-11 上午10:10:54
 * @since
 * @version
 */
public class PaymentOfflineEntity extends BaseEntity {

    /**
     * 序列化对象ID
     */
    private static final long serialVersionUID = 7868172543564530499L;
    
    /** 
     *  付款类型
     */
    private String type;

    /** 
     *  付款金额
     */
    private Long amount;

    /** 
     *  付款时间
     */
    private Date paymentTime;

    /**
     *   开始时间
     */
    private Date beginTime;

    /** 
     *  结束时间
     */
    private Date endTime;

	
	/**
	 * @return the type .
	 */
	public String getType() {
		return type;
	}

	
	/**
	 *@param type the type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	
	/**
	 * @return the amount .
	 */
	public Long getAmount() {
		return amount;
	}

	
	/**
	 *@param amount the amount to set.
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	
	/**
	 * @return the paymentTime .
	 */
	public Date getPaymentTime() {
		return paymentTime;
	}

	
	/**
	 *@param paymentTime the paymentTime to set.
	 */
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	
	/**
	 * @return the beginTime .
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	
	/**
	 *@param beginTime the beginTime to set.
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	
	/**
	 * @return the endTime .
	 */
	public Date getEndTime() {
		return endTime;
	}

	
	/**
	 *@param endTime the endTime to set.
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

   
    

}
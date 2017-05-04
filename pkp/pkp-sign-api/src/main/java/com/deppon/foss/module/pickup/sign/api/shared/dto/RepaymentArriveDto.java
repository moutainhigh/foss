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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/RepaymentArriveDto.java
 * 
 * FILE NAME        	: RepaymentArriveDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 付款返回Dto.
 *
 * @author 043258-
 * 		foss-zhaobin
 * @date 2012-12-24 
 * 		下午5:15:44
 */
public class RepaymentArriveDto implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7358516922337310684L;
	
	/** 到付总额（到付运费+代收货款）. */
	private BigDecimal arriveAmount;
	
	/** 到付运费. */
	private BigDecimal actualFreight;
	
	/** 付款方式. */
	private String paymentType;
	
	/** 付款时间. */
	private Date paymentTime;

	
	/**
	 * Gets the arrive amount.
	 *
	 * @return the arriveAmount
	 */
	public BigDecimal getArriveAmount()
	{
		return arriveAmount;
	}

	/**
	 * Sets the arrive amount.
	 *
	 * @param arriveAmount the arriveAmount to see
	 */
	public void setArriveAmount(BigDecimal arriveAmount)
	{
		this.arriveAmount = arriveAmount;
	}

	/**
	 * Gets the actual freight.
	 *
	 * @return the actualFreight
	 */
	public BigDecimal getActualFreight()
	{
		return actualFreight;
	}

	/**
	 * Sets the actual freight.
	 *
	 * @param actualFreight the actualFreight to see
	 */
	public void setActualFreight(BigDecimal actualFreight)
	{
		this.actualFreight = actualFreight;
	}

	/**
	 * Gets the payment type.
	 *
	 * @return the paymentType
	 */
	public String getPaymentType()
	{
		return paymentType;
	}

	/**
	 * Sets the payment type.
	 *
	 * @param paymentType the paymentType to see
	 */
	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}

	/**
	 * Gets the payment time.
	 *
	 * @return the paymentTime
	 */
	public Date getPaymentTime()
	{
		return paymentTime;
	}

	/**
	 * Sets the payment time.
	 *
	 * @param paymentTime the paymentTime to see
	 */
	public void setPaymentTime(Date paymentTime)
	{
		this.paymentTime = paymentTime;
	}

}
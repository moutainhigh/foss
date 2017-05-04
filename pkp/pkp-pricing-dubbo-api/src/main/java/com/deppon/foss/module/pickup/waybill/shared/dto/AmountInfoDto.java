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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/AmountInfoDto.java
 * 
 * FILE NAME        	: AmountInfoDto.java
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
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 运单价格明细实体
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-11-15 下午4:28:40
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-11-15 下午4:28:40
 * @since
 * @version
 */
public class AmountInfoDto implements Serializable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 1052410578171866859L;

	/**
	 * 计价条目编码
	 */
	private String valuationCode;

	/**
	 * 计价金额
	 */
	private BigDecimal valuationAmonut;

	/**
	 * @return the valuationCode .
	 */
	public String getValuationCode() {
		return valuationCode;
	}

	/**
	 * @param valuationCode
	 *            the valuationCode to set.
	 */
	public void setValuationCode(String valuationCode) {
		this.valuationCode = valuationCode;
	}

	/**
	 * @return the valuationAmonut .
	 */
	public BigDecimal getValuationAmonut() {
		return valuationAmonut;
	}

	/**
	 * @param valuationAmonut
	 *            the valuationAmonut to set.
	 */
	public void setValuationAmonut(BigDecimal valuationAmonut) {
		this.valuationAmonut = valuationAmonut;
	}

}
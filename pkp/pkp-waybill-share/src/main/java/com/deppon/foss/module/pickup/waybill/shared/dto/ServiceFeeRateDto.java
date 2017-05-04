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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dto/ServiceFeeRateDto.java
 * 
 * FILE NAME        	: ServiceFeeRateDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.math.BigDecimal;

/**
 * 
 * 查询装卸费时返回的DTO
 * @author 025000-FOSS-helong
 * @date 2013-2-25 下午03:41:52
 */
public class ServiceFeeRateDto {
	
	/**
	 * 装卸费费率
	 */
	private BigDecimal serviceFeeRate;
	
	/**
	 * 提示信息
	 */
	private String message;

	/**
	 * 
	 * 装卸费费率
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 下午03:44:24
	 * @return
	 */
	public BigDecimal getServiceFeeRate() {
		return serviceFeeRate;
	}

	/**
	 * 
	 * 装卸费费率
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 下午03:44:24
	 * @return
	 */
	public void setServiceFeeRate(BigDecimal serviceFeeRate) {
		this.serviceFeeRate = serviceFeeRate;
	}

	/**
	 * 
	 * 提示信息
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 下午03:44:24
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * 提示信息
	 * @author 025000-FOSS-helong
	 * @date 2013-2-25 下午03:44:24
	 * @return
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
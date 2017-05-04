/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/QueryDeparturePlanDto.java
 *  
 *  FILE NAME          :QueryDeparturePlanDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.dto
 * FILE    NAME: QueryDeparturePlanDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 查询发车计划结果
 * @author 046130-foss-xuduowei
 * @date 2012-11-27 下午7:36:51
 */
public class QueryDeparturePlanDto {
	/**
	 * 最大净空
	 */
	private BigDecimal ratedVolume;
	/**
	 * 最大载重
	 */
	private BigDecimal ratedWeight;
	/**
	 * 计划发车日期
	 */
	private Date planDepartDate;
	
	
	public BigDecimal getRatedVolume() {
		return ratedVolume;
	}
	public void setRatedVolume(BigDecimal ratedVolume) {
		this.ratedVolume = ratedVolume.setScale(2,BigDecimal.ROUND_HALF_UP);
	}
	public BigDecimal getRatedWeight() {
		return ratedWeight;
	}
	public void setRatedWeight(BigDecimal ratedWeight) {
		this.ratedWeight = ratedWeight.setScale(2,BigDecimal.ROUND_HALF_UP);
	}
	public Date getPlanDepartDate() {
		return planDepartDate;
	}
	public void setPlanDepartDate(Date planDepartDate) {
		this.planDepartDate = planDepartDate;
	}
	
	
}
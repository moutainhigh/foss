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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/vo/FlightPriceVo.java
 * 
 * FILE NAME        	: FlightPriceVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightDto;

/**
 * (时效区域VO)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-张斌,date:2012-12-05 下午15:13:10
 * </p>
 * 
 */
public class FlightPriceVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4480452754031715805L;
	
	/**
	 * 代理航空公司运价方案实体
	 */
	private FlightPricePlanEntity flightPricePlanEntity;
	/**
	 * list
	 */
	private List<FlightPricePlanEntity> flightPricePlanEntityList;
	/**
	 * 代理航空公司运价方案明细信息
	 */
	private FlightPricePlanDetailEntity flightPricePlanDetailEntity;
	/**
	 * 明细list
	 */
	private List<FlightPricePlanDetailEntity> flightPricePlanDetailEntityList;
	/**
	 * idList
	 */
	private List<String> idList;
	/**
	 * 航班信息
	 */
	private FlightEntity flightEntity;
	
	/**
	 * 航班信息DTO
	 */
	private List<FlightDto> flightDtoList;
	


	/**
	 * 获取 航班信息DTO.
	 *
	 * @return the 航班信息DTO
	 */
	public List<FlightDto> getFlightDtoList() {
		return flightDtoList;
	}
	
	/**
	 * 设置 航班信息DTO.
	 *
	 * @param flightDtoList the new 航班信息DTO
	 */
	public void setFlightDtoList(List<FlightDto> flightDtoList) {
		this.flightDtoList = flightDtoList;
	}
	
	/**
	 * 获取 航班信息.
	 *
	 * @return the 航班信息
	 */
	public FlightEntity getFlightEntity() {
		return flightEntity;
	}
	
	/**
	 * 设置 航班信息.
	 *
	 * @param flightEntity the new 航班信息
	 */
	public void setFlightEntity(FlightEntity flightEntity) {
		this.flightEntity = flightEntity;
	}
	
	/**
	 * 获取 idList.
	 *
	 * @return the idList
	 */
	public List<String> getIdList() {
		return idList;
	}
	
	/**
	 * 设置 idList.
	 *
	 * @param idList the new idList
	 */
	public void setIdList(List<String> idList) {
		this.idList = idList;
	}
	
	/**
	 * 获取 代理航空公司运价方案实体.
	 *
	 * @return the 代理航空公司运价方案实体
	 */
	public FlightPricePlanEntity getFlightPricePlanEntity() {
		return flightPricePlanEntity;
	}
	
	/**
	 * 设置 代理航空公司运价方案实体.
	 *
	 * @param flightPricePlanEntity the new 代理航空公司运价方案实体
	 */
	public void setFlightPricePlanEntity(FlightPricePlanEntity flightPricePlanEntity) {
		this.flightPricePlanEntity = flightPricePlanEntity;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public List<FlightPricePlanEntity> getFlightPricePlanEntityList() {
		return flightPricePlanEntityList;
	}
	
	/**
	 * 
	 *
	 * @param flightPricePlanEntityList 
	 */
	public void setFlightPricePlanEntityList(
			List<FlightPricePlanEntity> flightPricePlanEntityList) {
		this.flightPricePlanEntityList = flightPricePlanEntityList;
	}
	
	/**
	 * 获取 list private List<FlightPricePlanEntity> flightPricePlanEntityList; /** 代理航空公司运价方案明细信息.
	 *
	 * @return the list private List<FlightPricePlanEntity> flightPricePlanEntityList; /** 代理航空公司运价方案明细信息
	 */
	public FlightPricePlanDetailEntity getFlightPricePlanDetailEntity() {
		return flightPricePlanDetailEntity;
	}
	
	/**
	 * 设置 list private List<FlightPricePlanEntity> flightPricePlanEntityList; /** 代理航空公司运价方案明细信息.
	 *
	 * @param flightPricePlanDetailEntity the new list private List<FlightPricePlanEntity> flightPricePlanEntityList; /** 代理航空公司运价方案明细信息
	 */
	public void setFlightPricePlanDetailEntity(
			FlightPricePlanDetailEntity flightPricePlanDetailEntity) {
		this.flightPricePlanDetailEntity = flightPricePlanDetailEntity;
	}
	
	/**
	 * 获取 明细list.
	 *
	 * @return the 明细list
	 */
	public List<FlightPricePlanDetailEntity> getFlightPricePlanDetailEntityList() {
		return this.flightPricePlanDetailEntityList;
	}
	
	/**
	 * 设置 明细list.
	 *
	 * @param flightPricePlanDetailEntityList the new 明细list
	 */
	public void setFlightPricePlanDetailEntityList(
			List<FlightPricePlanDetailEntity> flightPricePlanDetailEntityList) {
	    	this.flightPricePlanDetailEntityList = flightPricePlanDetailEntityList;
	}
	
}
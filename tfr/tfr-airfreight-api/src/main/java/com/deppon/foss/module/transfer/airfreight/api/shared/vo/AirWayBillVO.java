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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/api/shared/vo/AirWayBillVO.java
 *  
 *  FILE NAME          :AirWayBillVO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AirlinesValueAddEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirWayBillDto;
/**
 * 航空正单vo
 * @author 038300-foss-pengzhen
 * @date 2012-12-25 下午7:08:59
 */
public class AirWayBillVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7377323121640738206L;
	
	/**
	 * 正单list
	 */
	private List<AirWaybillEntity> result;
	/**
	 * 航空正单DTO
	 */
	private AirWayBillDto airWayBillDto;
	/**
	 * 航空正单实体
	 */
	private AirWaybillEntity optAirWaybillEntity;
	/**
	 * 航空正单明细list
	 */
	List<AirWaybillDetailEntity> airWaybillDetailEntityList;
	/**
	 * 注入航空费率entity
	 */
	private AirlinesValueAddEntity airlinesValueAddEntity;

	/**
	 * 获取 正单list.
	 *
	 * @return the 正单list
	 */
	public List<AirWaybillEntity> getResult() {
		return result;
	}

	/**
	 * 设置 正单list.
	 *
	 * @param result the new 正单list
	 */
	public void setResult(List<AirWaybillEntity> result) {
		this.result = result;
	}

	/**
	 * 获取 航空正单DTO.
	 *
	 * @return the 航空正单DTO
	 */
	public AirWayBillDto getAirWayBillDto() {
		return airWayBillDto;
	}

	/**
	 * 设置 航空正单DTO.
	 *
	 * @param airWayBillDto the new 航空正单DTO
	 */
	public void setAirWayBillDto(AirWayBillDto airWayBillDto) {
		this.airWayBillDto = airWayBillDto;
	}

	/**
	 * 获取 航空正单实体.
	 *
	 * @return the 航空正单实体
	 */
	public AirWaybillEntity getOptAirWaybillEntity() {
		return optAirWaybillEntity;
	}

	/**
	 * 设置 航空正单实体.
	 *
	 * @param optAirWaybillEntity the new 航空正单实体
	 */
	public void setOptAirWaybillEntity(AirWaybillEntity optAirWaybillEntity) {
		this.optAirWaybillEntity = optAirWaybillEntity;
	}

	/**
	 * 获取 航空正单明细list.
	 *
	 * @return the 航空正单明细list
	 */
	public List<AirWaybillDetailEntity> getAirWaybillDetailEntityList() {
		return airWaybillDetailEntityList;
	}

	/**
	 * 设置 航空正单明细list.
	 *
	 * @param airWaybillDetailEntityList the new 航空正单明细list
	 */
	public void setAirWaybillDetailEntityList(
			List<AirWaybillDetailEntity> airWaybillDetailEntityList) {
		this.airWaybillDetailEntityList = airWaybillDetailEntityList;
	}

	/**
	 * 获取 注入航空费率entity.
	 *
	 * @return the 注入航空费率entity
	 */
	public AirlinesValueAddEntity getAirlinesValueAddEntity() {
		return airlinesValueAddEntity;
	}

	/**
	 * 设置 注入航空费率entity.
	 *
	 * @param airlinesValueAddEntity the new 注入航空费率entity
	 */
	public void setAirlinesValueAddEntity(
			AirlinesValueAddEntity airlinesValueAddEntity) {
		this.airlinesValueAddEntity = airlinesValueAddEntity;
	}
}
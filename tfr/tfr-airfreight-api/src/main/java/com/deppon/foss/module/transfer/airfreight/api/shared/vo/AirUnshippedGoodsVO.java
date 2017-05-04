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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/api/shared/vo/AirUnshippedGoodsVO.java
 *  
 *  FILE NAME          :AirUnshippedGoodsVO.java
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

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirUnshippedGoodsEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillSerialNoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirUnshippedGoodsDto;

/**
 * 拉货vo
 * @author foss-liuxue(for IBM)
 * @date 2012-11-29 下午8:45:29
 */
public class AirUnshippedGoodsVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -587240827738036117L;
	/**
	 * 拉货DTO
	 */
	private AirUnshippedGoodsDto airUnshippedGoodsDto;
	/**
	 * 存储查询到的拉货记录
	 */
	private List<AirUnshippedGoodsEntity> airUnshippedGoodsList;  
	/**
	 * 流水号
	 */
	private List<AirWaybillSerialNoEntity> airWaybillSerialNoList; 
	/**
	 * 拉货信息
	 */
	private AirUnshippedGoodsEntity airUnshippedGoodsEntity; 

	/**
	 * 获取 存储查询到的拉货记录.
	 *
	 * @return the 存储查询到的拉货记录
	 */
	public List<AirUnshippedGoodsEntity> getAirUnshippedGoodsList() {
		return airUnshippedGoodsList;
	}

	/**
	 * 设置 存储查询到的拉货记录.
	 *
	 * @param airUnshippedGoodsList the new 存储查询到的拉货记录
	 */
	public void setAirUnshippedGoodsList(
			List<AirUnshippedGoodsEntity> airUnshippedGoodsList) {
		this.airUnshippedGoodsList = airUnshippedGoodsList;
	}

	/**
	 * 获取 拉货DTO.
	 *
	 * @return the 拉货DTO
	 */
	public AirUnshippedGoodsDto getAirUnshippedGoodsDto() {
		return airUnshippedGoodsDto;
	}

	/**
	 * 设置 拉货DTO.
	 *
	 * @param airUnshippedGoodsDto the new 拉货DTO
	 */
	public void setAirUnshippedGoodsDto(AirUnshippedGoodsDto airUnshippedGoodsDto) {
		this.airUnshippedGoodsDto = airUnshippedGoodsDto;
	}

	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public List<AirWaybillSerialNoEntity> getAirWaybillSerialNoList() {
		return airWaybillSerialNoList;
	}

	/**
	 * 设置 流水号.
	 *
	 * @param airWaybillSerialNoList the new 流水号
	 */
	public void setAirWaybillSerialNoList(
			List<AirWaybillSerialNoEntity> airWaybillSerialNoList) {
		this.airWaybillSerialNoList = airWaybillSerialNoList;
	}

	/**
	 * 获取 拉货信息.
	 *
	 * @return the 拉货信息
	 */
	public AirUnshippedGoodsEntity getAirUnshippedGoodsEntity() {
		return airUnshippedGoodsEntity;
	}

	/**
	 * 设置 拉货信息.
	 *
	 * @param airUnshippedGoodsEntity the new 拉货信息
	 */
	public void setAirUnshippedGoodsEntity(
			AirUnshippedGoodsEntity airUnshippedGoodsEntity) {
		this.airUnshippedGoodsEntity = airUnshippedGoodsEntity;
	} 
	
}
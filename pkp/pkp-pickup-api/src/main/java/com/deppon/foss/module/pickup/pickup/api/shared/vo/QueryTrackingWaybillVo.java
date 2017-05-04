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
 * PROJECT NAME	: pkp-pickup-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/shared/vo/QueryTrackingWaybillVo.java
 * 
 * FILE NAME        	: QueryTrackingWaybillVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.pickup.api.shared.domain.WaybillTrackInfoEntity;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.QueryTrackingWaybillDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillConditionDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.TrackingWaybillDto;

/**
 * 
 * 跟踪运单VO
 * 
 * @author ibm-wangfei
 * @date Dec 29, 2012 1:40:11 PM
 */
public class QueryTrackingWaybillVo implements Serializable {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 4564551479509721012L;

	/**
	 * 跟踪运单查询条件Dto
	 */
	private TrackingWaybillConditionDto conditionDto;

	/**
	 * 运单跟踪Dto列表
	 */
	private List<TrackingWaybillDto> trackingWaybillDtoList;

	/**
	 * 跟踪运单弹出显示DTO
	 */
	private QueryTrackingWaybillDto queryTrackingWaybillDto;
	
	/**
	 * 运单追踪信息
	 */
	private WaybillTrackInfoEntity waybillTrackInfoEntity;

	/**
	 * 获取 运单跟踪Dto列表.
	 * 
	 * @return the 运单跟踪Dto列表
	 */
	public List<TrackingWaybillDto> getTrackingWaybillDtoList() {
		return trackingWaybillDtoList;
	}

	/**
	 * 设置 运单跟踪Dto列表.
	 * 
	 * @param trackingWaybillDtoList the new 运单跟踪Dto列表
	 */
	public void setTrackingWaybillDtoList(List<TrackingWaybillDto> trackingWaybillDtoList) {
		this.trackingWaybillDtoList = trackingWaybillDtoList;
	}

	/**
	 * 获取 跟踪运单弹出显示DTO.
	 * 
	 * @return the 跟踪运单弹出显示DTO
	 */
	public QueryTrackingWaybillDto getQueryTrackingWaybillDto() {
		return queryTrackingWaybillDto;
	}

	/**
	 * 设置 跟踪运单弹出显示DTO.
	 * 
	 * @param queryTrackingWaybillDto the new 跟踪运单弹出显示DTO
	 */
	public void setQueryTrackingWaybillDto(QueryTrackingWaybillDto queryTrackingWaybillDto) {
		this.queryTrackingWaybillDto = queryTrackingWaybillDto;
	}

	/**
	 * 跟踪运单查询条件Dto
	 * 
	 * @return 跟踪运单查询条件Dto
	 */
	public TrackingWaybillConditionDto getConditionDto() {
		return conditionDto;
	}

	/**
	 * 设置 跟踪运单查询条件Dto
	 * 
	 * @param conditionDto
	 */
	public void setConditionDto(TrackingWaybillConditionDto conditionDto) {
		this.conditionDto = conditionDto;
	}

	public WaybillTrackInfoEntity getWaybillTrackInfoEntity() {
		return waybillTrackInfoEntity;
	}

	public void setWaybillTrackInfoEntity(WaybillTrackInfoEntity waybillTrackInfoEntity) {
		this.waybillTrackInfoEntity = waybillTrackInfoEntity;
	}

}
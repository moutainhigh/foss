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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/shared/dto/QueryTrackingWaybillDto.java
 * 
 * FILE NAME        	: QueryTrackingWaybillDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.pickup.api.shared.domain.WaybillTrackInfoEntity;

/**
 * 
 * 跟踪运单弹出显示DTO
 * @author ibm-wangfei
 * @date Dec 29, 2012 1:40:11 PM
 */
public class QueryTrackingWaybillDto implements Serializable {
	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 2063055619078461713L;
	
	/**
	 * 运单配载信息dto列表
	 */
	private List<TrackAssemblyDto> assemblyDtoList;
	
	/**
	 * 配置单信息dto列表
	 */
	private List<TrackHandOverDto> handOverDtoList;
	
	/**
	 * 运单库存信息Dto
	 */
	private List<TrackStockDto> stockDtoList;
	
	/**
	 * 运单追踪信息列表
	 */
	private List<WaybillTrackInfoEntity> waybillTrackInfoEntityList;

	/**
	 * 获取 运单配载信息dto列表.
	 *
	 * @return the 运单配载信息dto列表
	 */
	public List<TrackAssemblyDto> getAssemblyDtoList() {
		return assemblyDtoList;
	}

	/**
	 * 设置 运单配载信息dto列表.
	 *
	 * @param assemblyDtoList the new 运单配载信息dto列表
	 */
	public void setAssemblyDtoList(List<TrackAssemblyDto> assemblyDtoList) {
		this.assemblyDtoList = assemblyDtoList;
	}

	/**
	 * 获取 配置单信息dto列表.
	 *
	 * @return the 配置单信息dto列表
	 */
	public List<TrackHandOverDto> getHandOverDtoList() {
		return handOverDtoList;
	}

	/**
	 * 设置 配置单信息dto列表.
	 *
	 * @param handOverDtoList the new 配置单信息dto列表
	 */
	public void setHandOverDtoList(List<TrackHandOverDto> handOverDtoList) {
		this.handOverDtoList = handOverDtoList;
	}

	/**
	 * 获取 运单库存信息Dto.
	 *
	 * @return the 运单库存信息Dto
	 */
	public List<TrackStockDto> getStockDtoList() {
		return stockDtoList;
	}

	/**
	 * 设置 运单库存信息Dto.
	 *
	 * @param stockDtoList the new 运单库存信息Dto
	 */
	public void setStockDtoList(List<TrackStockDto> stockDtoList) {
		this.stockDtoList = stockDtoList;
	}

	/**
	 * 获取 运单追踪信息列表.
	 *
	 * @return the 运单追踪信息列表
	 */
	public List<WaybillTrackInfoEntity> getWaybillTrackInfoEntityList() {
		return waybillTrackInfoEntityList;
	}

	/**
	 * 设置 运单追踪信息列表.
	 *
	 * @param waybillTrackInfoEntityList the new 运单追踪信息列表
	 */
	public void setWaybillTrackInfoEntityList(List<WaybillTrackInfoEntity> waybillTrackInfoEntityList) {
		this.waybillTrackInfoEntityList = waybillTrackInfoEntityList;
	}
}
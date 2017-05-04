/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
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
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/vo/DispatchOrderConditionVo.java
 * 
 * FILE NAME        	: DispatchOrderConditionVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.DispatchOrderVehicleDto;

/**
 * 调度订单查询条件VO
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-20 上午9:38:53
 */
public class DispatchOrderConditionVo implements Serializable {
	// 序列
	private static final long serialVersionUID = -6331288308826235791L;

	/**
	 * 处理订单查询条件Dto
	 */
	private DispatchOrderConditionDto dispatchOrderConditionDto = new DispatchOrderConditionDto();

	/**
	 * 查询处理订单任务DTO
	 */
	private DispatchOrderVehicleDto dispatchOrderVehicleDto;

	/**
	 * 返回结果 查询处理订单任务List
	 */
	private List<DispatchOrderVehicleDto> vehicleDtos;

	/**
	 * 调度订单
	 */
	private DispatchOrderEntity dispatchOrderEntity;

	/**
	 * 查询处理订单任务DTO list
	 */
	private List<DispatchOrderVehicleDto> orderIdsAndVehicleIds;

	/**
	 * 车型集合
	 */
	private List<VehicleTypeEntity> vehicleTypeList;

	/**
	 * @return the vehicleTypeList
	 */
	public List<VehicleTypeEntity> getVehicleTypeList() {
		return vehicleTypeList;
	}

	/**
	 * @param vehicleTypeList the vehicleTypeList to set
	 */
	public void setVehicleTypeList(List<VehicleTypeEntity> vehicleTypeList) {
		this.vehicleTypeList = vehicleTypeList;
	}

	/**
	 * @return the dispatchOrderConditionDto
	 */
	public DispatchOrderConditionDto getDispatchOrderConditionDto() {
		return dispatchOrderConditionDto;
	}

	/**
	 * @param dispatchOrderConditionDto the dispatchOrderConditionDto to set
	 */
	public void setDispatchOrderConditionDto(DispatchOrderConditionDto dispatchOrderConditionDto) {
		this.dispatchOrderConditionDto = dispatchOrderConditionDto;
	}

	/**
	 * @return the dispatchOrderVehicleDto
	 */
	public DispatchOrderVehicleDto getDispatchOrderVehicleDto() {
		return dispatchOrderVehicleDto;
	}

	/**
	 * @param dispatchOrderVehicleDto the dispatchOrderVehicleDto to set
	 */
	public void setDispatchOrderVehicleDto(DispatchOrderVehicleDto dispatchOrderVehicleDto) {
		this.dispatchOrderVehicleDto = dispatchOrderVehicleDto;
	}

	/**
	 * @return the vehicleDtos
	 */
	public List<DispatchOrderVehicleDto> getVehicleDtos() {
		return vehicleDtos;
	}

	/**
	 * @param vehicleDtos the vehicleDtos to set
	 */
	public void setVehicleDtos(List<DispatchOrderVehicleDto> vehicleDtos) {
		this.vehicleDtos = vehicleDtos;
	}

	/**
	 * @return the dispatchOrderEntity
	 */
	public DispatchOrderEntity getDispatchOrderEntity() {
		return dispatchOrderEntity;
	}

	/**
	 * @param dispatchOrderEntity the dispatchOrderEntity to set
	 */
	public void setDispatchOrderEntity(DispatchOrderEntity dispatchOrderEntity) {
		this.dispatchOrderEntity = dispatchOrderEntity;
	}

	/**
	 * @return the orderIdsAndVehicleIds
	 */
	public List<DispatchOrderVehicleDto> getOrderIdsAndVehicleIds() {
		return orderIdsAndVehicleIds;
	}

	/**
	 * @param orderIdsAndVehicleIds the orderIdsAndVehicleIds to set
	 */
	public void setOrderIdsAndVehicleIds(List<DispatchOrderVehicleDto> orderIdsAndVehicleIds) {
		this.orderIdsAndVehicleIds = orderIdsAndVehicleIds;
	}

}
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/vo/VehicleVo.java
 * 
 * FILE NAME        	: VehicleVo.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.dto.BindingLeasedTruckDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OwnVehicleRegionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.LeasedTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.LeasedTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckConditionDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.OwnTruckDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleActualSituationDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.VehicleDriverDto;

/**
 * 车辆Vo
 * @author 038590-foss-wanghui
 * @date 2013-3-15 下午4:49:17
 */
public class VehicleVo implements Serializable {
	// 序列
	private static final long serialVersionUID = 4973693168016460446L;

	/** 
	 * 返回的已用车辆的结果集
	 */
	private List<OwnTruckDto> usedVehicleDtos;
	/** 
	 * 修改车辆的实际情况
	 */
	private VehicleActualSituationDto situationDto;
	/** 
	 * 自有车查询条件Dto
	 */
	private OwnTruckConditionDto ownTruckConditionDto;
	/** 
	 * 外请车查询条件Dto
	 */
	private LeasedTruckConditionDto leasedTruckConditionDto;
	//329757 add for 外请车
	/**
	 * 已绑定外请车查询条件Dto
	 */
	private BindingLeasedTruckDto bindingLeasedTruckDto;
	
	/** 
	 * 自有车结果集
	 */
	private List<OwnVehicleRegionDto> ownVehicleRegionDtos;
	//329757 add for 外请车
	/**
	 * 已绑定外请车结果集
	 */
	private List<BindingLeasedTruckDto> bindingLeasedTruckDtos;
	/** 
	 * 外请车结果集
	 */
	private List<LeasedTruckDto> leasedTruckDtos;
	/** 
	 * 判断展开时是否已PDA绑定
	 */
	private VehicleDriverDto vehicleDriverDto;
	/** 
	 * 返回自有车的结果集
	 */
	private List<OwnTruckDto> ownTruckDtos;
	/** 
	 * 校验车辆的剩余体积+订单体积是否大于净空
	 * GPS电子地图参数使用
	 */
	private OwnTruckDto ownTruckDto;
	/** 
	 * GPS地址
	 */
	private String gpsUrl;

	/**
	 * @return the gpsUrl
	 */
	public String getGpsUrl() {
		return gpsUrl;
	}

	public BindingLeasedTruckDto getBindingLeasedTruckDto() {
		return bindingLeasedTruckDto;
	}

	public void setBindingLeasedTruckDto(BindingLeasedTruckDto bindingLeasedTruckDto) {
		this.bindingLeasedTruckDto = bindingLeasedTruckDto;
	}

	public List<BindingLeasedTruckDto> getBindingLeasedTruckDtos() {
		return bindingLeasedTruckDtos;
	}

	public void setBindingLeasedTruckDtos(
			List<BindingLeasedTruckDto> bindingLeasedTruckDtos) {
		this.bindingLeasedTruckDtos = bindingLeasedTruckDtos;
	}

	/**
	 * @param gpsUrl the gpsUrl to set
	 */
	public void setGpsUrl(String gpsUrl) {
		this.gpsUrl = gpsUrl;
	}

	/**
	 * @return the ownTruckDto
	 */
	public OwnTruckDto getOwnTruckDto() {
		return ownTruckDto;
	}

	/**
	 * @param ownTruckDto the ownTruckDto to set
	 */
	public void setOwnTruckDto(OwnTruckDto ownTruckDto) {
		this.ownTruckDto = ownTruckDto;
	}

	/**
	 * @return the vehicleDriverDto
	 */
	public VehicleDriverDto getVehicleDriverDto() {
		return vehicleDriverDto;
	}

	/**
	 * @param vehicleDriverDto the vehicleDriverDto to set
	 */
	public void setVehicleDriverDto(VehicleDriverDto vehicleDriverDto) {
		this.vehicleDriverDto = vehicleDriverDto;
	}

	/**
	 * @return the leasedTruckConditionDto
	 */
	public LeasedTruckConditionDto getLeasedTruckConditionDto() {
		return leasedTruckConditionDto;
	}

	/**
	 * @param leasedTruckConditionDto the leasedTruckConditionDto to set
	 */
	public void setLeasedTruckConditionDto(LeasedTruckConditionDto leasedTruckConditionDto) {
		this.leasedTruckConditionDto = leasedTruckConditionDto;
	}

	/**
	 * @return the ownTruckConditionDto
	 */
	public OwnTruckConditionDto getOwnTruckConditionDto() {
		return ownTruckConditionDto;
	}

	/**
	 * @param ownTruckConditionDto the ownTruckConditionDto to set
	 */
	public void setOwnTruckConditionDto(OwnTruckConditionDto ownTruckConditionDto) {
		this.ownTruckConditionDto = ownTruckConditionDto;
	}

	/**
	 * @return the ownTruckDtos
	 */
	public List<OwnTruckDto> getOwnTruckDtos() {
		return ownTruckDtos;
	}

	/**
	 * @param ownTruckDtos the ownTruckDtos to set
	 */
	public void setOwnTruckDtos(List<OwnTruckDto> ownTruckDtos) {
		this.ownTruckDtos = ownTruckDtos;
	}

	/**
	 * @return the situationDto
	 */
	public VehicleActualSituationDto getSituationDto() {
		return situationDto;
	}

	/**
	 * @param situationDto the situationDto to set
	 */
	public void setSituationDto(VehicleActualSituationDto situationDto) {
		this.situationDto = situationDto;
	}

	/**
	 * @return the ownVehicleRegionDtos
	 */
	public List<OwnVehicleRegionDto> getOwnVehicleRegionDtos() {
		return ownVehicleRegionDtos;
	}

	/**
	 * @param ownVehicleRegionDtos the ownVehicleRegionDtos to set
	 */
	public void setOwnVehicleRegionDtos(List<OwnVehicleRegionDto> ownVehicleRegionDtos) {
		this.ownVehicleRegionDtos = ownVehicleRegionDtos;
	}

	/**
	 * @return the leasedTruckDtos
	 */
	public List<LeasedTruckDto> getLeasedTruckDtos() {
		return leasedTruckDtos;
	}

	/**
	 * @param leasedTruckDtos the leasedTruckDtos to set
	 */
	public void setLeasedTruckDtos(List<LeasedTruckDto> leasedTruckDtos) {
		this.leasedTruckDtos = leasedTruckDtos;
	}
	/**
	 * @return the usedVehicleDtos
	 */
	public List<OwnTruckDto> getUsedVehicleDtos() {
		return usedVehicleDtos;
	}

	/**
	 * @param usedVehicleDtos the usedVehicleDtos to set
	 */
	public void setUsedVehicleDtos(List<OwnTruckDto> usedVehicleDtos) {
		this.usedVehicleDtos = usedVehicleDtos;
	}

}
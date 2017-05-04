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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/vo/FuelConsumptionVo.java
 *  
 *  FILE NAME          :FuelConsumptionVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.transfer.management.api.shared.domain.FuelDepartureEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.FuelDetailEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.FuelRoadTollEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.FuelVehicleEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.FuelConsumptionDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.FuelConsumptionTotalDto;

/**
 * 车辆油耗vo
 * @author foss-liuxue(for IBM)
 * @date 2012-12-25 下午6:48:48
 */
public class FuelConsumptionVo implements Serializable {

	private static final long serialVersionUID = -6441546428875966679L;
	
	private FuelConsumptionDto fuelConsumptionDto;
	
	private List<FuelConsumptionDto> fuelConsumptionDtoList;
	
	//车辆信息实体
	private FuelVehicleEntity fuelVehicleEntity;
	
	//发车信息List
	private List<FuelDepartureEntity> fuelDepartureList;
	
	//加油信息List
	private List<FuelDetailEntity> fuelDetailList;
	
	//路桥费List
	private List<FuelRoadTollEntity> fuelRoadTollList;
	
	private FuelConsumptionTotalDto totalDto;
	//油耗标准值
	private BigDecimal fuelStandardValue;
	
	//是否车队组织
	private String transDepartment;

	/**
	 * @return the transDepartment
	 */
	public String getTransDepartment() {
		return transDepartment;
	}

	/**
	 * @param transDepartment the transDepartment to set
	 */
	public void setTransDepartment(String transDepartment) {
		this.transDepartment = transDepartment;
	}

	/**
	 * @return the fuelStandardValue
	 */
	public BigDecimal getFuelStandardValue() {
		return fuelStandardValue;
	}

	/**
	 * @param fuelStandardValue the fuelStandardValue to set
	 */
	public void setFuelStandardValue(BigDecimal fuelStandardValue) {
		this.fuelStandardValue = fuelStandardValue;
	}

	/**
	 * @return the totalDto
	 */
	public FuelConsumptionTotalDto getTotalDto() {
		return totalDto;
	}

	/**
	 * @param totalDto the totalDto to set
	 */
	public void setTotalDto(FuelConsumptionTotalDto totalDto) {
		this.totalDto = totalDto;
	}

	/**
	 * @return the fuelDetailList
	 */
	public List<FuelDetailEntity> getFuelDetailList() {
		return fuelDetailList;
	}

	/**
	 * @param fuelDetailList the fuelDetailList to set
	 */
	public void setFuelDetailList(List<FuelDetailEntity> fuelDetailList) {
		this.fuelDetailList = fuelDetailList;
	}

	/**
	 * @return the fuelRoadTollList
	 */
	public List<FuelRoadTollEntity> getFuelRoadTollList() {
		return fuelRoadTollList;
	}

	/**
	 * @param fuelRoadTollList the fuelRoadTollList to set
	 */
	public void setFuelRoadTollList(List<FuelRoadTollEntity> fuelRoadTollList) {
		this.fuelRoadTollList = fuelRoadTollList;
	}

	/**
	 * @return the fuelDepartureList
	 */
	public List<FuelDepartureEntity> getFuelDepartureList() {
		return fuelDepartureList;
	}

	/**
	 * @param fuelDepartureList the fuelDepartureList to set
	 */
	public void setFuelDepartureList(List<FuelDepartureEntity> fuelDepartureList) {
		this.fuelDepartureList = fuelDepartureList;
	}


	/**
	 * @return the fuelConsumptionDto
	 */
	public FuelConsumptionDto getFuelConsumptionDto() {
		return fuelConsumptionDto;
	}

	/**
	 * @param fuelConsumptionDto the fuelConsumptionDto to set
	 */
	public void setFuelConsumptionDto(FuelConsumptionDto fuelConsumptionDto) {
		this.fuelConsumptionDto = fuelConsumptionDto;
	}

	/**
	 * @return the fuelConsumptionDtoList
	 */
	public List<FuelConsumptionDto> getFuelConsumptionDtoList() {
		return fuelConsumptionDtoList;
	}

	/**
	 * @param fuelConsumptionDtoList the fuelConsumptionDtoList to set
	 */
	public void setFuelConsumptionDtoList(
			List<FuelConsumptionDto> fuelConsumptionDtoList) {
		this.fuelConsumptionDtoList = fuelConsumptionDtoList;
	}

	/**
	 * @return the fuelVehicleEntity
	 */
	public FuelVehicleEntity getFuelVehicleEntity() {
		return fuelVehicleEntity;
	}

	/**
	 * @param fuelVehicleEntity the fuelVehicleEntity to set
	 */
	public void setFuelVehicleEntity(FuelVehicleEntity fuelVehicleEntity) {
		this.fuelVehicleEntity = fuelVehicleEntity;
	}

}
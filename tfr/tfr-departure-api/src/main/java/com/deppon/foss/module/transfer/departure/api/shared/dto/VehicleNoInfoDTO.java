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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/dto/VehicleNoInfoDTO.java
 *  
 *  FILE NAME          :VehicleNoInfoDTO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.dto;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 自动放行接口DTO
 * 
 * @author IBM-liubinbin
 * @date 2012-11-5 下午4:11:08
 */
public class VehicleNoInfoDTO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/*************车牌号***************/
    private String vehicleNo;
    
    /*************车辆归属类型***************/
    private String truckType;
    
    /*************车辆业务类型***************/
    private String businessType;
    
    /*************放行类型***************/
    private String departType;

	/**
	 * 获取 ***********车牌号**************.
	 *
	 * @return the ***********车牌号**************
	 */
	public String getVehicleNo()
	{
		return vehicleNo;
	}

	/**
	 * 设置 ***********车牌号**************.
	 *
	 * @param vehicleNo the new ***********车牌号**************
	 */
	public void setVehicleNo(String vehicleNo)
	{
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 ***********车辆归属类型**************.
	 *
	 * @return the ***********车辆归属类型**************
	 */
	public String getTruckType()
	{
		return truckType;
	}

	/**
	 * 设置 ***********车辆归属类型**************.
	 *
	 * @param truckType the new ***********车辆归属类型**************
	 */
	public void setTruckType(String truckType)
	{
		this.truckType = truckType;
	}

	/**
	 * 获取 ***********车辆业务类型**************.
	 *
	 * @return the ***********车辆业务类型**************
	 */
	public String getBusinessType()
	{
		return businessType;
	}

	/**
	 * 设置 ***********车辆业务类型**************.
	 *
	 * @param businessType the new ***********车辆业务类型**************
	 */
	public void setBusinessType(String businessType)
	{
		this.businessType = businessType;
	}

	/**
	 * 获取 ***********放行类型**************.
	 *
	 * @return the ***********放行类型**************
	 */
	public String getDepartType()
	{
		return departType;
	}

	/**
	 * 设置 ***********放行类型**************.
	 *
	 * @param departType the new ***********放行类型**************
	 */
	public void setDepartType(String departType)
	{
		this.departType = departType;
	}
}
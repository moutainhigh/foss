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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/LeasedTruckDto.java
 * 
 * FILE NAME        	: LeasedTruckDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;

/**
 * 外请车Dto
 * 
 * @author 038590-foss-wanghui
 * @date 2012-10-31 上午8:32:28
 */
public class LeasedTruckDto implements Serializable {

	private static final long serialVersionUID = 3853428244894328467L;

	/** 
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 *  车型
	 */
	private String vehicleType;

	/** 
	 * 司机姓名
	 */
	private String driverName;

	/** 
	 * 司机手机
	 */
	private String driverMobile;

	/** 
	 * 是否有尾板
	 */
	private boolean tailBoard;

	/** 
	 * 是否敞篷
	 */
	private boolean openVehicle;

	/**
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return the vehicleType
	 */
	public String getVehicleType() {
		return vehicleType;
	}

	/**
	 * @param vehicleType the vehicleType to set
	 */
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * @return the driverMobile
	 */
	public String getDriverMobile() {
		return driverMobile;
	}

	/**
	 * @param driverMobile the driverMobile to set
	 */
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	/**
	 * Checks if is tail board.
	 * 
	 * @return true, if is tail board
	 */
	public boolean isTailBoard() {
		return tailBoard;
	}

	/**
	 * @param tailBoard the tailBoard to set
	 */
	public void setTailBoard(boolean tailBoard) {
		this.tailBoard = tailBoard;
	}

	/**
	 * Checks if is open vehicle.
	 * 
	 * @return true, if is open vehicle
	 */
	public boolean isOpenVehicle() {
		return openVehicle;
	}

	/**
	 * @param openVehicle the openVehicle to set
	 */
	public void setOpenVehicle(boolean openVehicle) {
		this.openVehicle = openVehicle;
	}
}
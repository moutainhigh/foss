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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/FuelConsonptionForExportDto.java
 *  
 *  FILE NAME          :FuelConsonptionForExportDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.dto;

import com.deppon.foss.module.transfer.management.api.shared.domain.FuelDepartureEntity;

public class FuelConsonptionForExportDto extends FuelDepartureEntity {

	private static final long serialVersionUID = 6528809593664659990L;
	//车牌号
    private String vehicleNo;  
    
    //车型
    private String vehicleType;  

    //车辆品牌
    private String vehicleBrand;  

    //事业部
    private String divisionOrgName;  

    //车队
    private String transDepartmentName;  

    //小组
    private String groupOrgName;  

    //所属大区
    private String regionOrgName;  

    //出车性质
    private String departureTypeCode;  

    //备注
    private String vehicleRemark;

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
	 * @return the vehicleBrand
	 */
	public String getVehicleBrand() {
		return vehicleBrand;
	}

	/**
	 * @param vehicleBrand the vehicleBrand to set
	 */
	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	/**
	 * @return the divisionOrgName
	 */
	public String getDivisionOrgName() {
		return divisionOrgName;
	}

	/**
	 * @param divisionOrgName the divisionOrgName to set
	 */
	public void setDivisionOrgName(String divisionOrgName) {
		this.divisionOrgName = divisionOrgName;
	}

	/**
	 * @return the transDepartmentName
	 */
	public String getTransDepartmentName() {
		return transDepartmentName;
	}

	/**
	 * @param transDepartmentName the transDepartmentName to set
	 */
	public void setTransDepartmentName(String transDepartmentName) {
		this.transDepartmentName = transDepartmentName;
	}

	/**
	 * @return the groupOrgName
	 */
	public String getGroupOrgName() {
		return groupOrgName;
	}

	/**
	 * @param groupOrgName the groupOrgName to set
	 */
	public void setGroupOrgName(String groupOrgName) {
		this.groupOrgName = groupOrgName;
	}

	/**
	 * @return the regionOrgName
	 */
	public String getRegionOrgName() {
		return regionOrgName;
	}

	/**
	 * @param regionOrgName the regionOrgName to set
	 */
	public void setRegionOrgName(String regionOrgName) {
		this.regionOrgName = regionOrgName;
	}

	/**
	 * @return the departureTypeCode
	 */
	public String getDepartureTypeCode() {
		return departureTypeCode;
	}

	/**
	 * @param departureTypeCode the departureTypeCode to set
	 */
	public void setDepartureTypeCode(String departureTypeCode) {
		this.departureTypeCode = departureTypeCode;
	}

	/**
	 * @return the vehicleRemark
	 */
	public String getVehicleRemark() {
		return vehicleRemark;
	}

	/**
	 * @param vehicleRemark the vehicleRemark to set
	 */
	public void setVehicleRemark(String vehicleRemark) {
		this.vehicleRemark = vehicleRemark;
	}
    
    
	
}
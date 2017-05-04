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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/FuelConsumptionDto.java
 *  
 *  FILE NAME          :FuelConsumptionDto.java
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

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.management.api.shared.domain.FuelDepartureEntity;

/**
 * 车辆油耗dto
 * @author foss-liuxue(for IBM)
 * @date 2012-12-25 下午6:49:40
 */
public class FuelConsumptionDto extends FuelDepartureEntity{

	private static final long serialVersionUID = -1016770434487839407L;
    
    //车型
    private String vehicleType;  

    //车辆品牌
    private String vehicleBrand;  
	
	//出车性质
	private String departureTypeCode;
	
	//车辆id
	private String vehicleId;
	//车牌号
	private String vehicleNo;

	//事业部名称
	private String divisionOrgName;
	//区域名称
	private String regionOrgName;
	//车队
	private String transDepartmentName;
	//车队集合
	private List<String> transDepartmentNameList;
	//小组
	private String groupOrgName;
	//车辆备注
	private String vehicleRemark;
	//加油类型
	private String fuelType;
	//加油付款类型
	private String fuelPayType;
	//加油标号
	private String fuelGrade;
	//加油日期
	private Date fuelDate;
	//司机编码
	private String driverCode;
	//人员所在组织是否车队
	private String transDepartment;
	//当前部门名称
	private String currentDeptName;
	
	/**
	 * @return the currentDeptName
	 */
	public String getCurrentDeptName() {
		return currentDeptName;
	}

	/**
	 * @param currentDeptName the currentDeptName to set
	 */
	public void setCurrentDeptName(String currentDeptName) {
		this.currentDeptName = currentDeptName;
	}


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
	 * @return the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * @param driverCode the driverCode to set
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	public void setFuelDate(Date fuelDate) {
		this.fuelDate = fuelDate;
	}

	//创建时间
	private Date beginDate;
	//创建时间
	private Date endDate;
	
	/**
	 * @return the vehicleId
	 */
	public String getVehicleId() {
		return vehicleId;
	}

	/**
	 * @param vehicleId the vehicleId to set
	 */
	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

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
	 * @return the fuelType
	 */
	public String getFuelType() {
		return fuelType;
	}

	/**
	 * @param fuelType the fuelType to set
	 */
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	/**
	 * @return the fuelPayType
	 */
	public String getFuelPayType() {
		return fuelPayType;
	}

	/**
	 * @param fuelPayType the fuelPayType to set
	 */
	public void setFuelPayType(String fuelPayType) {
		this.fuelPayType = fuelPayType;
	}

	/**
	 * @return the fuelGrade
	 */
	public String getFuelGrade() {
		return fuelGrade;
	}

	/**
	 * @param fuelGrade the fuelGrade to set
	 */
	public void setFuelGrade(String fuelGrade) {
		this.fuelGrade = fuelGrade;
	}

	/**
	 * @return the beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the fuelDate
	 */
	public Date getFuelDate() {
		return fuelDate;
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

	public List<String> getTransDepartmentNameList() {
		return transDepartmentNameList;
	}

	public void setTransDepartmentNameList(List<String> transDepartmentNameList) {
		this.transDepartmentNameList = transDepartmentNameList;
	}

}
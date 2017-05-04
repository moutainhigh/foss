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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/DriveArchiveDto.java
 *  
 *  FILE NAME          :DriveArchiveDto.java
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

import java.io.Serializable;

import com.deppon.foss.module.transfer.management.api.shared.domain.DriveArchiveEntity;

/**
 * 
 */
public class DriveArchiveDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1963145261112930091L;
	
	/**
	 * id
	 */
	private String id;
	/**
	 * 车队
	 */
	private String transDepartment;
	/**
	 * 小组
	 */
	private String groupCode;
	/**
	 * 车辆品牌
	 */
	private String vehicleBrand;
	/**
	 * 车辆型号
	 */
	private String vehicleTypeLength;
	/**
	 * 货柜号码
	 */
	private String containerNo;
	/**
	 * 司机1
	 */
	private String driver1Code;
	/**
	 * 司机2
	 */
	private String driver2Code;
	/**
	 * 运输重量
	 */
	private String weight;
	/**
	 * 始发站
	 */
	private String deptRegionName;
	/**
	 * 目的站
	 */
	private String arrvRegionName;
	/**
	 * 罚款类型
	 */
	private String fineType;
	/**
	 * 制单人
	 */
	private String archiveUserCode;
	/**
	 * 是否晚到
	 */
	private String isLateArrive;
	/**
	 * 是否对发
	 */
	private String isMutual;
	/**
	 * 是否晚发
	 */
	private String isLateDeparture;
	/**
	 * 创建时间begin
	 */
	private String beginDate;
	/**
	 * 创建时间end
	 */
	private String endDate;
	
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 行驶档案实体
	 */
	private DriveArchiveEntity driveArchiveEntity;
	
	/**
	 * 配载车次号
	 */
	private String vehicleAssembleNo;
	
	/**
	 * 线路号
	 */
	private String lineNo;
	
	/**
	 * 线路名称
	 */
	private String lineName; 
	
	/**
	 * 班次号
	 */
	private String frequencyNo; 
	
	/**
	 * 规定发车日期
	 */
	private String baseDate;

	/**
	 * 获取 车队.
	 *
	 * @return the 车队
	 */
	public String getTransDepartment() {
		return transDepartment;
	}

	/**
	 * 获取 id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 设置 车队.
	 *
	 * @param transDepartment the new 车队
	 */
	public void setTransDepartment(String transDepartment) {
		this.transDepartment = transDepartment;
	}

	/**
	 * 获取 小组.
	 *
	 * @return the 小组
	 */
	public String getGroupCode() {
		return groupCode;
	}

	/**
	 * 设置 小组.
	 *
	 * @param groupCode the new 小组
	 */
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * 获取 车辆品牌.
	 *
	 * @return the 车辆品牌
	 */
	public String getVehicleBrand() {
		return vehicleBrand;
	}

	/**
	 * 设置 车辆品牌.
	 *
	 * @param vehicleBrand the new 车辆品牌
	 */
	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	/**
	 * 获取 车辆型号.
	 *
	 * @return the 车辆型号
	 */
	public String getVehicleTypeLength() {
		return vehicleTypeLength;
	}

	/**
	 * 设置 车辆型号.
	 *
	 * @param vehicleTypeLength the new 车辆型号
	 */
	public void setVehicleTypeLength(String vehicleTypeLength) {
		this.vehicleTypeLength = vehicleTypeLength;
	}

	/**
	 * 获取 货柜号码.
	 *
	 * @return the 货柜号码
	 */
	public String getContainerNo() {
		return containerNo;
	}

	/**
	 * 设置 货柜号码.
	 *
	 * @param containerNo the new 货柜号码
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	/**
	 * 获取 司机1.
	 *
	 * @return the 司机1
	 */
	public String getDriver1Code() {
		return driver1Code;
	}

	/**
	 * 设置 司机1.
	 *
	 * @param driver1Name the new 司机1
	 */
	public void setDriver1Code(String driver1Code) {
		this.driver1Code = driver1Code;
	}

	/**
	 * 获取 司机2.
	 *
	 * @return the 司机2
	 */
	public String getDriver2Code() {
		return driver2Code;
	}

	/**
	 * 设置 司机2.
	 *
	 * @param driver2Name the new 司机2
	 */
	public void setDriver2Code(String driver2Code) {
		this.driver2Code = driver2Code;
	}

	/**
	 * 获取 运输重量.
	 *
	 * @return the 运输重量
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * 设置 运输重量.
	 *
	 * @param weight the new 运输重量
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * 获取 始发站.
	 *
	 * @return the 始发站
	 */
	public String getDeptRegionName() {
		return deptRegionName;
	}

	/**
	 * 设置 始发站.
	 *
	 * @param deptRegionName the new 始发站
	 */
	public void setDeptRegionName(String deptRegionName) {
		this.deptRegionName = deptRegionName;
	}

	/**
	 * 获取 目的站.
	 *
	 * @return the 目的站
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}

	/**
	 * 设置 目的站.
	 *
	 * @param arrvRegionName the new 目的站
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}

	/**
	 * 获取 罚款类型.
	 *
	 * @return the 罚款类型
	 */
	public String getFineType() {
		return fineType;
	}

	/**
	 * 设置 罚款类型.
	 *
	 * @param fineType the new 罚款类型
	 */
	public void setFineType(String fineType) {
		this.fineType = fineType;
	}

	/**
	 * 获取 制单人.
	 *
	 * @return the 制单人
	 */
	public String getArchiveUserCode() {
		return archiveUserCode;
	}

	/**
	 * 设置 制单人.
	 *
	 * @param archiveUserName the new 制单人
	 */
	public void setArchiveUserCode(String archiveUserCode) {
		this.archiveUserCode = archiveUserCode;
	}

	/**
	 * 获取 是否晚到.
	 *
	 * @return the 是否晚到
	 */
	public String getIsLateArrive() {
		return isLateArrive;
	}

	/**
	 * 设置 是否晚到.
	 *
	 * @param isLateArrive the new 是否晚到
	 */
	public void setIsLateArrive(String isLateArrive) {
		this.isLateArrive = isLateArrive;
	}

	/**
	 * 获取 是否对发.
	 *
	 * @return the 是否对发
	 */
	public String getIsMutual() {
		return isMutual;
	}

	/**
	 * 设置 是否对发.
	 *
	 * @param isMutual the new 是否对发
	 */
	public void setIsMutual(String isMutual) {
		this.isMutual = isMutual;
	}

	/**
	 * 获取 是否晚发.
	 *
	 * @return the 是否晚发
	 */
	public String getIsLateDeparture() {
		return isLateDeparture;
	}

	/**
	 * 设置 是否晚发.
	 *
	 * @param isLateDeparture the new 是否晚发
	 */
	public void setIsLateDeparture(String isLateDeparture) {
		this.isLateDeparture = isLateDeparture;
	}

	/**
	 * 获取 创建时间begin.
	 *
	 * @return the 创建时间begin
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * 设置 创建时间begin.
	 *
	 * @param beginDate the new 创建时间begin
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 获取 创建时间end.
	 *
	 * @return the 创建时间end
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 设置 创建时间end.
	 *
	 * @param endDate the new 创建时间end
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取 线路号.
	 *
	 * @return the 线路号
	 */
	public String getLineNo() {
		return lineNo;
	}

	/**
	 * 设置 线路号.
	 *
	 * @param lineNo the new 线路号
	 */
	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	/**
	 * 获取 线路名称.
	 *
	 * @return the 线路名称
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * 设置 线路名称.
	 *
	 * @param lineName the new 线路名称
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * 获取 班次号.
	 *
	 * @return the 班次号
	 */
	public String getFrequencyNo() {
		return frequencyNo;
	}

	/**
	 * 设置 班次号.
	 *
	 * @param frequencyNo the new 班次号
	 */
	public void setFrequencyNo(String frequencyNo) {
		this.frequencyNo = frequencyNo;
	}

	/**
	 * 获取 配载车次号.
	 *
	 * @return the 配载车次号
	 */
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}

	/**
	 * 设置 配载车次号.
	 *
	 * @param vehicleAssembleNo the new 配载车次号
	 */
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}

	/**
	 * 获取 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 行驶档案实体.
	 *
	 * @return the 行驶档案实体
	 */
	public DriveArchiveEntity getDriveArchiveEntity() {
		return driveArchiveEntity;
	}
	
	/**
	 * 实际发车时间
	 */
	public String departureTime;
	
	/**
	 * 规定到达时间
	 */
	public String arriveTime;
	
	/**
	 * 规定发车时间
	 */
	public String stipulateDepartureTime;
	
	/**
	 * 实际发车时间
	 */
	public String stipulateArriveTime;
	
	/**
	 * 标准时效
	 */
	public String standardPrescription;
	
	/**
	 * 实际时效
	 */
	public String prescription;

	/**
	 * 获取 实际发车时间.
	 *
	 * @return the 实际发车时间
	 */
	public String getDepartureTime() {
		return departureTime;
	}

	/**
	 * 设置 实际发车时间.
	 *
	 * @param departureTime the new 实际发车时间
	 */
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	/**
	 * 获取 规定到达时间.
	 *
	 * @return the 规定到达时间
	 */
	public String getArriveTime() {
		return arriveTime;
	}

	/**
	 * 设置 规定到达时间.
	 *
	 * @param arriveTime the new 规定到达时间
	 */
	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * 获取 规定发车时间.
	 *
	 * @return the 规定发车时间
	 */
	public String getStipulateDepartureTime() {
		return stipulateDepartureTime;
	}

	/**
	 * 设置 规定发车时间.
	 *
	 * @param stipulateDepartureTime the new 规定发车时间
	 */
	public void setStipulateDepartureTime(String stipulateDepartureTime) {
		this.stipulateDepartureTime = stipulateDepartureTime;
	}

	/**
	 * 获取 实际发车时间.
	 *
	 * @return the 实际发车时间
	 */
	public String getStipulateArriveTime() {
		return stipulateArriveTime;
	}

	/**
	 * 设置 实际发车时间.
	 *
	 * @param stipulateArriveTime the new 实际发车时间
	 */
	public void setStipulateArriveTime(String stipulateArriveTime) {
		this.stipulateArriveTime = stipulateArriveTime;
	}

	/**
	 * 获取 标准时效.
	 *
	 * @return the 标准时效
	 */
	public String getStandardPrescription() {
		return standardPrescription;
	}

	/**
	 * 设置 标准时效.
	 *
	 * @param standardPrescription the new 标准时效
	 */
	public void setStandardPrescription(String standardPrescription) {
		this.standardPrescription = standardPrescription;
	}

	/**
	 * 获取 实际时效.
	 *
	 * @return the 实际时效
	 */
	public String getPrescription() {
		return prescription;
	}

	/**
	 * 设置 实际时效.
	 *
	 * @param prescription the new 实际时效
	 */
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	/**
	 * 设置 行驶档案实体.
	 *
	 * @param driveArchiveEntity the new 行驶档案实体
	 */
	public void setDriveArchiveEntity(DriveArchiveEntity driveArchiveEntity) {
		this.driveArchiveEntity = driveArchiveEntity;
	}

	public String getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}

}
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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/VehicleDriverWithDto.java
 * 
 *  FILE NAME     :VehicleDriverWithDto.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.dto
 * FILE    NAME: VehicleDriverWithDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 车辆司机相关DTO
 * @author 104306-foss-wangLong
 * @date 2012-12-18 下午8:24:53
 */
public class VehicleDriverWithDto implements Serializable {

	private static final long serialVersionUID = -1018494199603068516L;
	
	/** 车牌号 */
    private String vehicleNo;
    
    /** 车型名称 */
    private String vehcleLengthName;
    
    /** 车辆编码 */
    private String vehcleLengthCode;
    
	/** 司机姓名 */
	private String driverName;
	
	/** 司机编码 */
	private String driverCode;
	
	/** 司机电话 */
	private String driverPhone;
	
	/** 车辆组别 */
	private String vehcleGroupCode;
	
	private String vehcleGroupName;
	
	/** 借车申请部门编码  */
	private String borrowVehicleOrgCode;
	
	/** 借车申请状态 */
	private List<String> borrowVehicleApplyStatus;
	
	/** 请车价格 */
	private BigDecimal inviteCost;
	
	/**是否开蓬*/
	private String isOpenVehicle;
	
	/**车辆报废日期*/
	private Date vehicleScrapDate;
	/**预计车辆到达时间*/
	private Date predictArriveTime;
	/**
	 * 获得vehicleNo
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * 设置vehicleNo
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获得vehcleLengthName
	 * @return the vehcleLengthName
	 */
	public String getVehcleLengthName() {
		return vehcleLengthName;
	}

	/**
	 * 设置vehcleLengthName
	 * @param vehcleLengthName the vehcleLengthName to set
	 */
	public void setVehcleLengthName(String vehcleLengthName) {
		this.vehcleLengthName = vehcleLengthName;
	}

	/**
	 * 获得vehcleLengthCode
	 * @return the vehcleLengthCode
	 */
	public String getVehcleLengthCode() {
		return vehcleLengthCode;
	}

	/**
	 * 设置vehcleLengthCode
	 * @param vehcleLengthCode the vehcleLengthCode to set
	 */
	public void setVehcleLengthCode(String vehcleLengthCode) {
		this.vehcleLengthCode = vehcleLengthCode;
	}

	/**
	 * 获得driverName
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * 设置driverName
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 获得driverCode
	 * @return the driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * 设置driverCode
	 * @param driverCode the driverCode to set
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * 获得driverPhone
	 * @return the driverPhone
	 */
	public String getDriverPhone() {
		return driverPhone;
	}

	/**
	 * 设置driverPhone
	 * @param driverPhone the driverPhone to set
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	/**
	 * 获得vehcleGroupCode
	 * @return the vehcleGroupCode
	 */
	public String getVehcleGroupCode() {
		return vehcleGroupCode;
	}

	/**
	 * 设置vehcleGroupCode
	 * @param vehcleGroupCode the vehcleGroupCode to set
	 */
	public void setVehcleGroupCode(String vehcleGroupCode) {
		this.vehcleGroupCode = vehcleGroupCode;
	}

	/**
	 * 获得vehcleGroupName
	 * @return the vehcleGroupName
	 */
	public String getVehcleGroupName() {
		return vehcleGroupName;
	}

	/**
	 * 设置vehcleGroupName
	 * @param vehcleGroupName the vehcleGroupName to set
	 */
	public void setVehcleGroupName(String vehcleGroupName) {
		this.vehcleGroupName = vehcleGroupName;
	}

	/**
	 * 获得borrowVehicleOrgCode
	 * @return the borrowVehicleOrgCode
	 */
	public String getBorrowVehicleOrgCode() {
		return borrowVehicleOrgCode;
	}

	/**
	 * 设置borrowVehicleOrgCode
	 * @param borrowVehicleOrgCode the borrowVehicleOrgCode to set
	 */
	public void setBorrowVehicleOrgCode(String borrowVehicleOrgCode) {
		this.borrowVehicleOrgCode = borrowVehicleOrgCode;
	}

	/**
	 * 获得borrowVehicleApplyStatus
	 * @return the borrowVehicleApplyStatus
	 */
	public List<String> getBorrowVehicleApplyStatus() {
		return borrowVehicleApplyStatus;
	}

	/**
	 * 设置borrowVehicleApplyStatus
	 * @param borrowVehicleApplyStatus the borrowVehicleApplyStatus to set
	 */
	public void setBorrowVehicleApplyStatus(List<String> borrowVehicleApplyStatus) {
		this.borrowVehicleApplyStatus = borrowVehicleApplyStatus;
	}

	/**
	 * 获得inviteCost
	 * @return the inviteCost
	 */
	public BigDecimal getInviteCost() {
		return inviteCost;
	}

	/**
	 * 设置inviteCost
	 * @param inviteCost the inviteCost to set
	 */
	public void setInviteCost(BigDecimal inviteCost) {
		this.inviteCost = inviteCost;
	}

	/**
	 * 获取 是否开蓬.
	 *
	 * @return the 是否开蓬
	 */
	public String getIsOpenVehicle() {
		return isOpenVehicle;
	}

	/**
	 * 设置 是否开蓬.
	 *
	 * @param isOpenVehicle the new 是否开蓬
	 */
	public void setIsOpenVehicle(String isOpenVehicle) {
		this.isOpenVehicle = isOpenVehicle;
	}

	/**
	 * 获取 车辆报废日期.
	 *
	 * @return the 车辆报废日期
	 */
	public Date getVehicleScrapDate() {
		return vehicleScrapDate;
	}

	/**
	 * 设置 车辆报废日期.
	 *
	 * @param vehicleScrapDate the new 车辆报废日期
	 */
	public void setVehicleScrapDate(Date vehicleScrapDate) {
		this.vehicleScrapDate = vehicleScrapDate;
	}

	public Date getPredictArriveTime() {
		return predictArriveTime;
	}

	public void setPredictArriveTime(Date predictArriveTime) {
		this.predictArriveTime = predictArriveTime;
	}
}
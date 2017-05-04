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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/PassBorrowApplyEntity.java
 * 
 *  FILE NAME     :PassBorrowApplyEntity.java
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
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.util.Date;

/**
 * 借车受理
 * @author 104306-foss-wangLong
 * @date 2012-12-06 上午8:44:14
 */
public class PassBorrowApplyEntity extends BaseEntity {
	
	private static final long serialVersionUID = 30538830025533192L;
		
	/** 借车单号 */
	private String borrowNo;
		
	/** 受理状态 */
	private String passStatus;
	
	/** 车牌号 */
	private String vehicleNo;
		
	/** 受理时间 */
	private Date passTime;
		
	/** 受理部门编码 */
	private String acceptOrgName;
	
	/** 受理部门名称 */
	private String acceptOrgCode;
	
	/** 受理人名称 */
	private String acceptEmpName;
	
	/** 受理人编码 */
	private String acceptEmpCode;
		
	/**
	 * 获得borrowNo
	 * @return the borrowNo
	 */
	public String getBorrowNo() {
		return borrowNo;
	}
	
	/**
	 * 获得passStatus
	 * @return the passStatus
	 */
	public String getPassStatus() {
		return passStatus;
	}
	
	/**
	 * 获得vehicleNo
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * 获得passTime
	 * @return the passTime
	 */
	public Date getPassTime() {
		return passTime;
	}
	
	/**
	 * 获得acceptOrgName
	 * @return the acceptOrgName
	 */
	public String getAcceptOrgName() {
		return acceptOrgName;
	}
	
	/**
	 * 获得acceptOrgCode
	 * @return the acceptOrgCode
	 */
	public String getAcceptOrgCode() {
		return acceptOrgCode;
	}
	
	/**
	 * 获得acceptEmpName
	 * @return the acceptEmpName
	 */
	public String getAcceptEmpName() {
		return acceptEmpName;
	}
	
	/**
	 * 获得acceptEmpCode
	 * @return the acceptEmpCode
	 */
	public String getAcceptEmpCode() {
		return acceptEmpCode;
	}
	
		/**
	 * 设置borrowNo
	 * @param borrowNo the borrowNo to set
	 */
	public void setBorrowNo(String borrowNo) {
		this.borrowNo = borrowNo;
	}
	
	/**
	 * 设置passStatus
	 * @param passStatus the passStatus to set
	 */
	public void setPassStatus(String passStatus) {
		this.passStatus = passStatus;
	}
	
	/**
	 * 设置vehicleNo
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * 设置passTime
	 * @param passTime the passTime to set
	 */
	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}
	
	/**
	 * 设置acceptOrgName
	 * @param acceptOrgName the acceptOrgName to set
	 */
	public void setAcceptOrgName(String acceptOrgName) {
		this.acceptOrgName = acceptOrgName;
	}
	
	/**
	 * 设置acceptOrgCode
	 * @param acceptOrgCode the acceptOrgCode to set
	 */
	public void setAcceptOrgCode(String acceptOrgCode) {
		this.acceptOrgCode = acceptOrgCode;
	}
	
	/**
	 * 设置acceptEmpName
	 * @param acceptEmpName the acceptEmpName to set
	 */
	public void setAcceptEmpName(String acceptEmpName) {
		this.acceptEmpName = acceptEmpName;
	}
	
	/**
	 * 设置acceptEmpCode
	 * @param acceptEmpCode the acceptEmpCode to set
	 */
	public void setAcceptEmpCode(String acceptEmpCode) {
		this.acceptEmpCode = acceptEmpCode;
	}

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[borrowNo,").append(borrowNo).append("]");
		stringBuilder.append("[passStatus,").append(passStatus).append("]");
		stringBuilder.append("[vehicleNo,").append(vehicleNo).append("]");
		stringBuilder.append("[passTime,").append(passTime).append("]");
		stringBuilder.append("[acceptOrgName,").append(acceptOrgName).append("]");
		stringBuilder.append("[acceptOrgCode,").append(acceptOrgCode).append("]");
		stringBuilder.append("[acceptEmpName,").append(acceptEmpName).append("]");
		stringBuilder.append("[acceptEmpCode,").append(acceptEmpCode).append("]");
		return stringBuilder.toString();
	}
}
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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/PassOrderApplyEntity.java
 * 
 *  FILE NAME     :PassOrderApplyEntity.java
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
 * 受理约车实体
 * @author 104306-foss-wangLong
 * @date 2012-11-21 下午3:13:16
 */
public class PassOrderApplyEntity extends BaseEntity {
	
	private static final long serialVersionUID = 26561982329112612L;
		
	/** 约车编号 */
	private String orderNo;
	
	/** 预计到达时间 */
	private Date perdictArriveTime;
	
	/** 是否生成放行单 */
	private String ifNeedReleaseBill;
		
	/** 通过状态 */
	private String passStatus;
		
	/** 通过时间 */
	private Date passTime;
		
	/** 车牌号 */
	private String vehicleNo;
		
	/** 司机姓名 */
	private String driverName;
	
	/** 司机联系电话 */
	private String driverPhone;
		
	/** 受理车队名称  */
	private String acceptOrgName;
		
	/** 受理车队编码  */
	private String acceptOrgCode;
		
	/** 受理人员名称 */
	private String acceptEmpName;
		
	/** 受理人员编码 */
	private String acceptEmpCode;
	
	/** 放行任务主键id */
	private String truckDepartId;
	
	/** 司机编码 */
	private String driverCode;
	
	/**
	 * 获得orderNo
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}
	
	/**
	 * 获得perdictArriveTime
	 * @return the perdictArriveTime
	 */
	public Date getPerdictArriveTime() {
		return perdictArriveTime;
	}
	
	/**
	 * 获得ifNeedReleaseBill
	 * @return the ifNeedReleaseBill
	 */
	public String getIfNeedReleaseBill() {
		return ifNeedReleaseBill;
	}
	
	/**
	 * 获得passStatus
	 * @return the passStatus
	 */
	public String getPassStatus() {
		return passStatus;
	}
	
	/**
	 * 获得passTime
	 * @return the passTime
	 */
	public Date getPassTime() {
		return passTime;
	}
	
	/**
	 * 获得vehicleNo
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * 获得driverName
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
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
	 * 获得truckDepartId
	 * @return the truckDepartId
	 */
	public String getTruckDepartId() {
		return truckDepartId;
	}

	/**
	 * 设置orderNo
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	/**
	 * 设置perdictArriveTime
	 * @param perdictArriveTime the perdictArriveTime to set
	 */
	public void setPerdictArriveTime(Date perdictArriveTime) {
		this.perdictArriveTime = perdictArriveTime;
	}
	
	/**
	 * 设置ifNeedReleaseBill
	 * @param ifNeedReleaseBill the ifNeedReleaseBill to set
	 */
	public void setIfNeedReleaseBill(String ifNeedReleaseBill) {
		this.ifNeedReleaseBill = ifNeedReleaseBill;
	}
	
	/**
	 * 设置passStatus
	 * @param passStatus the passStatus to set
	 */
	public void setPassStatus(String passStatus) {
		this.passStatus = passStatus;
	}
	
	/**
	 * 设置passTime
	 * @param passTime the passTime to set
	 */
	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}
	
	/**
	 * 设置vehicleNo
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * 设置driverName
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
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
	
	/**
	 * 设置truckDepartId
	 * @param truckDepartId the truckDepartId to set
	 */
	public void setTruckDepartId(String truckDepartId) {
		this.truckDepartId = truckDepartId;
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

	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[orderNo,").append(orderNo).append("]");
		stringBuilder.append("[perdictArriveTime,").append(perdictArriveTime).append("]");
		stringBuilder.append("[ifNeedReleaseBill,").append(ifNeedReleaseBill).append("]");
		stringBuilder.append("[passStatus,").append(passStatus).append("]");
		stringBuilder.append("[passTime,").append(passTime).append("]");
		stringBuilder.append("[vehicleNo,").append(vehicleNo).append("]");
		stringBuilder.append("[driverName,").append(driverName).append("]");
		stringBuilder.append("[acceptOrgName,").append(acceptOrgName).append("]");
		stringBuilder.append("[acceptOrgCode,").append(acceptOrgCode).append("]");
		stringBuilder.append("[acceptEmpName,").append(acceptEmpName).append("]");
		stringBuilder.append("[acceptEmpCode,").append(acceptEmpCode).append("]");
		stringBuilder.append("[truckDepartId,").append(truckDepartId).append("]");
		stringBuilder.append("[driverPhone,").append(driverPhone).append("]");
		stringBuilder.append("[driverCode,").append(driverCode).append("]");
		return stringBuilder.toString();
	}
}
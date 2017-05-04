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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/domain/PassInviteApplyEntity.java
 * 
 *  FILE NAME     :PassInviteApplyEntity.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.domain
 * FILE    NAME: PassInviteApplyEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.scheduling.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * PassInviteApplyEntity
 * @author 104306-foss-wangLong
 * @date 2012-12-15 下午12:54:39
 */
public class PassInviteApplyEntity extends BaseEntity {
	
	private static final long serialVersionUID = 62919598059406744L;
		
	/** 外请车编号 */
	private String inviteNo;
	
	/** 预计到达时间 */
	private Date perdictArriveTime;
	
	/** 请车价格 */
	private BigDecimal inviteCost;
	
	/** 外请车编号 */
	private String vehicleNo;
	
	/** 受理车队名称 */
	private String acceptOrgName;
	
	/** 受理车队编码 */
	private String acceptOrgCode;
	
	/** 受理人员名称 */
	private String acceptEmpName;
	
	/** 受理人员编码 */
	private String acceptEmpCode;
	
	/** 通过状态*/
	private String passStatus;
	
	/** 通过时间 */
	private Date passTime;
		
	/** 币种 */
	private String currencyCode;
	
	/** 使用状态 */
	private String useStatus;
	
	/**信息部名称*/
	private String ministryinformation;
	
	/**约车部门*/
	private String applyOrgCode;
	
	/**具体部门  310248*/
	private String applyPath;
		
	public String getApplyPath() {
		return applyPath;
	}

	public void setApplyPath(String applyPath) {
		this.applyPath = applyPath;
	}
	
	/** 信息部编码(tps系统编码)--310248*/
	private String ministryinformationCode;
		

	public String getMinistryinformationCode() {
		return ministryinformationCode;
	}

	public void setMinistryinformationCode(String ministryinformationCode) {
		this.ministryinformationCode = ministryinformationCode;
	}
	public String getApplyOrgCode() {
		return applyOrgCode;
	}

	public void setApplyOrgCode(String applyOrgCode) {
		this.applyOrgCode = applyOrgCode;
	}

	/**
	 * 获得inviteNo
	 * @return the inviteNo
	 */
	public String getInviteNo() {
		return inviteNo;
	}
	
	/**
	 * 获得perdictArriveTime
	 * @return the perdictArriveTime
	 */
	public Date getPerdictArriveTime() {
		return perdictArriveTime;
	}
	
	/**
	 * 获得inviteCost
	 * @return the inviteCost
	 */
	public BigDecimal getInviteCost() {
		return inviteCost;
	}
	
	/**
	 * 获得vehicleNo
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
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
	 * 获得currencyCode
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	
		/**
	 * 设置inviteNo
	 * @param inviteNo the inviteNo to set
	 */
	public void setInviteNo(String inviteNo) {
		this.inviteNo = inviteNo;
	}
	
	/**
	 * 设置perdictArriveTime
	 * @param perdictArriveTime the perdictArriveTime to set
	 */
	public void setPerdictArriveTime(Date perdictArriveTime) {
		this.perdictArriveTime = perdictArriveTime;
	}
	
	/**
	 * 设置inviteCost
	 * @param inviteCost the inviteCost to set
	 */
	public void setInviteCost(BigDecimal inviteCost) {
		this.inviteCost = inviteCost;
	}
	
	/**
	 * 设置vehicleNo
	 * @param vehicleNo the vehicleNo to set
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
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
	 * 设置currencyCode
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
		
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[inviteNo,").append(inviteNo).append("]");
		stringBuilder.append("[perdictArriveTime,").append(perdictArriveTime).append("]");
		stringBuilder.append("[inviteCost,").append(inviteCost).append("]");
		stringBuilder.append("[vehicleNo,").append(vehicleNo).append("]");
		stringBuilder.append("[acceptOrgName,").append(acceptOrgName).append("]");
		stringBuilder.append("[acceptOrgCode,").append(acceptOrgCode).append("]");
		stringBuilder.append("[acceptEmpName,").append(acceptEmpName).append("]");
		stringBuilder.append("[acceptEmpCode,").append(acceptEmpCode).append("]");
		stringBuilder.append("[passStatus,").append(passStatus).append("]");
		stringBuilder.append("[passTime,").append(passTime).append("]");
		stringBuilder.append("[currencyCode,").append(currencyCode).append("]");
		return stringBuilder.toString();
	}

	/**
	 * @return the useStatus
	 */
	public String getUseStatus() {
		return useStatus;
	}

	/**
	 * @param useStatus the useStatus to set
	 */
	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	public String getMinistryinformation() {
		return ministryinformation;
	}

	public void setMinistryinformation(String ministryinformation) {
		this.ministryinformation = ministryinformation;
	}
}
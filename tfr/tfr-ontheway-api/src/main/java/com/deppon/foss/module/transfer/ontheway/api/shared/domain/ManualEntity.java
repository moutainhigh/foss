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
 *  PROJECT NAME  : tfr-ontheway-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/ontheway/api/shared/domain/ManualEntity.java
 *  
 *  FILE NAME          :ManualEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.ontheway.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 在途新增entity
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-25 上午9:13:39
 */
public class ManualEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/********** 任务明细ID ************/
	private String truckTaskDetailId;
	
	/********** 车牌号 ************/
	private String vehicleNo;  		//车牌号
	
	/********** 当前路径 ************/
	private String currentPlace;	//当前路径
	
	/********** 计划到达时间************/
	private Date planArriveTime;		//计划到达时间
	
	/********** 状态 ************/
	private String currentStatus;		//状态
	
	/********** 跟踪时间************/
	private Date trackingTime;		//跟踪时间
	
	/********** 备注 ************/
	private String notes;	//备注
	
	/********** 跟踪类型***********/
	private String trackingType;	//跟踪类型
	
	/********** 跟踪人 ************/
	private String trackingUserCode;	//跟踪人
	
	/********** 跟踪部门************/
	private String trackingOrgCode;	//跟踪部门
	
	/********** 是否最新 ************/
	private String isLatest;	//是否最新

	/**
	 * 获取 ******** 任务明细ID ***********.
	 *
	 * @return the ******** 任务明细ID ***********
	 */
	public String getTruckTaskDetailId()
	{
		return truckTaskDetailId;
	}

	/**
	 * 设置 ******** 任务明细ID ***********.
	 *
	 * @param truckTaskDetailId the new ******** 任务明细ID ***********
	 */
	public void setTruckTaskDetailId(String truckTaskDetailId)
	{
		this.truckTaskDetailId = truckTaskDetailId;
	}

	/**
	 * 获取 ******** 车牌号 ***********.
	 *
	 * @return the ******** 车牌号 ***********
	 */
	public String getVehicleNo()
	{
		return vehicleNo;
	}

	/**
	 * 设置 ******** 车牌号 ***********.
	 *
	 * @param vehicleNo the new ******** 车牌号 ***********
	 */
	public void setVehicleNo(String vehicleNo)
	{
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 ******** 当前路径 ***********.
	 *
	 * @return the ******** 当前路径 ***********
	 */
	public String getCurrentPlace()
	{
		return currentPlace;
	}

	/**
	 * 设置 ******** 当前路径 ***********.
	 *
	 * @param currentPlace the new ******** 当前路径 ***********
	 */
	public void setCurrentPlace(String currentPlace)
	{
		this.currentPlace = currentPlace;
	}

	/**
	 * 获取 ******** 计划到达时间***********.
	 *
	 * @return the ******** 计划到达时间***********
	 */
	public Date getPlanArriveTime()
	{
		return planArriveTime;
	}

	/**
	 * 设置 ******** 计划到达时间***********.
	 *
	 * @param planArriveTime the new ******** 计划到达时间***********
	 */
	public void setPlanArriveTime(Date planArriveTime)
	{
		this.planArriveTime = planArriveTime;
	}

	/**
	 * 获取 ******** 状态 ***********.
	 *
	 * @return the ******** 状态 ***********
	 */
	public String getCurrentStatus()
	{
		return currentStatus;
	}

	/**
	 * 设置 ******** 状态 ***********.
	 *
	 * @param currentStatus the new ******** 状态 ***********
	 */
	public void setCurrentStatus(String currentStatus)
	{
		this.currentStatus = currentStatus;
	}

	/**
	 * 获取 ******** 跟踪时间***********.
	 *
	 * @return the ******** 跟踪时间***********
	 */
	public Date getTrackingTime()
	{
		return trackingTime;
	}

	/**
	 * 设置 ******** 跟踪时间***********.
	 *
	 * @param trackingTime the new ******** 跟踪时间***********
	 */
	public void setTrackingTime(Date trackingTime)
	{
		this.trackingTime = trackingTime;
	}

	/**
	 * 获取 ******** 备注 ***********.
	 *
	 * @return the ******** 备注 ***********
	 */
	public String getNotes()
	{
		return notes;
	}

	/**
	 * 设置 ******** 备注 ***********.
	 *
	 * @param notes the new ******** 备注 ***********
	 */
	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	/**
	 * 获取 ******** 跟踪类型**********.
	 *
	 * @return the ******** 跟踪类型**********
	 */
	public String getTrackingType()
	{
		return trackingType;
	}

	/**
	 * 设置 ******** 跟踪类型**********.
	 *
	 * @param trackingType the new ******** 跟踪类型**********
	 */
	public void setTrackingType(String trackingType)
	{
		this.trackingType = trackingType;
	}

	/**
	 * 获取 ******** 跟踪人 ***********.
	 *
	 * @return the ******** 跟踪人 ***********
	 */
	public String getTrackingUserCode()
	{
		return trackingUserCode;
	}

	/**
	 * 设置 ******** 跟踪人 ***********.
	 *
	 * @param trackingUserCode the new ******** 跟踪人 ***********
	 */
	public void setTrackingUserCode(String trackingUserCode)
	{
		this.trackingUserCode = trackingUserCode;
	}

	/**
	 * 获取 ******** 跟踪部门***********.
	 *
	 * @return the ******** 跟踪部门***********
	 */
	public String getTrackingOrgCode()
	{
		return trackingOrgCode;
	}

	/**
	 * 设置 ******** 跟踪部门***********.
	 *
	 * @param trackingOrgCode the new ******** 跟踪部门***********
	 */
	public void setTrackingOrgCode(String trackingOrgCode)
	{
		this.trackingOrgCode = trackingOrgCode;
	}

	/**
	 * 获取 ******** 是否最新 ***********.
	 *
	 * @return the ******** 是否最新 ***********
	 */
	public String getIsLatest()
	{
		return isLatest;
	}

	/**
	 * 设置 ******** 是否最新 ***********.
	 *
	 * @param isLatest the new ******** 是否最新 ***********
	 */
	public void setIsLatest(String isLatest)
	{
		this.isLatest = isLatest;
	}
	
	
	
}
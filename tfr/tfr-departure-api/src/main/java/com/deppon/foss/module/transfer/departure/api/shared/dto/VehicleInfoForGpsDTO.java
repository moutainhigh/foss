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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/dto/VehicleInfoForGpsDTO.java
 *  
 *  FILE NAME          :VehicleInfoForGpsDTO.java
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

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
public class VehicleInfoForGpsDTO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/*************车牌号***************/
	private String vehicleNo;        
	
	/*************计划到达时间***************/
	private Date planArriveTime;     
	
	/*************出发部门***************/
	private String origOrgCode;      
	
	/*************到达部门***************/
	private String destOrgCode;		 
	
	/*************任务车辆明细ID***************/
	private String truckTaskDetailId;
	
	/*************任务车辆ID***************/
	private String truckTaskId;

	/*************是否整车***************/
	private String beCarLoad;
	
	/*************车辆任务明细状态***************/
	private String status;
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
	 * 获取 ***********计划到达时间**************.
	 *
	 * @return the ***********计划到达时间**************
	 */
	public Date getPlanArriveTime()
	{
		return planArriveTime;
	}

	/**
	 * 设置 ***********计划到达时间**************.
	 *
	 * @param planArriveTime the new ***********计划到达时间**************
	 */
	public void setPlanArriveTime(Date planArriveTime)
	{
		this.planArriveTime = planArriveTime;
	}

	/**
	 * 获取 ***********出发部门**************.
	 *
	 * @return the ***********出发部门**************
	 */
	public String getOrigOrgCode()
	{
		return origOrgCode;
	}

	/**
	 * 设置 ***********出发部门**************.
	 *
	 * @param origOrgCode the new ***********出发部门**************
	 */
	public void setOrigOrgCode(String origOrgCode)
	{
		this.origOrgCode = origOrgCode;
	}

	/**
	 * 获取 ***********到达部门**************.
	 *
	 * @return the ***********到达部门**************
	 */
	public String getDestOrgCode()
	{
		return destOrgCode;
	}

	/**
	 * 设置 ***********到达部门**************.
	 *
	 * @param destOrgCode the new ***********到达部门**************
	 */
	public void setDestOrgCode(String destOrgCode)
	{
		this.destOrgCode = destOrgCode;
	}

	/**
	 * 获取 ***********任务车辆明细ID**************.
	 *
	 * @return the ***********任务车辆明细ID**************
	 */
	public String getTruckTaskDetailId()
	{
		return truckTaskDetailId;
	}

	/**
	 * 设置 ***********任务车辆明细ID**************.
	 *
	 * @param truckTaskDetailId the new ***********任务车辆明细ID**************
	 */
	public void setTruckTaskDetailId(String truckTaskDetailId)
	{
		this.truckTaskDetailId = truckTaskDetailId;
	}

	public String getBeCarLoad() {
		return beCarLoad;
	}

	public void setBeCarLoad(String beCarLoad) {
		this.beCarLoad = beCarLoad;
	}

	public String getTruckTaskId() {
		return truckTaskId;
	}

	public void setTruckTaskId(String truckTaskId) {
		this.truckTaskId = truckTaskId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
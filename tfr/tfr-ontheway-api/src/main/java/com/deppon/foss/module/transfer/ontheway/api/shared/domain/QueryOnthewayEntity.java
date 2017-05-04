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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/ontheway/api/shared/domain/QueryOnthewayEntity.java
 *  
 *  FILE NAME          :QueryOnthewayEntity.java
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
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

// TODO: Auto-generated Javadoc
/**
 * Entity开发规范 1.必须继承com.deppon.foss.framework.entity.BaseEntity 2.类名必须以Entity结尾
 * 3.必须生成serialVersionUID 4.建议属性名称与数据库字段命名规则一致
 */
public class QueryOnthewayEntity extends BaseEntity
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3703272448562684594L;

	/** ******** 车牌号 ***********. */
	private String vehicleNo;
	
	/** ******** 起始时间 ***********. */
	private Date startTime;
	
	/** ******** 结束时间 ***********. */
	private Date endTime;
	
	/** ******** 出发起始时间 ***********. */
	private Date departStartTime;
	
	/** ******** 出发结束时间 ***********. */
	private Date departEndTime;
	
	/** ******** 车辆归属部门 ***********. */
	private String truckOrgCode;
	
	/** ******** 车辆业务类型 ***********. */
	private String businessType;
	
	/** ******** 出发部门 ***********. */
	private String origOrgCode;
	
	/** ******** 到达部门 ***********. */
	private String destOrgCode;
	
	/** ******** 车辆归属类型 ***********. */
	private String truckType;
	
	/** ******** 跟踪方式 ***********. */
	private String trackingType;
	
	/** ******** 当前状态***********. */
	private String currentStatus;
	
	/** ******** 当前状态***********. */
	private List<String> currentStatuslist;
	
	/** ******** 事故车辆***********. */
	private String accidents;
	
	/** ******** 车辆状态***********. */
	private String vehicleStatus;
	
	/** ******** 运行状态***********. */
	private String runStatus;
	
	/** ******** 预计到达状态***********. */
	private String planArrivalStatus;
	
	private List<String> ids;
	
	/** *******过滤类型***********. */
	private String filterCondition;
	private List<String> currentOrgCodes;
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
	 * 获取 ******** 起始时间 ***********.
	 *
	 * @return the ******** 起始时间 ***********
	 */
	public Date getStartTime()
	{
		return startTime;
	}
	
	/**
	 * 设置 ******** 起始时间 ***********.
	 *
	 * @param startTime the new ******** 起始时间 ***********
	 */
	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}
	
	/**
	 * 获取 ******** 结束时间 ***********.
	 *
	 * @return the ******** 结束时间 ***********
	 */
	public Date getEndTime()
	{
		return endTime;
	}
	
	/**
	 * 设置 ******** 结束时间 ***********.
	 *
	 * @param endTime the new ******** 结束时间 ***********
	 */
	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}
	
	/**
	 * 获取 ******** 车辆归属部门 ***********.
	 *
	 * @return the ******** 车辆归属部门 ***********
	 */
	public String getTruckOrgCode()
	{
		return truckOrgCode;
	}
	
	/**
	 * 设置 ******** 车辆归属部门 ***********.
	 *
	 * @param truckOrgCode the new ******** 车辆归属部门 ***********
	 */
	public void setTruckOrgCode(String truckOrgCode)
	{
		this.truckOrgCode = truckOrgCode;
	}
	
	/**
	 * 获取 ******** 车辆业务类型 ***********.
	 *
	 * @return the ******** 车辆业务类型 ***********
	 */
	public String getBusinessType()
	{
		return businessType;
	}
	
	/**
	 * 设置 ******** 车辆业务类型 ***********.
	 *
	 * @param businessType the new ******** 车辆业务类型 ***********
	 */
	public void setBusinessType(String businessType)
	{
		this.businessType = businessType;
	}
	
	/**
	 * 获取 ******** 出发部门 ***********.
	 *
	 * @return the ******** 出发部门 ***********
	 */
	public String getOrigOrgCode()
	{
		return origOrgCode;
	}
	
	/**
	 * 设置 ******** 出发部门 ***********.
	 *
	 * @param origOrgCode the new ******** 出发部门 ***********
	 */
	public void setOrigOrgCode(String origOrgCode)
	{
		this.origOrgCode = origOrgCode;
	}
	
	/**
	 * 获取 ******** 到达部门 ***********.
	 *
	 * @return the ******** 到达部门 ***********
	 */
	public String getDestOrgCode()
	{
		return destOrgCode;
	}
	
	/**
	 * 设置 ******** 到达部门 ***********.
	 *
	 * @param destOrgCode the new ******** 到达部门 ***********
	 */
	public void setDestOrgCode(String destOrgCode)
	{
		this.destOrgCode = destOrgCode;
	}
	
	/**
	 * 获取 ******** 车辆归属类型 ***********.
	 *
	 * @return the ******** 车辆归属类型 ***********
	 */
	public String getTruckType()
	{
		return truckType;
	}
	
	/**
	 * 设置 ******** 车辆归属类型 ***********.
	 *
	 * @param truckType the new ******** 车辆归属类型 ***********
	 */
	public void setTruckType(String truckType)
	{
		this.truckType = truckType;
	}
	
	/**
	 * 获取 ******** 跟踪方式 ***********.
	 *
	 * @return the ******** 跟踪方式 ***********
	 */
	public String getTrackingType()
	{
		return trackingType;
	}
	
	/**
	 * 设置 ******** 跟踪方式 ***********.
	 *
	 * @param trackingType the new ******** 跟踪方式 ***********
	 */
	public void setTrackingType(String trackingType)
	{
		this.trackingType = trackingType;
	}
	
	/**
	 * 获取 ******** 当前状态***********.
	 *
	 * @return the ******** 当前状态***********
	 */
	public String getCurrentStatus()
	{
		return currentStatus;
	}
	
	/**
	 * 设置 ******** 当前状态***********.
	 *
	 * @param currentStatus the new ******** 当前状态***********
	 */
	public void setCurrentStatus(String currentStatus)
	{
		this.currentStatus = currentStatus;
	}
	
	/**
	 * 获取 ******** 事故车辆***********.
	 *
	 * @return the ******** 事故车辆***********
	 */
	public String getAccidents()
	{
		return accidents;
	}
	
	/**
	 * 设置 ******** 事故车辆***********.
	 *
	 * @param accidents the new ******** 事故车辆***********
	 */
	public void setAccidents(String accidents)
	{
		this.accidents = accidents;
	}

	/**
	 * 获取 ******** 出发起始时间 ***********.
	 *
	 * @return the ******** 出发起始时间 ***********
	 */
	public Date getDepartStartTime()
	{
		return departStartTime;
	}

	/**
	 * 设置 ******** 出发起始时间 ***********.
	 *
	 * @param departStartTime the new ******** 出发起始时间 ***********
	 */
	public void setDepartStartTime(Date departStartTime)
	{
		this.departStartTime = departStartTime;
	}

	/**
	 * 获取 ******** 出发结束时间 ***********.
	 *
	 * @return the ******** 出发结束时间 ***********
	 */
	public Date getDepartEndTime()
	{
		return departEndTime;
	}

	/**
	 * 设置 ******** 出发结束时间 ***********.
	 *
	 * @param departEndTime the new ******** 出发结束时间 ***********
	 */
	public void setDepartEndTime(Date departEndTime)
	{
		this.departEndTime = departEndTime;
	}

	/**
	 * 获取 ******** 车辆状态***********.
	 *
	 * @return the ******** 车辆状态***********
	 */
	public String getVehicleStatus()
	{
		return vehicleStatus;
	}

	/**
	 * 设置 ******** 车辆状态***********.
	 *
	 * @param vehicleStatus the new ******** 车辆状态***********
	 */
	public void setVehicleStatus(String vehicleStatus)
	{
		this.vehicleStatus = vehicleStatus;
	}

	/**
	 * Gets the current statuslist.
	 *
	 * @return the current statuslist
	 */
	public List<String> getCurrentStatuslist()
	{
		return currentStatuslist;
	}

	/**
	 * Sets the current statuslist.
	 *
	 * @param currentStatuslist the new current statuslist
	 */
	public void setCurrentStatuslist(List<String> currentStatuslist)
	{
		this.currentStatuslist = currentStatuslist;
	}

	/**
	 * Gets the filter condition.
	 *
	 * @return the filter condition
	 */
	public String getFilterCondition() {
		return filterCondition;
	}

	/**
	 * Sets the filter condition.
	 *
	 * @param filterCondition the new filter condition
	 */
	public void setFilterCondition(String filterCondition) {
		this.filterCondition = filterCondition;
	}

	public String getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}

	public List<String> getCurrentOrgCodes() {
		return currentOrgCodes;
	}

	public void setCurrentOrgCodes(List<String> currentOrgCodes) {
		this.currentOrgCodes = currentOrgCodes;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getPlanArrivalStatus() {
		return planArrivalStatus;
	}

	public void setPlanArrivalStatus(String planArrivalStatus) {
		this.planArrivalStatus = planArrivalStatus;
	}

}
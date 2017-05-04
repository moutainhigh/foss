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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/ontheway/api/shared/vo/OnthewayVO.java
 *  
 *  FILE NAME          :OnthewayVO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.ontheway.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.ontheway.api.shared.domain.ArrivalEntity;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.ManualEntity;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.OnthewayEntity;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.QueryOnthewayEntity;
import com.deppon.foss.module.transfer.ontheway.api.shared.domain.VehicleStatistics;
/**
 * 
 * 在途展示数据层
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-25 上午9:14:35
 */
public class OnthewayVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -293285666591619830L;

	/********* 到达列表数据 **********/
	List<OnthewayEntity> onthewayList = new ArrayList<OnthewayEntity>();
	
	/********* 任务明细列表 **********/
	List<OnthewayEntity> detailList = new ArrayList<OnthewayEntity>();

	/********* 查询条件 *************/
	QueryOnthewayEntity queryEntity = new QueryOnthewayEntity();
	
	/*********手动新增一条记录**********/
	ManualEntity manualEntity = new ManualEntity();
	
	/********* 在途的一些车辆信息 *************/
	VehicleStatistics vehicleStatistics = new VehicleStatistics();  
	
	ArrivalEntity  arrivalEntity = new ArrivalEntity();
	
	/********* GPS链接地址 *************/
	private String  gpsUrl;
	
	/********* 车牌号 *************/
	private String vehicleNo;
	
	/********* 实际出发时间 *************/
	private Date actualDepartTime;
	
	/********* 车辆任务明细ID *************/
	private String truckTaskDetailId;
	
	/********* 实际到达时间 *************/
	private Date planArriveTime;
	
	private String ids;

	/**
	 * 获取 ******* 到达列表数据 *********.
	 *
	 * @return the ******* 到达列表数据 *********
	 */
	public List<OnthewayEntity> getOnthewayList()
	{
		return onthewayList;
	}

	/**
	 * 设置 ******* 到达列表数据 *********.
	 *
	 * @param onthewayList the new ******* 到达列表数据 *********
	 */
	public void setOnthewayList(List<OnthewayEntity> onthewayList)
	{
		this.onthewayList = onthewayList;
	}

	/**
	 * 获取 ******* 查询条件 ************.
	 *
	 * @return the ******* 查询条件 ************
	 */
	public QueryOnthewayEntity getQueryEntity()
	{
		return queryEntity;
	}

	/**
	 * 设置 ******* 查询条件 ************.
	 *
	 * @param queryEntity the new ******* 查询条件 ************
	 */
	public void setQueryEntity(QueryOnthewayEntity queryEntity)
	{
		this.queryEntity = queryEntity;
	}

	/**
	 * 获取 *******手动新增一条记录*********.
	 *
	 * @return the *******手动新增一条记录*********
	 */
	public ManualEntity getManualEntity()
	{
		return manualEntity;
	}

	/**
	 * 设置 *******手动新增一条记录*********.
	 *
	 * @param manualEntity the new *******手动新增一条记录*********
	 */
	public void setManualEntity(ManualEntity manualEntity)
	{
		this.manualEntity = manualEntity;
	}

	/**
	 * 获取 ******* 在途的一些车辆信息 ************.
	 *
	 * @return the ******* 在途的一些车辆信息 ************
	 */
	public VehicleStatistics getVehicleStatistics()
	{
		return vehicleStatistics;
	}

	/**
	 * 设置 ******* 在途的一些车辆信息 ************.
	 *
	 * @param vehicleStatistics the new ******* 在途的一些车辆信息 ************
	 */
	public void setVehicleStatistics(VehicleStatistics vehicleStatistics)
	{
		this.vehicleStatistics = vehicleStatistics;
	}

	/**
	 * 获取 ******* GPS链接地址 ************.
	 *
	 * @return the ******* GPS链接地址 ************
	 */
	public String getGpsUrl()
	{
		return gpsUrl;
	}

	/**
	 * 设置 ******* GPS链接地址 ************.
	 *
	 * @param gpsUrl the new ******* GPS链接地址 ************
	 */
	public void setGpsUrl(String gpsUrl)
	{
		this.gpsUrl = gpsUrl;
	}

	/**
	 * 获取 ******* 车牌号 ************.
	 *
	 * @return the ******* 车牌号 ************
	 */
	public String getVehicleNo()
	{
		return vehicleNo;
	}

	/**
	 * 设置 ******* 车牌号 ************.
	 *
	 * @param vehicleNo the new ******* 车牌号 ************
	 */
	public void setVehicleNo(String vehicleNo)
	{
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 ******* 实际出发时间 ************.
	 *
	 * @return the ******* 实际出发时间 ************
	 */
	public Date getActualDepartTime()
	{
		return actualDepartTime;
	}

	/**
	 * 设置 ******* 实际出发时间 ************.
	 *
	 * @param actualDepartTime the new ******* 实际出发时间 ************
	 */
	public void setActualDepartTime(Date actualDepartTime)
	{
		this.actualDepartTime = actualDepartTime;
	}

	/**
	 * 获取 ******* 实际到达时间 ************.
	 *
	 * @return the ******* 实际到达时间 ************
	 */
	public Date getPlanArriveTime()
	{
		return planArriveTime;
	}

	/**
	 * 设置 ******* 实际到达时间 ************.
	 *
	 * @param planArriveTime the new ******* 实际到达时间 ************
	 */
	public void setPlanArriveTime(Date planArriveTime)
	{
		this.planArriveTime = planArriveTime;
	}

	/**
	 * 获取 ******* 车辆任务明细ID ************.
	 *
	 * @return the ******* 车辆任务明细ID ************
	 */
	public String getTruckTaskDetailId()
	{
		return truckTaskDetailId;
	}

	/**
	 * 设置 ******* 车辆任务明细ID ************.
	 *
	 * @param truckTaskDetailId the new ******* 车辆任务明细ID ************
	 */
	public void setTruckTaskDetailId(String truckTaskDetailId)
	{
		this.truckTaskDetailId = truckTaskDetailId;
	}

	/**
	 * 获取 ******* 任务明细列表 *********.
	 *
	 * @return the ******* 任务明细列表 *********
	 */
	public List<OnthewayEntity> getDetailList()
	{
		return detailList;
	}

	/**
	 * 设置 ******* 任务明细列表 *********.
	 *
	 * @param detailList the new ******* 任务明细列表 *********
	 */
	public void setDetailList(List<OnthewayEntity> detailList)
	{
		this.detailList = detailList;
	}

	public ArrivalEntity getArrivalEntity() {
		return arrivalEntity;
	}

	public void setArrivalEntity(ArrivalEntity arrivalEntity) {
		this.arrivalEntity = arrivalEntity;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}


	


	
}
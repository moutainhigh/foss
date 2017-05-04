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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/domain/OnTheWayInfoEntity.java
 *  
 *  FILE NAME          :OnTheWayInfoEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
public class OnTheWayInfoEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********车辆所处路段************/
	private String currentPlace;
	
	/**********跟踪时间************/
	private Date trackingTime;
	
	/**********预计到达时间************/
	private Date estimateDepartTime;
	
	/**********车辆当前状态************/
	private String curentStatus;

	/**
	 * 获取 ********车辆所处路段***********.
	 *
	 * @return the ********车辆所处路段***********
	 */
	public String getCurrentPlace(){
		return currentPlace;
	}

	/**
	 * 设置 ********车辆所处路段***********.
	 *
	 * @param currentPlace the new ********车辆所处路段***********
	 */
	public void setCurrentPlace(String currentPlace){
		this.currentPlace = currentPlace;
	}

	/**
	 * 获取 ********跟踪时间***********.
	 *
	 * @return the ********跟踪时间***********
	 */
	public Date getTrackingTime(){
		return trackingTime;
	}

	/**
	 * 设置 ********跟踪时间***********.
	 *
	 * @param trackingTime the new ********跟踪时间***********
	 */
	public void setTrackingTime(Date trackingTime){
		this.trackingTime = trackingTime;
	}

	/**
	 * 获取 ********预计到达时间***********.
	 *
	 * @return the ********预计到达时间***********
	 */
	public Date getEstimateDepartTime(){
		return estimateDepartTime;
	}

	/**
	 * 设置 ********预计到达时间***********.
	 *
	 * @param estimateDepartTime the new ********预计到达时间***********
	 */
	public void setEstimateDepartTime(Date estimateDepartTime){
		this.estimateDepartTime = estimateDepartTime;
	}

	/**
	 * 获取 ********车辆当前状态***********.
	 *
	 * @return the ********车辆当前状态***********
	 */
	public String getCurentStatus(){
		return curentStatus;
	}

	/**
	 * 设置 ********车辆当前状态***********.
	 *
	 * @param curentStatus the new ********车辆当前状态***********
	 */
	public void setCurentStatus(String curentStatus){
		this.curentStatus = curentStatus;
	}
	



	
}
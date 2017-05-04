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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/dto/GpsNotifyDTO.java
 *  
 *  FILE NAME          :GpsNotifyDTO.java
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

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * GPS轨迹DTO
 */
public class GpsNotifyDTO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********更新后的放行状态************/
	private String status;        //更新后的放行状态
	
	/**********gps放行时间************/
	private Date gpsDepartTime;   //gps放行时间
	
	/**********gps到达时间************/
	private Date gpsArriveTime;   //gps到达时间
	
	/**********gps传入ID************/
	private String gpsTaskId;     //gps传入ID
	
	/**********任务ID************/
	private String vehicleId;	  //任务ID
	
	/**********路段************/
	private String roadSegmengt;  //路段
	
	/**********状态************/
	private String state;         //状态
	
	/**********速度************/
	private BigDecimal velocity;  //速度
	
	/**********预计到达时间************/
	private Date preArrivalDate;  //预计到达时间
	
	/**********跟踪方式0: GPS跟踪1:人工跟踪***********/
	private String trackingType;  //跟踪方式0: GPS跟踪1:人工跟踪
	
	/**********跟踪时间************/
	private Date trackingTime;    //跟踪时间
	
	/**********任务车辆明细ID************/
	private String truckTaskDetailId;//任务车辆明细ID
	
	/**********车牌号************/
	private String vehicleNo;	  //车牌号
	
	/**********是否最新的记录************/
	private String isLatest;      //是否最新的记录

	/**
	 * 获取 ********更新后的放行状态***********.
	 *
	 * @return the ********更新后的放行状态***********
	 */
	public String getStatus(){
		return status;
	}

	/**
	 * 设置 ********更新后的放行状态***********.
	 *
	 * @param status the new ********更新后的放行状态***********
	 */
	public void setStatus(String status){
		this.status = status;
	}

	/**
	 * 获取 ********gps放行时间***********.
	 *
	 * @return the ********gps放行时间***********
	 */
	public Date getGpsDepartTime(){
		return gpsDepartTime;
	}

	/**
	 * 设置 ********gps放行时间***********.
	 *
	 * @param gpsDepartTime the new ********gps放行时间***********
	 */
	public void setGpsDepartTime(Date gpsDepartTime){
		this.gpsDepartTime = gpsDepartTime;
	}

	/**
	 * 获取 ********gps到达时间***********.
	 *
	 * @return the ********gps到达时间***********
	 */
	public Date getGpsArriveTime(){
		return gpsArriveTime;
	}

	/**
	 * 设置 ********gps到达时间***********.
	 *
	 * @param gpsArriveTime the new ********gps到达时间***********
	 */
	public void setGpsArriveTime(Date gpsArriveTime){
		this.gpsArriveTime = gpsArriveTime;
	}

	/**
	 * 获取 ********gps传入ID***********.
	 *
	 * @return the ********gps传入ID***********
	 */
	public String getGpsTaskId(){
		return gpsTaskId;
	}

	/**
	 * 设置 ********gps传入ID***********.
	 *
	 * @param gpsTaskId the new ********gps传入ID***********
	 */
	public void setGpsTaskId(String gpsTaskId){
		this.gpsTaskId = gpsTaskId;
	}

	/**
	 * 获取 ********任务ID***********.
	 *
	 * @return the ********任务ID***********
	 */
	public String getVehicleId(){
		return vehicleId;
	}

	/**
	 * 设置 ********任务ID***********.
	 *
	 * @param vehicleId the new ********任务ID***********
	 */
	public void setVehicleId(String vehicleId){
		this.vehicleId = vehicleId;
	}

	/**
	 * 获取 ********路段***********.
	 *
	 * @return the ********路段***********
	 */
	public String getRoadSegmengt(){
		return roadSegmengt;
	}

	/**
	 * 设置 ********路段***********.
	 *
	 * @param roadSegmengt the new ********路段***********
	 */
	public void setRoadSegmengt(String roadSegmengt){
		this.roadSegmengt = roadSegmengt;
	}

	/**
	 * 获取 ********状态***********.
	 *
	 * @return the ********状态***********
	 */
	public String getState(){
		return state;
	}

	/**
	 * 设置 ********状态***********.
	 *
	 * @param state the new ********状态***********
	 */
	public void setState(String state){
		this.state = state;
	}

	/**
	 * 获取 ********速度***********.
	 *
	 * @return the ********速度***********
	 */
	public BigDecimal getVelocity(){
		return velocity;
	}

	/**
	 * 设置 ********速度***********.
	 *
	 * @param velocity the new ********速度***********
	 */
	public void setVelocity(BigDecimal velocity){
		this.velocity = velocity;
	}

	/**
	 * 获取 ********预计到达时间***********.
	 *
	 * @return the ********预计到达时间***********
	 */
	public Date getPreArrivalDate(){
		return preArrivalDate;
	}

	/**
	 * 设置 ********预计到达时间***********.
	 *
	 * @param preArrivalDate the new ********预计到达时间***********
	 */
	public void setPreArrivalDate(Date preArrivalDate){
		this.preArrivalDate = preArrivalDate;
	}

	/**
	 * 获取 ********跟踪方式0: GPS跟踪1:人工跟踪**********.
	 *
	 * @return the ********跟踪方式0: GPS跟踪1:人工跟踪**********
	 */
	public String getTrackingType(){
		return trackingType;
	}

	/**
	 * 设置 ********跟踪方式0: GPS跟踪1:人工跟踪**********.
	 *
	 * @param trackingType the new ********跟踪方式0: GPS跟踪1:人工跟踪**********
	 */
	public void setTrackingType(String trackingType){
		this.trackingType = trackingType;
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
	 * 获取 ********任务车辆明细ID***********.
	 *
	 * @return the ********任务车辆明细ID***********
	 */
	public String getTruckTaskDetailId(){
		return truckTaskDetailId;
	}

	/**
	 * 设置 ********任务车辆明细ID***********.
	 *
	 * @param truckTaskDetailId the new ********任务车辆明细ID***********
	 */
	public void setTruckTaskDetailId(String truckTaskDetailId){
		this.truckTaskDetailId = truckTaskDetailId;
	}

	/**
	 * 获取 ********车牌号***********.
	 *
	 * @return the ********车牌号***********
	 */
	public String getVehicleNo(){
		return vehicleNo;
	}

	/**
	 * 设置 ********车牌号***********.
	 *
	 * @param vehicleNo the new ********车牌号***********
	 */
	public void setVehicleNo(String vehicleNo){
		this.vehicleNo = vehicleNo;
	}

	/**
	 * 获取 ********是否最新的记录***********.
	 *
	 * @return the ********是否最新的记录***********
	 */
	public String getIsLatest(){
		return isLatest;
	}

	/**
	 * 设置 ********是否最新的记录***********.
	 *
	 * @param isLatest the new ********是否最新的记录***********
	 */
	public void setIsLatest(String isLatest){
		this.isLatest = isLatest;
	}
	
	
}
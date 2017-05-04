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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirTrackFlightDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTrackFlightEntity;
/**
 * 航空跟踪DTO
 * @author 038300-foss-pengzhen
 * @date 2012-12-17 下午7:14:44
 */
public class AirTrackFlightDto extends AirTrackFlightEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2824202671228622611L;
	/**
	 * 航空公司二字码
	 */
	private String airLineTwoletter;
	/**
	 * 正单号
	 */
	private String airWaybillNo;
	/**
	 * 航班号
	 */
	private String flightNo;
	/**
	 * 航班日期
	 */
	private Date flightDate; 	
	/**
	 * 配载部门编号
	 */
	private String airAssembleDeptCode;
	/**
	 * 配载部门名称
	 */
	private String airAssembleDeptName;
	/**
	 * 目的站编码
	 */
	private String arrvRegionCode;
	/**
	 * 目的站名称
	 */
	private String arrvRegionName;	
	
	/**
	 * 起飞时间
	 */
	private Date takeOffTime;
	/**
	 * 到达时间
	 */
	private Date arriveTime;
	/**
	 * 实飞时间
	 */
	private Date actualTakeOffTime;
	/**
	 * 实到时间
	 */
	private Date actualArriveTime;
	/**
	 * 
	 * 跟踪状态
	 */
	private String trackState;
	/**
	 * 跟踪信息内容
	 */
	private String message;
    /**
     * 跟踪信息列表
     */
    private List<AirTrackFlightEntity> airTrackFlightList;
    /**
     * 航空正单id列表
     */
    private String[] airWaybillIds;
	
	/**
	 * 获取 航空公司二字码.
	 *
	 * @return the 航空公司二字码
	 */
	public String getAirLineTwoletter() {
		return airLineTwoletter;
	}
	
	/**
	 * 设置 航空公司二字码.
	 *
	 * @param airLineTwoletter the new 航空公司二字码
	 */
	public void setAirLineTwoletter(String airLineTwoletter) {
		this.airLineTwoletter = airLineTwoletter;
	}
	
	/**
	 * 获取 正单号.
	 *
	 * @return the 正单号
	 */
	public String getAirWaybillNo() {
		return airWaybillNo;
	}
	
	/**
	 * 设置 正单号.
	 *
	 * @param airWaybillNo the new 正单号
	 */
	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}
	
	/**
	 * 获取 航班号.
	 *
	 * @return the 航班号
	 */
	public String getFlightNo() {
		return flightNo;
	}
	
	/**
	 * 设置 航班号.
	 *
	 * @param flightNo the new 航班号
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	
	/**
	 * 获取 航班日期.
	 *
	 * @return the 航班日期
	 */
	public Date getFlightDate() {
		return flightDate;
	}
	
	/**
	 * 设置 航班日期.
	 *
	 * @param flightDate the new 航班日期
	 */
	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}
	
	/**
	 * 获取 目的站编码.
	 *
	 * @return the 目的站编码
	 */
	public String getArrvRegionCode() {
		return arrvRegionCode;
	}
	
	/**
	 * 设置 目的站编码.
	 *
	 * @param arrvRegionCode the new 目的站编码
	 */
	public void setArrvRegionCode(String arrvRegionCode) {
		this.arrvRegionCode = arrvRegionCode;
	}
	
	/**
	 * 获取 目的站名称.
	 *
	 * @return the 目的站名称
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}
	
	/**
	 * 设置 目的站名称.
	 *
	 * @param arrvRegionName the new 目的站名称
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}
	
	/**
	 * 获取 实飞时间.
	 *
	 * @return the 实飞时间
	 */
	@DateFormat
	public Date getActualTakeOffTime() {
		return actualTakeOffTime;
	}
	
	/**
	 * 设置 实飞时间.
	 *
	 * @param actualTakeOffTime the new 实飞时间
	 */
	@DateFormat
	public void setActualTakeOffTime(Date actualTakeOffTime) {
		this.actualTakeOffTime = actualTakeOffTime;
	}
	
	/**
	 * 获取 实到时间.
	 *
	 * @return the 实到时间
	 */
	@DateFormat
	public Date getActualArriveTime() {
		return actualArriveTime;
	}
	
	/**
	 * 设置 实到时间.
	 *
	 * @param actualArriveTime the new 实到时间
	 */
	@DateFormat
	public void setActualArriveTime(Date actualArriveTime) {
		this.actualArriveTime = actualArriveTime;
	}
	
	/**
	 * 获取 跟踪状态.
	 *
	 * @return the 跟踪状态
	 */
	public String getTrackState() {
		return trackState;
	}
	
	/**
	 * 设置 跟踪状态.
	 *
	 * @param trackState the new 跟踪状态
	 */
	public void setTrackState(String trackState) {
		this.trackState = trackState;
	}
	
	/**
	 * 获取 配载部门编号.
	 *
	 * @return the 配载部门编号
	 */
	public String getAirAssembleDeptCode() {
		return airAssembleDeptCode;
	}
	
	/**
	 * 设置 配载部门编号.
	 *
	 * @param airAssembleDeptCode the new 配载部门编号
	 */
	public void setAirAssembleDeptCode(String airAssembleDeptCode) {
		this.airAssembleDeptCode = airAssembleDeptCode;
	}
	
	/**
	 * 获取 配载部门名称.
	 *
	 * @return the 配载部门名称
	 */
	public String getAirAssembleDeptName() {
		return airAssembleDeptName;
	}
	
	/**
	 * 设置 配载部门名称.
	 *
	 * @param airAssembleDeptName the new 配载部门名称
	 */
	public void setAirAssembleDeptName(String airAssembleDeptName) {
		this.airAssembleDeptName = airAssembleDeptName;
	}
	
	/**
	 * 获取 跟踪信息列表.
	 *
	 * @return the 跟踪信息列表
	 */
	public List<AirTrackFlightEntity> getAirTrackFlightList() {
		return airTrackFlightList;
	}
	
	/**
	 * 设置 跟踪信息列表.
	 *
	 * @param airTrackFlightList the new 跟踪信息列表
	 */
	public void setAirTrackFlightList(List<AirTrackFlightEntity> airTrackFlightList) {
		this.airTrackFlightList = airTrackFlightList;
	}
	
	/** 
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-25 下午6:59:20
	 * @see com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTrackFlightEntity#getMessage()
	 */
	public String getMessage() {
		return message;
	}
	
	/** 
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-25 下午6:59:20
	 * @see com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTrackFlightEntity#setMessage(java.lang.String)
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 获取 航空正单id列表.
	 *
	 * @return the 航空正单id列表
	 */
	public String[] getAirWaybillIds() {
		return airWaybillIds;
	}
	
	/**
	 * 设置 航空正单id列表.
	 *
	 * @param airWaybillIds the new 航空正单id列表
	 */
	public void setAirWaybillIds(String[] airWaybillIds) {
		this.airWaybillIds = airWaybillIds;
	}

	/**
	 * 获取 起飞时间.
	 *
	 * @return the 起飞时间
	 */
	public Date getTakeOffTime() {
		return takeOffTime;
	}

	/**
	 * 设置 起飞时间.
	 *
	 * @param takeOffTime the new 起飞时间
	 */
	public void setTakeOffTime(Date takeOffTime) {
		this.takeOffTime = takeOffTime;
	}

	/**
	 * 获取 到达时间.
	 *
	 * @return the 到达时间
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * 设置 到达时间.
	 *
	 * @param arriveTime the new 到达时间
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	
}
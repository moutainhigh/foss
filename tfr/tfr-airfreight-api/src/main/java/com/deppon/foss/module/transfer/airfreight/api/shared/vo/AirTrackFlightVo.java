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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/api/shared/vo/AirTrackFlightVo.java
 *  
 *  FILE NAME          :AirTrackFlightVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirTrackFlightDto;
/**
 * 空运跟踪VO
 * @author 038300-foss-pengzhen
 * @date 2012-12-25 下午7:05:07
 */
public class AirTrackFlightVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6588520925486731677L;
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
	 * 实飞时间
	 */
	private Date actualTakeOffTime;
	/**
	 * 实到时间
	 */
	private Date actualArriveTime;
	/**
	 * 跟踪状态
	 */
	private String trackState;
	/**
	 * 航空正单id
	 */
    private String airWaybillId;
    /**
     * 跟踪人名称
     */
    private String createUserCode;
    /**
     * 跟踪人名称
     */
    private String createUserName;
    /**
     * 跟踪信息
     */
    private String message;
    /**
     * 跟踪部门编码
     */
    private String createOrgCode;
    /**
     * 跟踪部门名称
     */
    private String createOrgName;
    /**
     * 跟踪时间
     */
    private Date createTime;
    /**
     * 跟踪信息列表
     */
    private List<String> messageList;
    /**
     * 空运跟踪列表
     */
    private List <AirTrackFlightDto> dtoList;
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
	@DateFormat(formate="yyyy-MM-dd")
	public Date getFlightDate() {
		return flightDate;
	}
	
	/**
	 * 设置 航班日期.
	 *
	 * @param flightDate the new 航班日期
	 */
	@DateFormat(formate="yyyy-MM-dd")
	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
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
	 * 获取 航空正单id.
	 *
	 * @return the 航空正单id
	 */
	public String getAirWaybillId() {
		return airWaybillId;
	}
	
	/**
	 * 设置 航空正单id.
	 *
	 * @param airWaybillId the new 航空正单id
	 */
	public void setAirWaybillId(String airWaybillId) {
		this.airWaybillId = airWaybillId;
	}
	
	/**
	 * 获取 跟踪人名称.
	 *
	 * @return the 跟踪人名称
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	
	/**
	 * 设置 跟踪人名称.
	 *
	 * @param createUserCode the new 跟踪人名称
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	
	/**
	 * 获取 跟踪人名称.
	 *
	 * @return the 跟踪人名称
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	
	/**
	 * 设置 跟踪人名称.
	 *
	 * @param createUserName the new 跟踪人名称
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	/**
	 * 获取 跟踪信息.
	 *
	 * @return the 跟踪信息
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * 设置 跟踪信息.
	 *
	 * @param message the new 跟踪信息
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 获取 跟踪部门编码.
	 *
	 * @return the 跟踪部门编码
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	
	/**
	 * 设置 跟踪部门编码.
	 *
	 * @param createOrgCode the new 跟踪部门编码
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	
	/**
	 * 获取 跟踪部门名称.
	 *
	 * @return the 跟踪部门名称
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}
	
	/**
	 * 设置 跟踪部门名称.
	 *
	 * @param createOrgName the new 跟踪部门名称
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	
	/**
	 * 获取 跟踪时间.
	 *
	 * @return the 跟踪时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * 设置 跟踪时间.
	 *
	 * @param createTime the new 跟踪时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 获取 跟踪信息列表.
	 *
	 * @return the 跟踪信息列表
	 */
	public List<String> getMessageList() {
		return messageList;
	}
	
	/**
	 * 设置 跟踪信息列表.
	 *
	 * @param messageList the new 跟踪信息列表
	 */
	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
	
	/**
	 * 获取 空运跟踪列表.
	 *
	 * @return the 空运跟踪列表
	 */
	public List<AirTrackFlightDto> getDtoList() {
		return dtoList;
	}
	
	/**
	 * 设置 空运跟踪列表.
	 *
	 * @param dtoList the new 空运跟踪列表
	 */
	public void setDtoList(List<AirTrackFlightDto> dtoList) {
		this.dtoList = dtoList;
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
}
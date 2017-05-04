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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/dto/OnthewayDTO.java
 *  
 *  FILE NAME          :OnthewayDTO.java
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
 * 在途界面展示数据
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-25 上午9:13:16
 */
public class OnthewayDTO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********运单号************/
	private String waybillNo;
	/***********跟踪线路************/
	private String lineName;
	/***********跟踪路段************/
	private String currentPlace;
	/***********跟踪内容（线路加路段）************/
	private String currentContent;
	/*********联系人名称************/
	private String driverName;
	/*********联系人编码************/
	private String driverCode;
	/************系统跟踪状态***********/
	private String SystemTrackingStatus;
	/************人工跟踪状态***********/
	private String manualTrackingStatus;
	/************跟踪状态***********/
	private String currentStatus;
	/********** 最后一次跟踪时间 ************/
	private Date trackingTime;	//最后一次跟踪时间
	/********** 跟踪部门编码 ************/
	private String trackingOrgCode;
	/********** 跟踪部门名称 ************/
	private String trackingOrgName;
	/********** 跟踪方式 ************/
	private String trackingType;	//跟踪方式
	/********** 跟踪人 ************/
	private String trackingUserCode;
	/************出发部门编码*************/
	private String origOrgCode;
	/************出发部门名称*************/
	private String origOrgName;
	/************到达部门编码*************/
	private String destOrgCode;
	/************到达部门名称*************/
	private String destOrgName;
	public String getWaybillNo(){
		return waybillNo;
	}
	
	/**
	 * 获取 **********跟踪状态**********.
	 *
	 * @return the **********跟踪状态**********
	 */
	public String getCurrentStatus(){
		return currentStatus;
	}

	/**
	 * 设置 **********跟踪状态**********.
	 *
	 * @param currentStatus the new **********跟踪状态**********
	 */
	public void setCurrentStatus(String currentStatus){
		this.currentStatus = currentStatus;
	}

	/**
	 * 设置 ********运单号***********.
	 *
	 * @param waybillNo the new ********运单号***********
	 */
	public void setWaybillNo(String waybillNo){
		this.waybillNo = waybillNo;
	}
	
	/**
	 * 获取 *********跟踪线路***********.
	 *
	 * @return the *********跟踪线路***********
	 */
	public String getLineName(){
		return lineName;
	}
	
	/**
	 * 设置 *********跟踪线路***********.
	 *
	 * @param lineName the new *********跟踪线路***********
	 */
	public void setLineName(String lineName){
		this.lineName = lineName;
	}
	
	/**
	 * 获取 *********跟踪路段***********.
	 *
	 * @return the *********跟踪路段***********
	 */
	public String getCurrentPlace(){
		return currentPlace;
	}
	
	/**
	 * 设置 *********跟踪路段***********.
	 *
	 * @param currentPlace the new *********跟踪路段***********
	 */
	public void setCurrentPlace(String currentPlace){
		this.currentPlace = currentPlace;
	}
	
	/**
	 * 获取 *********跟踪内容（线路加路段）***********.
	 *
	 * @return the *********跟踪内容（线路加路段）***********
	 */
	public String getCurrentContent(){
		return currentContent;
	}
	
	/**
	 * 设置 *********跟踪内容（线路加路段）***********.
	 *
	 * @param currentContent the new *********跟踪内容（线路加路段）***********
	 */
	public void setCurrentContent(String currentContent){
		this.currentContent = currentContent;
	}
	
	/**
	 * 获取 *******联系人名称***********.
	 *
	 * @return the *******联系人名称***********
	 */
	public String getDriverName(){
		return driverName;
	}
	
	/**
	 * 设置 *******联系人名称***********.
	 *
	 * @param driverName the new *******联系人名称***********
	 */
	public void setDriverName(String driverName){
		this.driverName = driverName;
	}
	
	/**
	 * 获取 *******联系人编码***********.
	 *
	 * @return the *******联系人编码***********
	 */
	public String getDriverCode(){
		return driverCode;
	}
	
	/**
	 * 设置 *******联系人编码***********.
	 *
	 * @param driverCode the new *******联系人编码***********
	 */
	public void setDriverCode(String driverCode){
		this.driverCode = driverCode;
	}
	
	/**
	 * 
	 *
	 * @return 
	 */
	public String getSystemTrackingStatus(){
		return SystemTrackingStatus;
	}
	
	/**
	 * 
	 *
	 * @param systemTrackingStatus 
	 */
	public void setSystemTrackingStatus(String systemTrackingStatus){
		SystemTrackingStatus = systemTrackingStatus;
	}
	
	/**
	 * 获取 **********人工跟踪状态**********.
	 *
	 * @return the **********人工跟踪状态**********
	 */
	public String getManualTrackingStatus(){
		return manualTrackingStatus;
	}
	
	/**
	 * 设置 **********人工跟踪状态**********.
	 *
	 * @param manualTrackingStatus the new **********人工跟踪状态**********
	 */
	public void setManualTrackingStatus(String manualTrackingStatus){
		this.manualTrackingStatus = manualTrackingStatus;
	}
	
	/**
	 * 获取 ******** 最后一次跟踪时间 ***********.
	 *
	 * @return the ******** 最后一次跟踪时间 ***********
	 */
	public Date getTrackingTime(){
		return trackingTime;
	}
	
	/**
	 * 设置 ******** 最后一次跟踪时间 ***********.
	 *
	 * @param trackingTime the new ******** 最后一次跟踪时间 ***********
	 */
	public void setTrackingTime(Date trackingTime){
		this.trackingTime = trackingTime;
	}
	
	/**
	 * 获取 ******** 跟踪部门编码 ***********.
	 *
	 * @return the ******** 跟踪部门编码 ***********
	 */
	public String getTrackingOrgCode(){
		return trackingOrgCode;
	}
	
	/**
	 * 设置 ******** 跟踪部门编码 ***********.
	 *
	 * @param trackingOrgCode the new ******** 跟踪部门编码 ***********
	 */
	public void setTrackingOrgCode(String trackingOrgCode){
		this.trackingOrgCode = trackingOrgCode;
	}
	
	/**
	 * 获取 ******** 跟踪部门名称 ***********.
	 *
	 * @return the ******** 跟踪部门名称 ***********
	 */
	public String getTrackingOrgName(){
		return trackingOrgName;
	}
	
	/**
	 * 设置 ******** 跟踪部门名称 ***********.
	 *
	 * @param trackingOrgName the new ******** 跟踪部门名称 ***********
	 */
	public void setTrackingOrgName(String trackingOrgName){
		this.trackingOrgName = trackingOrgName;
	}
	
	/**
	 * 获取 ******** 跟踪方式 ***********.
	 *
	 * @return the ******** 跟踪方式 ***********
	 */
	public String getTrackingType(){
		return trackingType;
	}
	
	/**
	 * 设置 ******** 跟踪方式 ***********.
	 *
	 * @param trackingType the new ******** 跟踪方式 ***********
	 */
	public void setTrackingType(String trackingType){
		this.trackingType = trackingType;
	}
	
	
	
	public String getTrackingUserCode(){
		return trackingUserCode;
	}

	public void setTrackingUserCode(String trackingUserCode){
		this.trackingUserCode = trackingUserCode;
	}

	/**
	 * 获取 **********出发部门编码************.
	 *
	 * @return the **********出发部门编码************
	 */
	public String getOrigOrgCode(){
		return origOrgCode;
	}
	
	/**
	 * 设置 **********出发部门编码************.
	 *
	 * @param origOrgCode the new **********出发部门编码************
	 */
	public void setOrigOrgCode(String origOrgCode){
		this.origOrgCode = origOrgCode;
	}
	
	
	
	public String getOrigOrgName(){
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName){
		this.origOrgName = origOrgName;
	}

	public String getDestOrgName(){
		return destOrgName;
	}

	public void setDestOrgName(String destOrgName){
		this.destOrgName = destOrgName;
	}

	/**
	 * 获取 **********到达部门编码************.
	 *
	 * @return the **********到达部门编码************
	 */
	public String getDestOrgCode(){
		return destOrgCode;
	}
	
	/**
	 * 设置 **********到达部门编码************.
	 *
	 * @param destOrgCode the new **********到达部门编码************
	 */
	public void setDestOrgCode(String destOrgCode){
		this.destOrgCode = destOrgCode;
	}
	
}
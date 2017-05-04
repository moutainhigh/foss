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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/QueryVehicleAssembleBillConditionDto.java
 *  
 *  FILE NAME          :QueryVehicleAssembleBillConditionDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.server.web.result.json.annotation.DateFormat;

/** 
 * @className: QueryVehicleAssembleBillConditionDto
 * @author: ShiWei shiwei@outlook.com
 * @description: dto类，用来接收传入的查询配载单条件
 * @date: 2012-11-13 下午4:07:50
 * 
 */
public class QueryVehicleAssembleBillConditionDto implements Serializable {

	private static final long serialVersionUID = -6931734375960028668L;
	//配载车次号
	private String vehicleAssembleNo;
	//配载部门
	private String assembleDept;
	//配载类型
	private String assembleType;
	//司机
	private String driver;
	//到达部门
	private String arriveDept;
	//车牌号
	private String vehicleNo;
	//开始制单时间、
	private Date beginCreateTime;
	//截止制单时间
	private Date endCreateTime;
	//当前部门code
	private String currentDeptCode;
	//车辆所属类型
	private String vehicleType;
	
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getCurrentDeptCode() {
		return currentDeptCode;
	}
	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}
	public String getAssembleDept() {
		return assembleDept;
	}
	public void setAssembleDept(String assembleDept) {
		this.assembleDept = assembleDept;
	}
	public String getAssembleType() {
		return assembleType;
	}
	public void setAssembleType(String assembleType) {
		this.assembleType = assembleType;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getArriveDept() {
		return arriveDept;
	}
	public void setArriveDept(String arriveDept) {
		this.arriveDept = arriveDept;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	@DateFormat
	public Date getBeginCreateTime() {
		return beginCreateTime;
	}
	@DateFormat
	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	@DateFormat
	public Date getEndCreateTime() {
		return endCreateTime;
	}
	@DateFormat
	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	
}
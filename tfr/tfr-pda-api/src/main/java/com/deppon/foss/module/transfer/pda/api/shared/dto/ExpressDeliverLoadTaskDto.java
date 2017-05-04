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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/PDATaskDto.java
 *  
 *  FILE NAME          :PDATaskDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: PDATaskDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * PDA装卸车任务
 * @author dp-duyi
 * @date 2012-12-17 下午2:26:25
 */
public class ExpressDeliverLoadTaskDto implements Serializable {
	
	private static final long serialVersionUID = 7739033369720064886L;
	/**收派员*/
	private String courier;                      //收派员编码
	/**vehicleNo*/
	private String vehicleNo;                      //车牌号
	/**platformNo*/
	private String platformNo;                      //月台号
	/**createOrgCode*/
	private String createOrgCode;                      //建立任务部门编码
	/**createTime*/
	private Date createTime;                      //建立任务时间
	/**operatorCode*/
	private String operatorCode;                      //操作人编码
	/**deviceNo*/
	private String deviceNo;                      //PDA设备号
	/**taskNo*/
	private String taskNo;                      //任务编号
	private String goodsType;                   //货物类型:AB货
	
	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
	}

	/**
	 * Gets the vehicleNo.
	 *
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * Sets the vehicleNo.
	 *
	 * @param vehicleNo the new vehicleNo
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * Gets the platformNo.
	 *
	 * @return the platformNo
	 */
	public String getPlatformNo() {
		return platformNo;
	}
	
	/**
	 * Sets the platformNo.
	 *
	 * @param platformNo the new platformNo
	 */
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	
	/**
	 * Gets the createOrgCode.
	 *
	 * @return the createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	
	/**
	 * Sets the createOrgCode.
	 *
	 * @param createOrgCode the new createOrgCode
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	
	/**
	 * Gets the createTime.
	 *
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * Sets the createTime.
	 *
	 * @param createTime the new createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * Gets the operatorCode.
	 *
	 * @return the operatorCode
	 */
	public String getOperatorCode() {
		return operatorCode;
	}
	
	/**
	 * Sets the operatorCode.
	 *
	 * @param operatorCode the new operatorCode
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	
	/**
	 * Gets the deviceNo.
	 *
	 * @return the deviceNo
	 */
	public String getDeviceNo() {
		return deviceNo;
	}
	
	/**
	 * Sets the deviceNo.
	 *
	 * @param deviceNo the new deviceNo
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	
	/**
	 * Gets the taskNo.
	 *
	 * @return the taskNo
	 */
	public String getTaskNo() {
		return taskNo;
	}
	
	/**
	 * Sets the taskNo.
	 *
	 * @param taskNo the new taskNo
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
}
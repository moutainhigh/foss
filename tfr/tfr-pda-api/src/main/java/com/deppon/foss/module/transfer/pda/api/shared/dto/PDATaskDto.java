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
import java.util.List;

/**
 * PDA装卸车任务
 * @author dp-duyi
 * @date 2012-12-17 下午2:26:25
 */
public class PDATaskDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5755789021683273621L;
	/**loaderCodes*/
	private List<LoaderDto> loaderCodes;                      //理货员
	/**vehicleNo*/
	private String vehicleNo;                      //车牌号
	/**platformNo*/
	private String platformNo;                      //月台号
	/**createOrgCode*/
	private String createOrgCode;                      //建立任务部门
	/**createTime*/
	private Date createTime;                      //建立任务时间
	/**operatorCode*/
	private String operatorCode;                      //操作人编号
	/**deviceNo*/
	private String deviceNo;                      //PDA设备号
	/**taskNo*/
	private String taskNo;                      //卸车任务编号
	/**transitGoodsType*/
	private String transitGoodsType;              //转货类型
	
	/**
	 * Gets the loaderCodes.
	 *
	 * @return the loaderCodes
	 */
	public List<LoaderDto> getLoaderCodes() {
		return loaderCodes;
	}
	
	/**
	 * Sets the loaderCodes.
	 *
	 * @param loaderCodes the new loaderCodes
	 */
	public void setLoaderCodes(List<LoaderDto> loaderCodes) {
		this.loaderCodes = loaderCodes;
	}
	
	/**
	 * //车牌号
	 *
	 * @return the vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * //车牌号
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

	/**
	 * 
	 * Gets the transitGoodsType. 
	 * @author alfred
	 * @date 2013-11-13 上午11:32:09
	 * @return transitGoodsType
	 * @see
	 */
	public String getTransitGoodsType() {
		return transitGoodsType;
	}

	/**
	 * 
	 * <p> Sets the transitGoodsType</p> 
	 * @author alfred
	 * @date 2013-11-13 上午11:32:15
	 * @param transitGoodsType
	 * @see
	 */
	public void setTransitGoodsType(String transitGoodsType) {
		this.transitGoodsType = transitGoodsType;
	}
}
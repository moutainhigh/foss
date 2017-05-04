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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/TruckTaskEntity.java
 *  
 *  FILE NAME          :TruckTaskEntity.java
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
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.domain
 * FILE    NAME: TruckTaskEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务车辆实体
 * @author dp-duyi
 * @date 2012-11-7 上午8:35:37
 */
public class TruckTaskEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 183148421689567904L;
	/**id*/
	private String id;
	/**车牌号*/
	private String vehicleNo;
	/**线路名称*/
	private String lineName;
	/**线路虚拟编号*/
	private String lineVirtualCode;
	/**业务类型*/
	private String businessType;
	/**司机1*/
	private String driverName1;
	/**司机1编号*/
	private String driverCode1;
	/**司机1电话*/
	private String driverPhone1;
	/**司机2电话*/
	private String driverPhone2;
	/**状态*/
	private String state;
	/**司机2编号*/
	private String driverCode2;
	/**司机2名称*/
	private String driverName2;
	/**创建时间*/
	private Date createTime;
	/**是否整车*/
	private String beCarLoad;
	/**计费配载单*/
	private String chargingAssembleNo;
	/**修改时间*/
	private Date modifyTime;
	/**出发部门编码，以生成车辆任务的第一个交接单出发部门为主*/
	private String origOrgCode;
	
	
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * Sets the 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	
	
	/**
	 * Gets the 线路名称.
	 *
	 * @return the 线路名称
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * Sets the 线路名称.
	 *
	 * @param lineName the new 线路名称
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * Gets the 线路虚拟编号.
	 *
	 * @return the 线路虚拟编号
	 */
	public String getLineVirtualCode() {
		return lineVirtualCode;
	}

	/**
	 * Sets the 线路虚拟编号.
	 *
	 * @param lineVirualCode the new 线路虚拟编号
	 */
	public void setLineVirtualCode(String lineVirtualCode) {
		this.lineVirtualCode = lineVirtualCode;
	}

	/**
	 * Gets the 业务类型.
	 *
	 * @return the 业务类型
	 */
	public String getBusinessType() {
		return businessType;
	}
	
	/**
	 * Sets the 业务类型.
	 *
	 * @param businessType the new 业务类型
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	/**
	 * Gets the 司机1.
	 *
	 * @return the 司机1
	 */
	public String getDriverName1() {
		return driverName1;
	}
	
	/**
	 * Sets the 司机1.
	 *
	 * @param driverName1 the new 司机1
	 */
	public void setDriverName1(String driverName1) {
		this.driverName1 = driverName1;
	}
	
	/**
	 * Gets the 司机1编号.
	 *
	 * @return the 司机1编号
	 */
	public String getDriverCode1() {
		return driverCode1;
	}
	
	/**
	 * Sets the 司机1编号.
	 *
	 * @param driverCode1 the new 司机1编号
	 */
	public void setDriverCode1(String driverCode1) {
		this.driverCode1 = driverCode1;
	}
	
	/**
	 * Gets the 司机1电话.
	 *
	 * @return the 司机1电话
	 */
	public String getDriverPhone1() {
		return driverPhone1;
	}
	
	/**
	 * Sets the 司机1电话.
	 *
	 * @param driverPhone1 the new 司机1电话
	 */
	public void setDriverPhone1(String driverPhone1) {
		this.driverPhone1 = driverPhone1;
	}
	
	/**
	 * Gets the 司机2电话.
	 *
	 * @return the 司机2电话
	 */
	public String getDriverPhone2() {
		return driverPhone2;
	}
	
	/**
	 * Sets the 司机2电话.
	 *
	 * @param driverPhone2 the new 司机2电话
	 */
	public void setDriverPhone2(String driverPhone2) {
		this.driverPhone2 = driverPhone2;
	}
	
	/**
	 * Gets the 状态.
	 *
	 * @return the 状态
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * Sets the 状态.
	 *
	 * @param state the new 状态
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Gets the 司机2编号.
	 *
	 * @return the 司机2编号
	 */
	public String getDriverCode2() {
		return driverCode2;
	}
	
	/**
	 * Sets the 司机2编号.
	 *
	 * @param driverCode2 the new 司机2编号
	 */
	public void setDriverCode2(String driverCode2) {
		this.driverCode2 = driverCode2;
	}
	
	/**
	 * Gets the 司机2名称.
	 *
	 * @return the 司机2名称
	 */
	public String getDriverName2() {
		return driverName2;
	}
	
	/**
	 * Sets the 司机2名称.
	 *
	 * @param driverName2 the new 司机2名称
	 */
	public void setDriverName2(String driverName2) {
		this.driverName2 = driverName2;
	}
	
	/**
	 * Gets the 创建时间.
	 *
	 * @return the 创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	
	/**
	 * Sets the 创建时间.
	 *
	 * @param createTime the new 创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * Gets the 是否整车.
	 *
	 * @return the 是否整车
	 */
	public String getBeCarLoad() {
		return beCarLoad;
	}
	
	/**
	 * Sets the 是否整车.
	 *
	 * @param beCarLoad the new 是否整车
	 */
	public void setBeCarLoad(String beCarLoad) {
		this.beCarLoad = beCarLoad;
	}
	
	/**
	 * Gets the 计费配载单.
	 *
	 * @return the 计费配载单
	 */
	public String getChargingAssembleNo() {
		return chargingAssembleNo;
	}
	
	/**
	 * Sets the 计费配载单.
	 *
	 * @param chargingAssembleNo the new 计费配载单
	 */
	public void setChargingAssembleNo(String chargingAssembleNo) {
		this.chargingAssembleNo = chargingAssembleNo;
	}
}
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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/domain/VehicleInfoEntity.java
 *  
 *  FILE NAME          :VehicleInfoEntity.java
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

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
public class VehicleInfoEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********车牌号************/
	private String vehicleNo;
	
	/**********车辆归属类型************/
	private String truckType;
	
	/**********车辆业务类型************/
	private String businessType;
	
	/**********车辆归属部门************/
	private String truckOrgCode;
	
	/**********车辆归属名称************/
	private String truckOrgName;
	
	/**********车型************/
	private BigDecimal length;
	
	/**********车型************/
	private BigDecimal vehicleLength;
	
	/**********载重/净空************/
	private String selfcubage;
	
	/**********是否安装GPS************/
	private String hasGPS;

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
	 * 获取 ********车辆归属类型***********.
	 *
	 * @return the ********车辆归属类型***********
	 */
	public String getTruckType(){
		return truckType;
	}

	/**
	 * 设置 ********车辆归属类型***********.
	 *
	 * @param truckType the new ********车辆归属类型***********
	 */
	public void setTruckType(String truckType){
		this.truckType = truckType;
	}

	/**
	 * 获取 ********车辆业务类型***********.
	 *
	 * @return the ********车辆业务类型***********
	 */
	public String getBusinessType(){
		return businessType;
	}

	/**
	 * 设置 ********车辆业务类型***********.
	 *
	 * @param businessType the new ********车辆业务类型***********
	 */
	public void setBusinessType(String businessType){
		this.businessType = businessType;
	}

	/**
	 * 获取 ********车辆归属部门***********.
	 *
	 * @return the ********车辆归属部门***********
	 */
	public String getTruckOrgCode(){
		return truckOrgCode;
	}

	/**
	 * 设置 ********车辆归属部门***********.
	 *
	 * @param truckOrgCode the new ********车辆归属部门***********
	 */
	public void setTruckOrgCode(String truckOrgCode){
		this.truckOrgCode = truckOrgCode;
	}

	/**
	 * 获取 ********车辆归属名称***********.
	 *
	 * @return the ********车辆归属名称***********
	 */
	public String getTruckOrgName(){
		return truckOrgName;
	}

	/**
	 * 设置 ********车辆归属名称***********.
	 *
	 * @param truckOrgName the new ********车辆归属名称***********
	 */
	public void setTruckOrgName(String truckOrgName){
		this.truckOrgName = truckOrgName;
	}



	/**
	 * 获取 ********车型***********.
	 *
	 * @return the ********车型***********
	 */
	public BigDecimal getLength(){
		return length;
	}

	/**
	 * 设置 ********车型***********.
	 *
	 * @param length the new ********车型***********
	 */
	public void setLength(BigDecimal length){
		this.length = length;
	}

	/**
	 * 获取 ********是否安装GPS***********.
	 *
	 * @return the ********是否安装GPS***********
	 */
	public String getHasGPS(){
		return hasGPS;
	}

	/**
	 * 设置 ********是否安装GPS***********.
	 *
	 * @param hasGPS the new ********是否安装GPS***********
	 */
	public void setHasGPS(String hasGPS){
		this.hasGPS = hasGPS;
	}

	/**
	 * 获取 ********载重/净空***********.
	 *
	 * @return the ********载重/净空***********
	 */
	public String getSelfcubage(){
		return selfcubage;
	}

	/**
	 * 设置 ********载重/净空***********.
	 *
	 * @param selfcubage the new ********载重/净空***********
	 */
	public void setSelfcubage(String selfcubage){
		this.selfcubage = selfcubage;
	}

	public BigDecimal getVehicleLength() {
		return vehicleLength;
	}

	public void setVehicleLength(BigDecimal vehicleLength) {
		this.vehicleLength = vehicleLength;
	}


	
	

	
}
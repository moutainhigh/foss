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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/domain/RelationInfoEntity.java
 *  
 *  FILE NAME          :RelationInfoEntity.java
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

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
public class RelationInfoEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********司机姓名************/
	private String driverName;
	
	/**********司机编号************/
	private String driverCode;
	
	/**********司机电话************/
	private String driverPhone;
	
	/**********车牌号************/
	private String vehicleNo;
	
	/**********车队部门经理电话************/
	private String fleetManagerPhone;

	/**
	 * 获取 ********司机姓名***********.
	 *
	 * @return the ********司机姓名***********
	 */
	public String getDriverName(){
		return driverName;
	}

	/**
	 * 设置 ********司机姓名***********.
	 *
	 * @param driverName the new ********司机姓名***********
	 */
	public void setDriverName(String driverName){
		this.driverName = driverName;
	}

	/**
	 * 获取 ********司机电话***********.
	 *
	 * @return the ********司机电话***********
	 */
	public String getDriverPhone(){
		return driverPhone;
	}

	/**
	 * 设置 ********司机电话***********.
	 *
	 * @param driverPhone the new ********司机电话***********
	 */
	public void setDriverPhone(String driverPhone){
		this.driverPhone = driverPhone;
	}

	/**
	 * 获取 ********车队部门经理电话***********.
	 *
	 * @return the ********车队部门经理电话***********
	 */
	public String getFleetManagerPhone(){
		return fleetManagerPhone;
	}

	/**
	 * 设置 ********车队部门经理电话***********.
	 *
	 * @param fleetManagerPhone the new ********车队部门经理电话***********
	 */
	public void setFleetManagerPhone(String fleetManagerPhone){
		this.fleetManagerPhone = fleetManagerPhone;
	}

	/**
	 * 获取 ********司机编号***********.
	 *
	 * @return the ********司机编号***********
	 */
	public String getDriverCode(){
		return driverCode;
	}

	/**
	 * 设置 ********司机编号***********.
	 *
	 * @param driverCode the new ********司机编号***********
	 */
	public void setDriverCode(String driverCode){
		this.driverCode = driverCode;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	


	
}
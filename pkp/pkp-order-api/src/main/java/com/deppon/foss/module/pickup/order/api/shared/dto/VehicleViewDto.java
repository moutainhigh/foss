/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 038590-foss-wanghui
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-order-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/order/api/shared/dto/DispatchOrderDto.java
 * 
 * FILE NAME        	: DispatchOrderDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.order.api.shared.dto;

import java.io.Serializable;

/**
 * 
 * @ClassName: ExpressOrderDto 
 * @Description: 快递订单DTO 
 * @author YANGBIN
 * @date 2014-5-13 下午2:11:10 
 *
 */
public class VehicleViewDto implements Serializable {
	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = 1L;
	private String vehicle_no;
	private String vehicle_type;
	private String driver_name;
	private String driver_tel;
	private String smallzone;
	private String bigzone;
	private String status;
	private String responsibletype;
	public String getVehicle_no() {
		return vehicle_no;
	}
	public void setVehicle_no(String vehicle_no) {
		this.vehicle_no = vehicle_no;
	}
	public String getVehicle_type() {
		return vehicle_type;
	}
	public void setVehicle_type(String vehicle_type) {
		this.vehicle_type = vehicle_type;
	}
	public String getDriver_name() {
		return driver_name;
	}
	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}
	public String getDriver_tel() {
		return driver_tel;
	}
	public void setDriver_tel(String driver_tel) {
		this.driver_tel = driver_tel;
	}
	public String getSmallzone() {
		return smallzone;
	}
	public void setSmallzone(String smallzone) {
		this.smallzone = smallzone;
	}
	public String getBigzone() {
		return bigzone;
	}
	public void setBigzone(String bigzone) {
		this.bigzone = bigzone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResponsibletype() {
		return responsibletype;
	}
	public void setResponsibletype(String responsibletype) {
		this.responsibletype = responsibletype;
	}
}
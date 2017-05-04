/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/shared/dto/PickupDoneDto.java
 * 
 * FILE NAME        	: PickupDoneDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 接货dto
 * @author foss-meiying
 * @date 2012-11-28 上午11:41:39
 * @since
 * @version
 */
public class PickupDoneDto implements Serializable{
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5093412584526038289L;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 司机工号
	 */
	private String driverCode;
	/**
	 * 创建人
	 */
	private String createUserName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 部门code
	 */
	private String orgCode;
	
	/**
	 * Gets the orgCode.
	 * 
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the orgCode.
	 * 
	 * @param orgCode the orgCode to see
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
	 * Gets the 司机工号.
	 *
	 * @return the 司机工号
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * Sets the 司机工号.
	 *
	 * @param driverCode the new 司机工号
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * Gets the 创建人.
	 *
	 * @return the 创建人
	 */
	public String getCreateUserName() {
		return createUserName;
	}

	/**
	 * Sets the 创建人.
	 *
	 * @param createUserName the new 创建人
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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



}
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
 *  
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/ScheduleDriverDto.java
 * 
 *  FILE NAME     :ScheduleDriverDto.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.dto
 * FILE    NAME: ScheduleDriverDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

/**
 * 排版司机相关DTO
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-11-3 下午12:20:37
 */
public class ScheduleDriverDto implements java.io.Serializable {

	private static final long serialVersionUID = -2033703628721808980L;
	/**
	 * 司机代码
	 */
	private String driverCodes;
	/**
	 * 司机编码数量
	 */
	private int driverCodeCnt;
	/**
	 * 司机所属部门DRIVER_ORG_CODE
	 */
	private String driverOrgCode;

	/**
	 * 获取 司机代码.
	 * 
	 * @return the 司机代码
	 */
	public String getDriverCodes() {
		return driverCodes;
	}

	/**
	 * 设置 司机代码.
	 * 
	 * @param driverCodes
	 *            the new 司机代码
	 */
	public void setDriverCodes(String driverCodes) {
		this.driverCodes = driverCodes;
	}

	/**
	 * 获取 司机编码数量.
	 * 
	 * @return the 司机编码数量
	 */
	public int getDriverCodeCnt() {
		return driverCodeCnt;
	}

	/**
	 * 设置 司机编码数量.
	 * 
	 * @param driverCodeCnt
	 *            the new 司机编码数量
	 */
	public void setDriverCodeCnt(int driverCodeCnt) {
		this.driverCodeCnt = driverCodeCnt;
	}

	/**
	 * 获取 司机所属部门DRIVER_ORG_CODE.
	 * 
	 * @return the 司机所属部门DRIVER_ORG_CODE
	 */
	public String getDriverOrgCode() {
		return driverOrgCode;
	}

	/**
	 * 设置 司机所属部门DRIVER_ORG_CODE.
	 * 
	 * @param driverOrgCode
	 *            the new 司机所属部门DRIVER_ORG_CODE
	 */
	public void setDriverOrgCode(String driverOrgCode) {
		this.driverOrgCode = driverOrgCode;
	}

}
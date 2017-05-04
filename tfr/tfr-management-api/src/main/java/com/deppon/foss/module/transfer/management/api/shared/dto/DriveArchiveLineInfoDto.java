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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/dto/DriveArchiveLineInfoDto.java
 *  
 *  FILE NAME          :DriveArchiveLineInfoDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.dto;

import java.io.Serializable;

/**
 * 行驶档案中线路信息的DTO
 * @author foss-liuxue(for IBM)
 * @date 2013-1-4 下午4:32:30
 */
public class DriveArchiveLineInfoDto implements Serializable {

	private static final long serialVersionUID = -455855783430597237L;
	
	/**
	 * 始发站
	 */
	private String deptRegionName;
	
	/**
	 * 目的站
	 */
	private String arriveRegionName;
	
	/**
	 * 预计发车时间
	 */
	private String stipulateDepartureTime;
	
	/**
	 * 预计到达时间
	 */
	private String stipulateArriveTime;
	
	/**
	 * 标准时效
	 */
	private String standardPreion;

	/**
	 * 获取 始发站.
	 *
	 * @return the 始发站
	 */
	public String getDeptRegionName() {
		return deptRegionName;
	}

	/**
	 * 设置 始发站.
	 *
	 * @param deptRegionName the new 始发站
	 */
	public void setDeptRegionName(String deptRegionName) {
		this.deptRegionName = deptRegionName;
	}

	/**
	 * 获取 目的站.
	 *
	 * @return the 目的站
	 */
	public String getArriveRegionName() {
		return arriveRegionName;
	}

	/**
	 * 设置 目的站.
	 *
	 * @param arriveRegionName the new 目的站
	 */
	public void setArriveRegionName(String arriveRegionName) {
		this.arriveRegionName = arriveRegionName;
	}

	/**
	 * 获取 预计发车时间.
	 *
	 * @return the 预计发车时间
	 */
	public String getStipulateDepartureTime() {
		return stipulateDepartureTime;
	}

	/**
	 * 设置 预计发车时间.
	 *
	 * @param stipulateDepartureTime the new 预计发车时间
	 */
	public void setStipulateDepartureTime(String stipulateDepartureTime) {
		this.stipulateDepartureTime = stipulateDepartureTime;
	}

	/**
	 * 获取 预计到达时间.
	 *
	 * @return the 预计到达时间
	 */
	public String getStipulateArriveTime() {
		return stipulateArriveTime;
	}

	/**
	 * 设置 预计到达时间.
	 *
	 * @param stipulateArriveTime the new 预计到达时间
	 */
	public void setStipulateArriveTime(String stipulateArriveTime) {
		this.stipulateArriveTime = stipulateArriveTime;
	}

	/**
	 * 获取 标准时效.
	 *
	 * @return the 标准时效
	 */
	public String getStandardPreion() {
		return standardPreion;
	}

	/**
	 * 设置 标准时效.
	 *
	 * @param standardPreion the new 标准时效
	 */
	public void setStandardPreion(String standardPreion) {
		this.standardPreion = standardPreion;
	}
}
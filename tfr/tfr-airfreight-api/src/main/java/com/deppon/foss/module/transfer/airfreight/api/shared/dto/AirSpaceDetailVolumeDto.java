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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/AirSpaceDetailVolumeDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.dto;

import java.io.Serializable;
/**
 * 航空公司舱位Dto
 * 
 * @author dp-liming
 * @date 2012-10-18 下午17:39:30
 */
public class AirSpaceDetailVolumeDto implements Serializable {
	
	private static final long serialVersionUID = 1618351284107058690L;
	/**
	 * 配载部门
	 */
	private String assembleOrgName;
	/**
	 * 目的站
	 */
	private String arrvRegionName;
	/**
	 * 舱位使用日期
	 */
	private String takeOffDate;

	/**
	 * 获取 配载部门.
	 *
	 * @return the 配载部门
	 */
	public String getAssembleOrgName() {
		return assembleOrgName;
	}

	/**
	 * 设置 配载部门.
	 *
	 * @param assembleOrgName the new 配载部门
	 */
	public void setAssembleOrgName(String assembleOrgName) {
		this.assembleOrgName = assembleOrgName;
	}

	/**
	 * 获取 目的站.
	 *
	 * @return the 目的站
	 */
	public String getArrvRegionName() {
		return arrvRegionName;
	}

	/**
	 * 设置 目的站.
	 *
	 * @param arrvRegionName the new 目的站
	 */
	public void setArrvRegionName(String arrvRegionName) {
		this.arrvRegionName = arrvRegionName;
	}

	/**
	 * 获取 舱位使用日期.
	 *
	 * @return the 舱位使用日期
	 */
	public String getTakeOffDate() {
		return takeOffDate;
	}

	/**
	 * 设置 舱位使用日期.
	 *
	 * @param takeOffDate the new 舱位使用日期
	 */
	public void setTakeOffDate(String takeOffDate) {
		this.takeOffDate = takeOffDate;
	}
	
	

}
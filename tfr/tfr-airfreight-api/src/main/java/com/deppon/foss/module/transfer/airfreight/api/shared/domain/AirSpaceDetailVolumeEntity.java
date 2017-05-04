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
 *  FILE PATH          :/AirSpaceDetailVolumeEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 航空公司舱位明细实体
 * 
 * @author dp-liming
 * @date 2012-10-18 下午17:39:30
 * @update zwd 200968 2015-05-07
 * @param 航班类型从原来的早班、中班、晚班和中转变为即日达、次日达、航空普运
 */
public class AirSpaceDetailVolumeEntity extends BaseEntity {
		

	/**
	 * 
	 */
	private static final long serialVersionUID = -3344076554001378359L;
	/**
	 *  配载部门
	 */
	private String assembleOrgName;
	/**
	 *  目的站
	 */
	private String arrvRegionName;
	/**
	 *  航班类型
	 */
	private String flightType; 
	/**
	 *  总舱位
	 */
	private BigDecimal spaceTotal; 
	/**
	 *  舱位使用日期
	 */
	private Date takeOffDate;
	/*	*//**
	 *  早班总仓位
	 *  即日达总仓位
	 */
	private BigDecimal earlySpaceTotal;
	/**
	 *  中班总仓位
	 *  次日达总仓位
	 */
	private BigDecimal middaySpaceTotal;
	/**
	 *  晚班总仓位
	 *  次日达总仓位
	 */
	private BigDecimal nightSpaceTotal;
	/**
	 *  中转总仓位
	 *  航空普运总仓位
	 */
	private BigDecimal transitSpaceTotal;

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
	 * 获取 航班类型.
	 *
	 * @return the 航班类型
	 */
	public String getFlightType() {
		return flightType;
	}

	/**
	 * 设置 航班类型.
	 *
	 * @param flightType the new 航班类型
	 */
	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}

	/**
	 * 获取 总舱位.
	 *
	 * @return the 总舱位
	 */
	public BigDecimal getSpaceTotal() {
		return spaceTotal;
	}

	/**
	 * 设置 总舱位.
	 *
	 * @param spaceTotal the new 总舱位
	 */
	public void setSpaceTotal(BigDecimal spaceTotal) {
		this.spaceTotal = spaceTotal;
	}

	/**
	 * 获取 舱位使用日期.
	 *
	 * @return the 舱位使用日期
	 */
	public Date getTakeOffDate() {
		return takeOffDate;
	}

	/**
	 * 设置 舱位使用日期.
	 *
	 * @param takeOffDate the new 舱位使用日期
	 */
	public void setTakeOffDate(Date takeOffDate) {
		this.takeOffDate = takeOffDate;
	}

	/**
	 * 获取 早班总仓位.
	 * 即日达总仓位
	 * @return the 即日达总仓位
	 */
	public BigDecimal getEarlySpaceTotal() {
		return earlySpaceTotal;
	}

	/**
	 * 设置 早班总仓位.
	 * 即日达总仓位
	 * @param earlySpaceTotal the new 即日达总仓位
	 */
	public void setEarlySpaceTotal(BigDecimal earlySpaceTotal) {
		this.earlySpaceTotal = earlySpaceTotal;
	}

	/**
	 * 获取 中班总仓位.
	 * 次日达总仓位
	 * @return the 次日达总仓位
	 */
	public BigDecimal getMiddaySpaceTotal() {
		return middaySpaceTotal;
	}

	/**
	 * 设置 中班总仓位.
	 * 次日达总仓位
	 * @param middaySpaceTotal the new 次日达总仓位
	 */
	public void setMiddaySpaceTotal(BigDecimal middaySpaceTotal) {
		this.middaySpaceTotal = middaySpaceTotal;
	}

	/**
	 * 获取 晚班总仓位.
	 * 次日达总仓位
	 * @return the 次日达总仓位
	 */
	public BigDecimal getNightSpaceTotal() {
		return nightSpaceTotal;
	}

	/**
	 * 设置 晚班总仓位.
	 * 次日达总仓位
	 * @param nightSpaceTotal the new 次日达总仓位
	 */
	public void setNightSpaceTotal(BigDecimal nightSpaceTotal) {
		this.nightSpaceTotal = nightSpaceTotal;
	}

	/**
	 * 获取 中转总仓位.
	 * 航空普运总仓位
	 * @return the 航空普运总仓位
	 */
	public BigDecimal getTransitSpaceTotal() {
		return transitSpaceTotal;
	}

	/**
	 * 设置 中转总仓位.
	 * 航空普运总仓位
	 * @param transitSpaceTotal the new 航空普运总仓位
	 */
	public void setTransitSpaceTotal(BigDecimal transitSpaceTotal) {
		this.transitSpaceTotal = transitSpaceTotal;
	}

}
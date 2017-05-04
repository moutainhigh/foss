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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/DepartureDto.java
 * 
 *  FILE NAME     :DepartureDto.java
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
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *  班次DTO
 * @author huyue
 * @date 2012-10-15 下午12:50:34
 */
public class DepartureDto implements Serializable {
	
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 2503911558468096776L;
	/**
	 *  线路名称
	 */
    private String lineName;
    
    /**
     *  线路虚拟编码
     */
    private String lineVirtualCode;
    
    /**
     *  班次虚拟编码
     */
    private String departureStandardVirtualCode;
    
    /**
     *  准点发车时间
     */
    private Date leaveTime;
    
    /**
     *  准点到达时间
     */
    private Date arriveTime;
    
    /**
     *  准点到达时间的天数,默认是0
     */
    private Integer arriveDay;

    /**
     *  出发部门编码
     */
    private String sourceCode;
    
    /**
     *  到达部门编码
     */
    private String targetCode;

	/**
	 * 获取 线路名称.
	 *
	 * @return the 线路名称
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * 设置 线路名称.
	 *
	 * @param lineName the new 线路名称
	 */
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	/**
	 * 获取 线路虚拟编码.
	 *
	 * @return the 线路虚拟编码
	 */
	public String getLineVirtualCode() {
		return lineVirtualCode;
	}

	/**
	 * 设置 线路虚拟编码.
	 *
	 * @param lineVirtualCode the new 线路虚拟编码
	 */
	public void setLineVirtualCode(String lineVirtualCode) {
		this.lineVirtualCode = lineVirtualCode;
	}

	/**
	 * 获取 班次虚拟编码.
	 *
	 * @return the 班次虚拟编码
	 */
	public String getDepartureStandardVirtualCode() {
		return departureStandardVirtualCode;
	}

	/**
	 * 设置 班次虚拟编码.
	 *
	 * @param departureStandardVirtualCode the new 班次虚拟编码
	 */
	public void setDepartureStandardVirtualCode(String departureStandardVirtualCode) {
		this.departureStandardVirtualCode = departureStandardVirtualCode;
	}

	/**
	 * 获取 准点发车时间.
	 *
	 * @return the 准点发车时间
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}

	/**
	 * 设置 准点发车时间.
	 *
	 * @param leaveTime the new 准点发车时间
	 */
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	/**
	 * 获取 准点到达时间.
	 *
	 * @return the 准点到达时间
	 */
	public Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * 设置 准点到达时间.
	 *
	 * @param arriveTime the new 准点到达时间
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * 获取 准点到达时间的天数,默认是0.
	 *
	 * @return the 准点到达时间的天数,默认是0
	 */
	public Integer getArriveDay() {
		return arriveDay;
	}

	/**
	 * 设置 准点到达时间的天数,默认是0.
	 *
	 * @param arriveDay the new 准点到达时间的天数,默认是0
	 */
	public void setArriveDay(Integer arriveDay) {
		this.arriveDay = arriveDay;
	}

	/**
	 * 获取 出发部门编码.
	 *
	 * @return the 出发部门编码
	 */
	public String getSourceCode() {
		return sourceCode;
	}

	/**
	 * 设置 出发部门编码.
	 *
	 * @param sourceCode the new 出发部门编码
	 */
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	/**
	 * 获取 到达部门编码.
	 *
	 * @return the 到达部门编码
	 */
	public String getTargetCode() {
		return targetCode;
	}

	/**
	 * 设置 到达部门编码.
	 *
	 * @param targetCode the new 到达部门编码
	 */
	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}
    
}
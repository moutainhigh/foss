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
 *  FILE PATH          :/QueryAirArriveInfoDto.java
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
import java.util.Date;

/**
 * 空运到达派送信息录入情况统计DTO 
 * @author 099197-foss-zhoudejun
 * @date 2012-12-26 下午2:41:17
 */
public class QueryAirArriveInfoDto implements Serializable {

	private static final long serialVersionUID = -6111490726545591288L;
	
	/** 运单号 */
	private String wayBillNumber;
    
	/** 代理网点编号 */
    private String ladingStationNumber;
    
    /** 录入状态 */
    private String recordState;
    
    /** 配载部门 */
    private String departmentID;
    
    /** 出港日期起始时间 */
    private Date departureStartTime;
    
    /** 出港日期终止时间 */
    private Date departureEndTime;

	/**
	 * 获取 运单号.
	 *
	 * @return the 运单号
	 */
	public String getWayBillNumber() {
		return wayBillNumber;
	}

	/**
	 * 设置 运单号.
	 *
	 * @param wayBillNumber the new 运单号
	 */
	public void setWayBillNumber(String wayBillNumber) {
		this.wayBillNumber = wayBillNumber;
	}

	/**
	 * 获取 代理网点编号.
	 *
	 * @return the 代理网点编号
	 */
	public String getLadingStationNumber() {
		return ladingStationNumber;
	}

	/**
	 * 设置 代理网点编号.
	 *
	 * @param ladingStationNumber the new 代理网点编号
	 */
	public void setLadingStationNumber(String ladingStationNumber) {
		this.ladingStationNumber = ladingStationNumber;
	}

	/**
	 * 获取 录入状态.
	 *
	 * @return the 录入状态
	 */
	public String getRecordState() {
		return recordState;
	}

	/**
	 * 设置 录入状态.
	 *
	 * @param recordState the new 录入状态
	 */
	public void setRecordState(String recordState) {
		this.recordState = recordState;
	}

	/**
	 * 获取 配载部门.
	 *
	 * @return the 配载部门
	 */
	public String getDepartmentID() {
		return departmentID;
	}

	/**
	 * 设置 配载部门.
	 *
	 * @param departmentID the new 配载部门
	 */
	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	/**
	 * 获取 出港日期起始时间.
	 *
	 * @return the 出港日期起始时间
	 */
	public Date getDepartureStartTime() {
		return departureStartTime;
	}

	/**
	 * 设置 出港日期起始时间.
	 *
	 * @param departureStartTime the new 出港日期起始时间
	 */
	public void setDepartureStartTime(Date departureStartTime) {
		this.departureStartTime = departureStartTime;
	}

	/**
	 * 获取 出港日期终止时间.
	 *
	 * @return the 出港日期终止时间
	 */
	public Date getDepartureEndTime() {
		return departureEndTime;
	}

	/**
	 * 设置 出港日期终止时间.
	 *
	 * @param departureEndTime the new 出港日期终止时间
	 */
	public void setDepartureEndTime(Date departureEndTime) {
		this.departureEndTime = departureEndTime;
	}
    
    

}
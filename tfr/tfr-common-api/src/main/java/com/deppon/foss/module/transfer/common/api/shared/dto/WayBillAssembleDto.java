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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/dto/WayBillAssembleDto.java
 *  
 *  FILE NAME          :WayBillAssembleDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.util.Date;

/**
 * 运单配载信息,用于提供给接送货“跟踪运单”功能
 * @author 038300-foss-pengzhen
 * @date 2013-1-10 上午11:11:10
 */
public class WayBillAssembleDto {
	/**
	 * 配载车次号
	 */
    private String vehicleAssembleNo;
    /**
     * 航班号
     */
    private String flightNo;
    /**
     * 发车时间
     */
    private Date leaveTime;
    /**
     * 预到时间
     */
    private Date preArriveTime;
    
	/**
	 * 获取 配载车次号.
	 *
	 * @return the 配载车次号
	 */
	public String getVehicleAssembleNo() {
		return vehicleAssembleNo;
	}
	
	/**
	 * 设置 配载车次号.
	 *
	 * @param vehicleAssembleNo the new 配载车次号
	 */
	public void setVehicleAssembleNo(String vehicleAssembleNo) {
		this.vehicleAssembleNo = vehicleAssembleNo;
	}
	
	/**
	 * 获取 航班号.
	 *
	 * @return the 航班号
	 */
	public String getFlightNo() {
		return flightNo;
	}
	
	/**
	 * 设置 航班号.
	 *
	 * @param flightNo the new 航班号
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	
	/**
	 * 获取 发车时间.
	 *
	 * @return the 发车时间
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}
	
	/**
	 * 设置 发车时间.
	 *
	 * @param leaveTime the new 发车时间
	 */
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	
	/**
	 * 获取 预到时间.
	 *
	 * @return the 预到时间
	 */
	public Date getPreArriveTime() {
		return preArriveTime;
	}
	
	/**
	 * 设置 预到时间.
	 *
	 * @param preArriveTime the new 预到时间
	 */
	public void setPreArriveTime(Date preArriveTime) {
		this.preArriveTime = preArriveTime;
	}
    
}
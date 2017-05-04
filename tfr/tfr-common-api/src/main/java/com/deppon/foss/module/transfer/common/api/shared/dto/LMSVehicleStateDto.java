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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/dto/LMSVehicleStateDto.java
 *  
 *  FILE NAME          :LMSVehicleStateDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-common-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.api.shared.dto
 * FILE    NAME: LMSVehicleStateDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.api.shared.dto;

import java.util.Date;

/**
 * 更新LMS车辆状态需要传入的数据
 * @author 046130-foss-xuduowei
 * @date 2012-12-4 下午7:15:36
 */
public class LMSVehicleStateDto {
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 车辆状态 （出发/到达）
	 */
	private String state;
	/**
	 * 出发时间
	 */
	private Date startDate;
	/**
	 * 当前城市
	 */
	private String cityCode;
	/**
	 * 到达时间（出发时为预计到达时间，到达时为实际到达时间）
	 */
	private Date arriveDate;
	
	
	
	/**
	 * 获取 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * 设置 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
	/**
	 * 获取 车辆状态 （出发/到达）.
	 *
	 * @return the 车辆状态 （出发/到达）
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * 设置 车辆状态 （出发/到达）.
	 *
	 * @param state the new 车辆状态 （出发/到达）
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * 获取 出发时间.
	 *
	 * @return the 出发时间
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * 设置 出发时间.
	 *
	 * @param startDate the new 出发时间
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * 获取 当前城市.
	 *
	 * @return the 当前城市
	 */
	public String getCityCode() {
		return cityCode;
	}
	
	/**
	 * 设置 当前城市.
	 *
	 * @param cityCode the new 当前城市
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	/**
	 * 获取 到达时间（出发时为预计到达时间，到达时为实际到达时间）.
	 *
	 * @return the 到达时间（出发时为预计到达时间，到达时为实际到达时间）
	 */
	public Date getArriveDate() {
		return arriveDate;
	}
	
	/**
	 * 设置 到达时间（出发时为预计到达时间，到达时为实际到达时间）.
	 *
	 * @param arriveDate the new 到达时间（出发时为预计到达时间，到达时为实际到达时间）
	 */
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	
	
	
}
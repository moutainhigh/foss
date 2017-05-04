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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/UnloadTaskDto.java
 *  
 *  FILE NAME          :UnloadTaskDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadTaskEntity;

/**
 * 卸车任务Dto
 * @author ibm-zhangyixin
 * @date 2012-11-29 上午10:52:59
 */
public class UnloadTaskDto extends UnloadTaskEntity implements Serializable {
	
	private static final long serialVersionUID = -7446049114424031324L;
	
	/**开始时间**/
	private String beginDate;		

	/**结束时间**/
	private String endDate;			

	/**理货员**/
	private String loaderName;		

	/**差异报告编号**/
	private String gaprepNo;		

	/**线路**/
	private String line;			

	/**车辆到达时间**/
	private Date arriveTime;		
	
	/**单据编号*/
	private String billNo;
	
	/**卸车状态*/
	private String state;
	
	//航班号272681
	private String flightNo;
	/**
	 * 获取 开始时间*.
	 *
	 * @return the 开始时间*
	 */
	public String getBeginDate() {
		return beginDate;
	}
	
	/**
	 * 设置 开始时间*.
	 *
	 * @param beginDate the new 开始时间*
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	/**
	 * 获取 结束时间*.
	 *
	 * @return the 结束时间*
	 */
	public String getEndDate() {
		return endDate;
	}
	
	/**
	 * 设置 结束时间*.
	 *
	 * @param endDate the new 结束时间*
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * 获取 理货员*.
	 *
	 * @return the 理货员*
	 */
	public String getLoaderName() {
		return loaderName;
	}
	
	/**
	 * 设置 理货员*.
	 *
	 * @param loaderName the new 理货员*
	 */
	public void setLoaderName(String loaderName) {
		this.loaderName = loaderName;
	}
	
	/**
	 * 获取 差异报告编号*.
	 *
	 * @return the 差异报告编号*
	 */
	public String getGaprepNo() {
		return gaprepNo;
	}
	
	/**
	 * 设置 差异报告编号*.
	 *
	 * @param gaprepNo the new 差异报告编号*
	 */
	public void setGaprepNo(String gaprepNo) {
		this.gaprepNo = gaprepNo;
	}
	
	/**
	 * 获取 线路*.
	 *
	 * @return the 线路*
	 */
	public String getLine() {
		return line;
	}
	
	/**
	 * 设置 线路*.
	 *
	 * @param line the new 线路*
	 */
	public void setLine(String line) {
		this.line = line;
	}
	
	/**
	 * 获取 车辆到达时间*.
	 *
	 * @return the 车辆到达时间*
	 */
	public Date getArriveTime() {
		return arriveTime;
	}
	
	/**
	 * 设置 车辆到达时间*.
	 *
	 * @param arriveTime the new 车辆到达时间*
	 */
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
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
 *  PROJECT NAME  : tfr-arrival-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/arrival/api/shared/dto/LeasedTruckDTO.java
 *  
 *  FILE NAME          :LeasedTruckDTO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.arrival.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 操作到达记录时，传值到后台进行验证，操作
 * 
 * @author IBM-liubinbin
 * @date 2012-11-5 下午4:11:08
 */
public class LeasedTruckDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/************* 开始时间 ****************/
	private Date startTime;
	
	/**************结束时间*************/
	private Date endTime;
	
	/**************外请车预计到达个数*************/
	private BigDecimal totalTrucks;
	
	/**************未结清金额合计（元）*************/
	private BigDecimal totalFee;





	/**
	 * 获取 *********** 开始时间 ***************.
	 *
	 * @return the *********** 开始时间 ***************
	 */
	public Date getStartTime(){
		return startTime;
	}

	/**
	 * 设置 *********** 开始时间 ***************.
	 *
	 * @param startTime the new *********** 开始时间 ***************
	 */
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}

	/**
	 * 获取 ************结束时间************.
	 *
	 * @return the ************结束时间************
	 */
	public Date getEndTime(){
		return endTime;
	}

	/**
	 * 设置 ************结束时间************.
	 *
	 * @param endTime the new ************结束时间************
	 */
	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}

	/**
	 * 获取 ************外请车预计到达个数************.
	 *
	 * @return the ************外请车预计到达个数************
	 */
	public BigDecimal getTotalTrucks(){
		return totalTrucks;
	}

	/**
	 * 设置 ************外请车预计到达个数************.
	 *
	 * @param totalTrucks the new ************外请车预计到达个数************
	 */
	public void setTotalTrucks(BigDecimal totalTrucks){
		this.totalTrucks = totalTrucks;
	}

	/**
	 * 获取 ************未结清金额合计（元）************.
	 *
	 * @return the ************未结清金额合计（元）************
	 */
	public BigDecimal getTotalFee(){
		return totalFee;
	}

	/**
	 * 设置 ************未结清金额合计（元）************.
	 *
	 * @param totalFee the new ************未结清金额合计（元）************
	 */
	public void setTotalFee(BigDecimal totalFee){
		this.totalFee = totalFee;
	}


}
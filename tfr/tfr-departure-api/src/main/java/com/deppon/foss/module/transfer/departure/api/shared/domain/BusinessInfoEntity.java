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
 *  PROJECT NAME  : tfr-departure-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/domain/BusinessInfoEntity.java
 *  
 *  FILE NAME          :BusinessInfoEntity.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.domain;

import java.math.BigDecimal;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 */
public class BusinessInfoEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********任务车辆类型************/
	private String businessType;
	
	/**********线路************/
	private String lineNo;
	
	/********** 运行时长(小时)************/
	private String runningTime;
	
	/********** 重量(千克)************/
	private BigDecimal weight = new BigDecimal(0);
	
	/**********体积(方)************/
	private BigDecimal volume = new BigDecimal(0);
	
	/**********数量************/
	private int waybill;

	/**
	 * 获取 ********任务车辆类型***********.
	 *
	 * @return the ********任务车辆类型***********
	 */
	public String getBusinessType(){
		return businessType;
	}

	/**
	 * 设置 ********任务车辆类型***********.
	 *
	 * @param businessType the new ********任务车辆类型***********
	 */
	public void setBusinessType(String businessType){
		this.businessType = businessType;
	}

	/**
	 * 获取 ********线路***********.
	 *
	 * @return the ********线路***********
	 */
	public String getLineNo(){
		return lineNo;
	}

	/**
	 * 设置 ********线路***********.
	 *
	 * @param lineNo the new ********线路***********
	 */
	public void setLineNo(String lineNo){
		this.lineNo = lineNo;
	}

	/**
	 * 获取 ******** 运行时长(小时)***********.
	 *
	 * @return the ******** 运行时长(小时)***********
	 */
	public String getRunningTime(){
		return runningTime;
	}

	/**
	 * 设置 ******** 运行时长(小时)***********.
	 *
	 * @param runningTime the new ******** 运行时长(小时)***********
	 */
	public void setRunningTime(String runningTime){
		this.runningTime = runningTime;
	}

	/**
	 * 获取 ******** 重量(千克)***********.
	 *
	 * @return the ******** 重量(千克)***********
	 */
	public BigDecimal getWeight(){
		return weight;
	}

	/**
	 * 设置 ******** 重量(千克)***********.
	 *
	 * @param weight the new ******** 重量(千克)***********
	 */
	public void setWeight(BigDecimal weight){
		this.weight = weight;
	}

	/**
	 * 获取 ********体积(方)***********.
	 *
	 * @return the ********体积(方)***********
	 */
	public BigDecimal getVolume(){
		return volume;
	}

	/**
	 * 设置 ********体积(方)***********.
	 *
	 * @param volume the new ********体积(方)***********
	 */
	public void setVolume(BigDecimal volume){
		this.volume = volume;
	}

	/**
	 * 获取 ********数量***********.
	 *
	 * @return the ********数量***********
	 */
	public int getWaybill(){
		return waybill;
	}

	/**
	 * 设置 ********数量***********.
	 *
	 * @param waybill the new ********数量***********
	 */
	public void setWaybill(int waybill){
		this.waybill = waybill;
	}
}
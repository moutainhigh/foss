/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/shared/dto/PdaDeliverTaskConditionDto.java
 * 
 * FILE NAME        	: PdaDeliverTaskConditionDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 查询送货任务查询条件DTO
 * @author 097972-foss-dengtingting
 * @date 2012-12-13 上午9:52:09
 */
public class PdaDeliverTaskConditionDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  派送单状态
	 */
	private String deliverbillStatus;
	/**
	 *  司机编号
	 */
	private String driverCode;
	/**
	 *  车牌号
	 */
	private String vehicleNo;
	/**
	 *  到达联是否有效
	 */
	private String arriveSheetActive;
	/**
	 *  到达联状态
	 */
	private String arriveSheetStatus;
	/**
	 *  派送单号
	 */
	private String deliverbillNo;
	/**
	 * 到达联是否作废
	 */
	private String arriveSheetDestroyed;
	/**
	 * 货物状态
	 */
	private List<String> goodsStates;
	/**
	 * 
	 */
	private String taskState;
	/**
	 * 用户类型  用来区分零担跟快递
	 */
	private String userType;
	
	/**
	 * 派送单状态(用于快递)
	 */
	private List<String> deliverbillStatusList;
	
	/**
	 *  是否显示联系电话 （运单）收货客户固定电话
	 * @author yangqiang 309603
	 *	 N 不显示 （默认）     Y 显示
	 */
	private String showPhoneNo="N";

	/**
	 * Gets the 派送单状态.
	 *
	 * @return the 派送单状态
	 */
	public String getDeliverbillStatus() {
		return deliverbillStatus;
	}

	/**
	 * Sets the 派送单状态.
	 *
	 * @param deliverbillStatus the new 派送单状态
	 */
	public void setDeliverbillStatus(String deliverbillStatus) {
		this.deliverbillStatus = deliverbillStatus;
	}

	/**
	 * Gets the 司机编号.
	 *
	 * @return the 司机编号
	 */
	public String getDriverCode() {
		return driverCode;
	}

	/**
	 * Sets the 司机编号.
	 *
	 * @param driverCode the new 司机编号
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}

	/**
	 * Gets the 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * Sets the 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * Gets the 到达联是否有效.
	 *
	 * @return the 到达联是否有效
	 */
	public String getArriveSheetActive() {
		return arriveSheetActive;
	}

	/**
	 * Sets the 到达联是否有效.
	 *
	 * @param arriveSheetActive the new 到达联是否有效
	 */
	public void setArriveSheetActive(String arriveSheetActive) {
		this.arriveSheetActive = arriveSheetActive;
	}

	/**
	 * Gets the 到达联状态.
	 *
	 * @return the 到达联状态
	 */
	public String getArriveSheetStatus() {
		return arriveSheetStatus;
	}

	/**
	 * Sets the 到达联状态.
	 *
	 * @param arriveSheetStatus the new 到达联状态
	 */
	public void setArriveSheetStatus(String arriveSheetStatus) {
		this.arriveSheetStatus = arriveSheetStatus;
	}

	/**
	 * Gets the 派送单号.
	 *
	 * @return the 派送单号
	 */
	public String getDeliverbillNo() {
		return deliverbillNo;
	}

	/**
	 * Sets the 派送单号.
	 *
	 * @param deliverbillNo the new 派送单号
	 */
	public void setDeliverbillNo(String deliverbillNo) {
		this.deliverbillNo = deliverbillNo;
	}

	public String getArriveSheetDestroyed() {
		return arriveSheetDestroyed;
	}

	public void setArriveSheetDestroyed(String arriveSheetDestroyed) {
		this.arriveSheetDestroyed = arriveSheetDestroyed;
	}

	public List<String> getGoodsStates() {
		return goodsStates;
	}

	public void setGoodsStates(List<String> goodsStates) {
		this.goodsStates = goodsStates;
	}

	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<String> getDeliverbillStatusList() {
		return deliverbillStatusList;
	}

	public void setDeliverbillStatusList(List<String> deliverbillStatusList) {
		this.deliverbillStatusList = deliverbillStatusList;
	}

	public String getShowPhoneNo() {
		return showPhoneNo;
	}

	public void setShowPhoneNo(String showPhoneNo) {
		this.showPhoneNo = showPhoneNo;
	}
}
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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/AssignLoadTaskQueryConditionDto.java
 *  
 *  FILE NAME          :AssignLoadTaskQueryConditionDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
/**
 * AssignLoadTaskQueryConditionDto
 * @author dp-duyi
 * @date 2012-12-24 上午11:03:22
 */
public class AssignLoadTaskQueryConditionDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8705585426447139528L;
	/**单据编号*/
	private String billNo;
	/**车牌号*/
	private String vehicleNo;
	/**任务开始时间*/
	private String assignTimeBegin;
	/**任务结束时间*/
	private String assignTimeEnd;
	/**任务状态*/
	private String taskState;
	/**理货员编号*/
	private String loaderCode;
	/**登录部门*/
	private String loginOrgCode;
	
	/**
	 * Gets the 单据编号.
	 *
	 * @return the 单据编号
	 */
	public String getBillNo() {
		return billNo;
	}
	
	/**
	 * Sets the 单据编号.
	 *
	 * @param billNo the new 单据编号
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
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
	 * Gets the 任务开始时间.
	 *
	 * @return the 任务开始时间
	 */
	public String getAssignTimeBegin() {
		return assignTimeBegin;
	}
	
	/**
	 * Sets the 任务开始时间.
	 *
	 * @param assignTimeBegin the new 任务开始时间
	 */
	public void setAssignTimeBegin(String assignTimeBegin) {
		this.assignTimeBegin = assignTimeBegin;
	}
	
	/**
	 * Gets the 任务结束时间.
	 *
	 * @return the 任务结束时间
	 */
	public String getAssignTimeEnd() {
		return assignTimeEnd;
	}
	
	/**
	 * Sets the 任务结束时间.
	 *
	 * @param assignTimeEnd the new 任务结束时间
	 */
	public void setAssignTimeEnd(String assignTimeEnd) {
		this.assignTimeEnd = assignTimeEnd;
	}
	
	/**
	 * Gets the 任务状态.
	 *
	 * @return the 任务状态
	 */
	public String getTaskState() {
		return taskState;
	}
	
	/**
	 * Sets the 任务状态.
	 *
	 * @param taskState the new 任务状态
	 */
	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	
	/**
	 * Gets the 理货员编号.
	 *
	 * @return the 理货员编号
	 */
	public String getLoaderCode() {
		return loaderCode;
	}
	
	/**
	 * Sets the 理货员编号.
	 *
	 * @param loaderCode the new 理货员编号
	 */
	public void setLoaderCode(String loaderCode) {
		this.loaderCode = loaderCode;
	}
	
	/**
	 * Gets the 登录部门.
	 *
	 * @return the 登录部门
	 */
	public String getLoginOrgCode() {
		return loginOrgCode;
	}
	
	/**
	 * Sets the 登录部门.
	 *
	 * @param loginOrgCode the new 登录部门
	 */
	public void setLoginOrgCode(String loginOrgCode) {
		this.loginOrgCode = loginOrgCode;
	}
	
}
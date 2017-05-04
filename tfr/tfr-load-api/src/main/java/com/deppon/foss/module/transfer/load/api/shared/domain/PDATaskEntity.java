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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/domain/PDATaskEntity.java
 *  
 *  FILE NAME          :PDATaskEntity.java
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
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.domain
 * FILE    NAME: PDATaskEntity.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * pda参与装卸车情况
 * @author dp-duyi
 * @date 2012-12-3 下午8:22:38
 */
public class PDATaskEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3378205718143836217L;
	/**id*/
	private String id;
	/**加入时间*/
	private Date joinTime;
	/**离开时间*/
	private Date leaveTime;
	/**设备编号*/
	private String deviceNo;
	/**是否创建人*/
	private String beCreator;
	/**任务类型*/
	private String taskType;
	/**任务编号*/
	private String taskNo;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the 加入时间.
	 *
	 * @return the 加入时间
	 */
	public Date getJoinTime() {
		return joinTime;
	}
	
	/**
	 * Sets the 加入时间.
	 *
	 * @param joinTime the new 加入时间
	 */
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	
	/**
	 * Gets the 离开时间.
	 *
	 * @return the 离开时间
	 */
	public Date getLeaveTime() {
		return leaveTime;
	}
	
	/**
	 * Sets the 离开时间.
	 *
	 * @param leaveTime the new 离开时间
	 */
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	
	/**
	 * Gets the 设备编号.
	 *
	 * @return the 设备编号
	 */
	public String getDeviceNo() {
		return deviceNo;
	}
	
	/**
	 * Sets the 设备编号.
	 *
	 * @param deviceNo the new 设备编号
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	
	/**
	 * Gets the 是否创建人.
	 *
	 * @return the 是否创建人
	 */
	public String getBeCreator() {
		return beCreator;
	}
	
	/**
	 * Sets the 是否创建人.
	 *
	 * @param beCreator the new 是否创建人
	 */
	public void setBeCreator(String beCreator) {
		this.beCreator = beCreator;
	}
	
	/**
	 * Gets the 任务类型.
	 *
	 * @return the 任务类型
	 */
	public String getTaskType() {
		return taskType;
	}
	
	/**
	 * Sets the 任务类型.
	 *
	 * @param taskType the new 任务类型
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	/**
	 * Gets the 任务编号.
	 *
	 * @return the 任务编号
	 */
	public String getTaskNo() {
		return taskNo;
	}
	
	/**
	 * Sets the 任务编号.
	 *
	 * @param taskNo the new 任务编号
	 */
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
}
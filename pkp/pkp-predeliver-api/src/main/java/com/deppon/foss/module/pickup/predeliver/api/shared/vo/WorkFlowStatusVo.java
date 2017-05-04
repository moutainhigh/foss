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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/shared/vo/NotifyCustomerVo.java
 * 
 * FILE NAME        	: NotifyCustomerVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.shared.vo;

import java.io.Serializable;

/**
 * 工作流状态Vo
 * 
 * @author 136892
 */
public class WorkFlowStatusVo implements Serializable {



	private static final long serialVersionUID = -9107023127042404606L;

	/**
	 * 当前状态
	 */
	public String currentStatus;

	/**
	 * 当前审批人
	 */
	public String partiName;

	/**
	 * 权责
	 */
	public String duty;

	/**
	 * 审批结果
	 */
	public String result;

	/**
	 * 工作流号
	 */
	public String flowCode;

	/**
	 * 活动定义ID
	 */
	public String activityDefId;


	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getPartiName() {
		return partiName;
	}

	public void setPartiName(String partiName) {
		this.partiName = partiName;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getFlowCode() {
		return flowCode;
	}

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

	public String getActivityDefId() {
		return activityDefId;
	}

	public void setActivityDefId(String activityDefId) {
		this.activityDefId = activityDefId;
	}
	
	

}
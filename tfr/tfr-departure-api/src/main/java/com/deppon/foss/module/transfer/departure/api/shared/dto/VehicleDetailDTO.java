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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/dto/VehicleDetailDTO.java
 *  
 *  FILE NAME          :VehicleDetailDTO.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.departure.api.shared.dto;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 
 */
public class VehicleDetailDTO  extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********放行ID************/
	private String departId;
	
	/**********任务明细ID************/
	private String taskDetailId;

	/**
	 * 获取 ********放行ID***********.
	 *
	 * @return the ********放行ID***********
	 */
	public String getDepartId()
	{
		return departId;
	}

	/**
	 * 设置 ********放行ID***********.
	 *
	 * @param departId the new ********放行ID***********
	 */
	public void setDepartId(String departId)
	{
		this.departId = departId;
	}

	/**
	 * 获取 ********任务明细ID***********.
	 *
	 * @return the ********任务明细ID***********
	 */
	public String getTaskDetailId()
	{
		return taskDetailId;
	}

	/**
	 * 设置 ********任务明细ID***********.
	 *
	 * @param taskDetailId the new ********任务明细ID***********
	 */
	public void setTaskDetailId(String taskDetailId)
	{
		this.taskDetailId = taskDetailId;
	}
	
	
	
}
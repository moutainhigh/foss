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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/departure/api/shared/dto/DriverInfoForTaskDTO.java
 *  
 *  FILE NAME          :DriverInfoForTaskDTO.java
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
 * LMS任务放行时录入司机的信息
 * @author foss-liubinbin(for IBM)
 * @date 2012-11-21 下午5:06:13
 */
public class DriverInfoForTaskDTO extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3703272448562684594L;

	/**********司机编号************/
	private String driverCode;
	
	/**********联系方式************/
	private String driverPhone;

	/**
	 * 获取 ********司机编号***********.
	 *
	 * @return the ********司机编号***********
	 */
	public String getDriverCode(){
		return driverCode;
	}

	/**
	 * 设置 ********司机编号***********.
	 *
	 * @param driverCode the new ********司机编号***********
	 */
	public void setDriverCode(String driverCode){
		this.driverCode = driverCode;
	}

	/**
	 * 获取 ********联系方式***********.
	 *
	 * @return the ********联系方式***********
	 */
	public String getDriverPhone(){
		return driverPhone;
	}

	/**
	 * 设置 ********联系方式***********.
	 *
	 * @param driverPhone the new ********联系方式***********
	 */
	public void setDriverPhone(String driverPhone){
		this.driverPhone = driverPhone;
	}
	
	
}
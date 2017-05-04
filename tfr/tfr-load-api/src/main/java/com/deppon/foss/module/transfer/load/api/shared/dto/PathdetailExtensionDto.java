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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/LoadTaskSerialNoDto.java
 *  
 *  FILE NAME          :LoadTaskSerialNoDto.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.dto
 * FILE    NAME: LoadTaskSerialnoDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.load.api.shared.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 走货路径查询出发时间，到达时间Dto
 * 
 * @author 336785
 * @date 2012-11-20 下午3:00:05
 */
public class PathdetailExtensionDto implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	/** 出发部门编码 */
	private String origOrgCode;
	/** 到达部门CODE */
	private String objectiveOrgCode;
	/** 实际出发时间 */
	private Date actualStartTime;
	/** 实际到达时间 */
	private Date actualArriveTime;
	/** 车牌号 */
	private String vehicleNo;

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}
	
	public String getObjectiveOrgCode() {
		return objectiveOrgCode;
	}

	public void setObjectiveOrgCode(String objectiveOrgCode) {
		this.objectiveOrgCode = objectiveOrgCode;
	}

	public Date getActualStartTime() {
		return actualStartTime;
	}

	public void setActualStartTime(Date actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public Date getActualArriveTime() {
		return actualArriveTime;
	}

	public void setActualArriveTime(Date actualArriveTime) {
		this.actualArriveTime = actualArriveTime;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
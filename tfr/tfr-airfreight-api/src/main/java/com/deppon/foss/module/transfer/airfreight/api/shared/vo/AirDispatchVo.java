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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/api/shared/vo/AirDispatchVo.java
 *  
 *  FILE NAME          :AirDispatchVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirDispatchDto;

/**
 * 空运总调vo
 * @author foss-liuxue(for IBM)
 * @date 2013-1-30 上午9:50:48
 */
public class AirDispatchVo implements Serializable {

	private static final long serialVersionUID = 6016773746844511532L;
	
	/**
	 * 部门代码
	 */
	private String deptCode;
	
	/**
	 * 空运总调dto
	 */
	private AirDispatchDto airDispatchDto;
	
	/**
	 * 空运总调 部门
	 */
	private OrgAdministrativeInfoEntity orgAdministrativeInfoEntity;

	
	/**
	 * 部门代码
	 */
	public String getDeptCode() {
		return deptCode;
	}
	
	/**
	 * 部门代码
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * 获取 空运总调dto.
	 *
	 * @return the 空运总调dto
	 */
	public AirDispatchDto getAirDispatchDto() {
		return airDispatchDto;
	}

	/**
	 * 设置 空运总调dto.
	 *
	 * @param airDispatchDto the new 空运总调dto
	 */
	public void setAirDispatchDto(AirDispatchDto airDispatchDto) {
		this.airDispatchDto = airDispatchDto;
	}

	/**
	 * 获取空运总调 部门
	 */
	public OrgAdministrativeInfoEntity getOrgAdministrativeInfoEntity() {
		return orgAdministrativeInfoEntity;
	}

	/**
	 * 设置空运总调 部门
	 */
	public void setOrgAdministrativeInfoEntity(
			OrgAdministrativeInfoEntity orgAdministrativeInfoEntity) {
		this.orgAdministrativeInfoEntity = orgAdministrativeInfoEntity;
	}
	
	

}
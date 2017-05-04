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
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/shared/dto/CurrentDeptDto.java
 *  
 *  FILE NAME          :CurrentDeptDto.java
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
 * PROJECT NAME: tfr-package-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.api.shared.dto
 * FILE    NAME: CurrentDeptDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.shared.dto;

// TODO: Auto-generated Javadoc
/**
 * 当前部门信息.
 *
 * @author 046130-foss-xuduowei
 * @date 2012-11-26 下午8:00:06
 */
public class CurrentDeptDto {

	/** 
	 * 部门code.
	 */
	private String deptCode;
	
	/** 
	 * 部门名称
	 */
	private String deptName;
	
	
	
	/**
	 * 获取 部门code.
	 *
	 * @return the 部门code
	 */
	public String getDeptCode() {
		return deptCode;
	}
	

	/**
	 * 设置 部门code.
	 *
	 * @param deptCode the new 部门code
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	

	/**
	 * 获取 部门名称.
	 *
	 * @return the 部门名称
	 */
	public String getDeptName() {
		return deptName;
	}
	

	/**
	 * 设置 部门名称.
	 *
	 * @param deptName the new 部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	
}
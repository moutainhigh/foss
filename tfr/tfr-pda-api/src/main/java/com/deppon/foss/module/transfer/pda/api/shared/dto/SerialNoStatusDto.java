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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/SerialNoStatusDto.java
 *  
 *  FILE NAME          :SerialNoStatusDto.java
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
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.shared.dto
 * FILE    NAME: SerialNoStatusDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

/**
 * PDA查询未包装的货物时显示每件货物的状态
 * @author 046130-foss-xuduowei
 * @date 2013-1-9 下午4:05:22
 */
public class SerialNoStatusDto {
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 包装类型
	 */
	private String packageType;
	
	/**
	 * 获取 流水号.
	 *
	 * @return the 流水号
	 */
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	 * 设置 流水号.
	 *
	 * @param serialNo the new 流水号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * 获取 状态.
	 *
	 * @return the 状态
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * 设置 状态.
	 *
	 * @param status the new 状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 包装类型
	 * @return
	 */
	public String getPackageType() {
		return packageType;
	}

	/**
	 * 包装类型
	 * @param packageType
	 */
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	
	
}
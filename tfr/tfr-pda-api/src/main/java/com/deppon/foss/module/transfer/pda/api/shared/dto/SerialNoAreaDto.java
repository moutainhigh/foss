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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/SerialNoAreaDto.java
 *  
 *  FILE NAME          :SerialNoAreaDto.java
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
 * FILE    NAME: SerialNoAreaDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

/**
 * 根据运单号查询货件当前库存区域
 * @author 046130-foss-xuduowei
 * @date 2013-1-9 下午4:44:02
 */
public class SerialNoAreaDto {
	/**
	 * 流水号
	 */
	private String serialNo;
	/**
	 * 部门code
	 */
	private String orgCode;
	/**
	 * 货区code
	 */
	private String areaCode;
	/**
	 * 是否已包装
	 */
	private String isPacked;
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
	 * 获取 部门code.
	 *
	 * @return the 部门code
	 */
	public String getOrgCode() {
		return orgCode;
	}
	
	/**
	 * 设置 部门code.
	 *
	 * @param orgCode the new 部门code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * 获取 货区code.
	 *
	 * @return the 货区code
	 */
	public String getAreaCode() {
		return areaCode;
	}
	
	/**
	 * 设置 货区code.
	 *
	 * @param areaCode the new 货区code
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getIsPacked() {
		return isPacked;
	}

	public void setIsPacked(String isPacked) {
		this.isPacked = isPacked;
	}

	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	
	
}
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
 *  PROJECT NAME  : tfr-unload-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/UnloadBillDetailDto.java
 *  
 *  FILE NAME          :UnloadBillDetailDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 *  
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-unload-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.unload.api.shared.dto
 * FILE    NAME: UnloadBillDetailDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import com.deppon.foss.module.transfer.unload.api.shared.domain.UnloadBillDetailEntity;

/**
 * 卸车任务单据明细DTO
 * @author dp-duyi
 * @date 2012-12-17 下午4:16:50
 */
public class UnloadBillDetailDto extends UnloadBillDetailEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6372362080049628013L;
	/**单据状态*/
	private String billState;
	/**业务类型*/
	private String businessType;
	private String origOrgCode;
	private String destOrgCode;
	/**卸车状态272681*/
	private String unloadState;
	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public String getDestOrgCode() {
		return destOrgCode;
	}

	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	/**
	 * Gets the 单据状态.
	 *
	 * @return the 单据状态
	 */
	public String getBillState() {
		return billState;
	}
	
	/**
	 * Sets the 单据状态.
	 *
	 * @param billState the new 单据状态
	 */
	public void setBillState(String billState) {
		this.billState = billState;
	}
	
	/**
	 * Gets the 业务类型.
	 *
	 * @return the 业务类型
	 */
	public String getBusinessType() {
		return businessType;
	}
	
	/**
	 * Sets the 业务类型.
	 *
	 * @param businessType the new 业务类型
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getUnloadState() {
		return unloadState;
	}

	public void setUnloadState(String unloadState) {
		this.unloadState = unloadState;
	}
}
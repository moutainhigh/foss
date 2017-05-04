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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/UnloadTaskDto.java
 *  
 *  FILE NAME          :UnloadTaskDto.java
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
 * FILE    NAME: UnLoadTaskDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.util.List;

/**
 * PDA建立卸车任务
 * @author dp-duyi
 * @date 2012-12-17 下午1:55:08
 */
public class UnloadTaskDto extends PDATaskDto{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3107539425792929841L;
	/**交接单/配载单*/
	private List<String> billNos;                      
	/**操作部门编号*/
	private String operatorOrgCode;                      
	
	/**
	 * Gets the 交接单/配载单.
	 *
	 * @return the 交接单/配载单
	 */
	public List<String> getBillNos() {
		return billNos;
	}
	
	/**
	 * Sets the 交接单/配载单.
	 *
	 * @param billNos the new 交接单/配载单
	 */
	public void setBillNos(List<String> billNos) {
		this.billNos = billNos;
	}
	
	/**
	 * Gets the 操作部门编号.
	 *
	 * @return the 操作部门编号
	 */
	public String getOperatorOrgCode() {
		return operatorOrgCode;
	}
	
	/**
	 * Sets the 操作部门编号.
	 *
	 * @param operatorOrgCode the new 操作部门编号
	 */
	public void setOperatorOrgCode(String operatorOrgCode) {
		this.operatorOrgCode = operatorOrgCode;
	}
}
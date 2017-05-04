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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/UnloadTaskResultDto.java
 *  
 *  FILE NAME          :UnloadTaskResultDto.java
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
 * FILE    NAME: UnloadTaskResultDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

/**
 * 建立卸车任务返回给PDA的Ｄｔｏ
 * @author dp-duyi
 * @date 2013-3-6 上午8:30:01
 */
public class UnloadTaskResultDto extends TaskResultDto{
	private static final long serialVersionUID = 4932014758167121406L;
	
	/**
	 * 卸车类型
	 */
	private String unloadType;

	/**
	 * Gets the 卸车类型.
	 *
	 * @return the 卸车类型
	 */
	public String getUnloadType() {
		return unloadType;
	}

	/**
	 * Sets the 卸车类型.
	 *
	 * @param unloadType the new 卸车类型
	 */
	public void setUnloadType(String unloadType) {
		this.unloadType = unloadType;
	}
}
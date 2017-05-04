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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/FinishPackResultDto.java
 *  
 *  FILE NAME          :FinishPackResultDto.java
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
 * FILE    NAME: FinishPackResultDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.shared.dto;

/**
 * 完成包装返回结果
 * @author 046130-foss-xuduowei
 * @date 2012-12-3 下午2:23:44
 */
public class FinishPackResultDto {
	/**
	 * 是否成功
	 */
	private boolean isSuccess;
	/**
	 * 新标签号
	 */
	private String newSerialNo;
	
	/**
	 * 
	 *
	 * @return 
	 */
	public boolean isSuccess() {
		return isSuccess;
	}
	
	/**
	 * 
	 *
	 * @param isSuccess 
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	/**
	 * 获取 新标签号.
	 *
	 * @return the 新标签号
	 */
	public String getNewSerialNo() {
		return newSerialNo;
	}
	
	/**
	 * 设置 新标签号.
	 *
	 * @param newSerialNo the new 新标签号
	 */
	public void setNewSerialNo(String newSerialNo) {
		this.newSerialNo = newSerialNo;
	}
	
	
}
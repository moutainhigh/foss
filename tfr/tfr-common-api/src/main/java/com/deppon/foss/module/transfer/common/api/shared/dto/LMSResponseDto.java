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
 *  PROJECT NAME  : tfr-common-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/common/api/shared/dto/LMSResponseDto.java
 *  
 *  FILE NAME          :LMSResponseDto.java
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
 * PROJECT NAME: tfr-common-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.common.api.shared.dto
 * FILE    NAME: LMSResponseDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.common.api.shared.dto;

/**
 * 调用LMS接口返回值
 * @author 046130-foss-xuduowei
 * @date 2012-12-4 下午7:20:01
 */
public class LMSResponseDto {
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 成功或失败
	 */
	private boolean isSuccess;
	/**
	 * 失败原因
	 */
	private String failureReason;
	
	/**
	 * 获取 车牌号.
	 *
	 * @return the 车牌号
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	
	/**
	 * 设置 车牌号.
	 *
	 * @param vehicleNo the new 车牌号
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	
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
	 * 获取 失败原因.
	 *
	 * @return the 失败原因
	 */
	public String getFailureReason() {
		return failureReason;
	}
	
	/**
	 * 设置 失败原因.
	 *
	 * @param failureReason the new 失败原因
	 */
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	
	
	
}
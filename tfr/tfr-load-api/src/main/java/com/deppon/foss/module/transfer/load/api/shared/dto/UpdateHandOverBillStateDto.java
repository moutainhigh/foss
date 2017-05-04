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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/UpdateHandOverBillStateDto.java
 *  
 *  FILE NAME          :UpdateHandOverBillStateDto.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.dto;
/** 
 * @className: UpdateHandOverBillStateDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 更新交接单状态dto
 * @date: 2012-11-2 上午11:21:20
 * 
 */
public class UpdateHandOverBillStateDto {
	//交接单号
	private String handOverBillNo;
	//目标状态
	private int targetState;
	//可否跳过状态规则校验
	private String beJump;
	
	public String getBeJump() {
		return beJump;
	}
	public void setBeJump(String beJump) {
		this.beJump = beJump;
	}
	public String getHandOverBillNo() {
		return handOverBillNo;
	}
	public void setHandOverBillNo(String handOverBillNo) {
		this.handOverBillNo = handOverBillNo;
	}
	public int getTargetState() {
		return targetState;
	}
	public void setTargetState(int targetState) {
		this.targetState = targetState;
	}
	

}
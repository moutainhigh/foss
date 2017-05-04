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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/unload/api/shared/dto/ConfirmUnloadTaskDto.java
 *  
 *  FILE NAME          :ConfirmUnloadTaskDto.java
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
package com.deppon.foss.module.transfer.unload.api.shared.dto;

import java.io.Serializable;
import java.util.List;

/** 
 * @className: ConfirmUnloadTaskDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 确认卸车任务时，提交至后台的dto
 * @date: 2012-12-19 下午6:53:54
 * 
 */
public class ConfirmUnloadTaskDto implements Serializable {

	private static final long serialVersionUID = -5238021872310193953L;
	
	//卸车任务ID
	private String unloadTaskId;
	//卸车任务编号
	private String unloadTaskNo;
	//少货配载单
	private List<ConfirmUnloadTaskBillsDto> lackVehicleAssembleBillList;
	//少货交接单
	private List<ConfirmUnloadTaskBillsDto> lackHandOverBillList;
	//少货运单
	private List<ConfirmUnloadTaskBillsDto> lackWaybillList;
	//少货流水号
	private List<ConfirmUnloadTaskBillsDto> lackSerialNoList;
	//多货流水号list
	private List<ConfirmUnloadTaskBillsDto> moreSerialNoList;
	
	public List<ConfirmUnloadTaskBillsDto> getMoreSerialNoList() {
		return moreSerialNoList;
	}
	public void setMoreSerialNoList(List<ConfirmUnloadTaskBillsDto> moreSerialNoList) {
		this.moreSerialNoList = moreSerialNoList;
	}
	public String getUnloadTaskId() {
		return unloadTaskId;
	}
	public void setUnloadTaskId(String unloadTaskId) {
		this.unloadTaskId = unloadTaskId;
	}
	public String getUnloadTaskNo() {
		return unloadTaskNo;
	}
	public void setUnloadTaskNo(String unloadTaskNo) {
		this.unloadTaskNo = unloadTaskNo;
	}
	public List<ConfirmUnloadTaskBillsDto> getLackVehicleAssembleBillList() {
		return lackVehicleAssembleBillList;
	}
	public void setLackVehicleAssembleBillList(
			List<ConfirmUnloadTaskBillsDto> lackVehicleAssembleBillList) {
		this.lackVehicleAssembleBillList = lackVehicleAssembleBillList;
	}
	public List<ConfirmUnloadTaskBillsDto> getLackHandOverBillList() {
		return lackHandOverBillList;
	}
	public void setLackHandOverBillList(
			List<ConfirmUnloadTaskBillsDto> lackHandOverBillList) {
		this.lackHandOverBillList = lackHandOverBillList;
	}
	public List<ConfirmUnloadTaskBillsDto> getLackWaybillList() {
		return lackWaybillList;
	}
	public void setLackWaybillList(List<ConfirmUnloadTaskBillsDto> lackWaybillList) {
		this.lackWaybillList = lackWaybillList;
	}
	public List<ConfirmUnloadTaskBillsDto> getLackSerialNoList() {
		return lackSerialNoList;
	}
	public void setLackSerialNoList(List<ConfirmUnloadTaskBillsDto> lackSerialNoList) {
		this.lackSerialNoList = lackSerialNoList;
	}
	
}
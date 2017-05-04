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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/UpdateVehicleAssembleBillDto.java
 *  
 *  FILE NAME          :UpdateVehicleAssembleBillDto.java
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

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity;

/** 
 * @className: UpdateVehicleAssembleBillDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 用于接收配载单修改信息
 * @date: 2012-11-19 下午3:01:53
 * 
 */
public class UpdateVehicleAssembleBillDto implements Serializable{
	
	private static final long serialVersionUID = 715316531650454774L;
	
	//接收修改后的配载单基本信息
	private VehicleAssembleBillEntity baseEntity;
	//接收基本信息修改日志
	private String baseInfoOptLog;
	//接收新增的交接单列表
	private List<VehicleAssembleBillDetailEntity> addedHandOverBillList;
	//接收删除的交接单列表
	private List<VehicleAssembleBillDetailEntity> deletedHandOverBillList;
	//接收所有的交接单列表
	private List<VehicleAssembleBillDetailEntity> handoverBillList;
	
	public VehicleAssembleBillEntity getBaseEntity() {
		return baseEntity;
	}
	public void setBaseEntity(VehicleAssembleBillEntity baseEntity) {
		this.baseEntity = baseEntity;
	}
	public String getBaseInfoOptLog() {
		return baseInfoOptLog;
	}
	public void setBaseInfoOptLog(String baseInfoOptLog) {
		this.baseInfoOptLog = baseInfoOptLog;
	}
	public List<VehicleAssembleBillDetailEntity> getAddedHandOverBillList() {
		return addedHandOverBillList;
	}
	public void setAddedHandOverBillList(
			List<VehicleAssembleBillDetailEntity> addedHandOverBillList) {
		this.addedHandOverBillList = addedHandOverBillList;
	}
	public List<VehicleAssembleBillDetailEntity> getDeletedHandOverBillList() {
		return deletedHandOverBillList;
	}
	public void setDeletedHandOverBillList(
			List<VehicleAssembleBillDetailEntity> deletedHandOverBillList) {
		this.deletedHandOverBillList = deletedHandOverBillList;
	}
	/**   
	 * handoverBillList   
	 *   
	 * @return  the handoverBillList   
	 */
	
	public List<VehicleAssembleBillDetailEntity> getHandoverBillList() {
		return handoverBillList;
	}
	/**   
	 * @param handoverBillList the handoverBillList to set
	 * Date:2013-7-9下午2:12:13
	 */
	public void setHandoverBillList(
			List<VehicleAssembleBillDetailEntity> handoverBillList) {
		this.handoverBillList = handoverBillList;
	}
}
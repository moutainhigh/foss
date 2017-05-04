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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/dto/UpdateHandOverBillDto.java
 *  
 *  FILE NAME          :UpdateHandOverBillDto.java
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
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.ConnectionBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;

/** 
 * @className: UpdateConnectionBillDto
 * @author: 205109-foss-zenghaibin
 * @description: 修改交接单后，用来接收修改参数的dto
 * @date: 2014-05-12 下午2:19:27
 * 
 */
public class UpdateConnectionBillDto implements Serializable {

	private static final long serialVersionUID = 2395335465258929607L;
	
	//接收修改后提交的被删除的运单
	private Map<String,ConnectionBillDetailEntity> deletedWaybillMap;
	
	//接收修改后提交的修改的运单
	private Map<String,ConnectionBillDetailEntity> updatedWaybillMap;
	
	//接收修改后提交的被删除的流水号HandOverBillSerialNoDetailEntity
	private List<HandOverBillSerialNoDetailEntity> deletedSerialNoList;
	
	//接收修改后提交的所有流水号list
//	private List<HandOverBillSerialNoDetailEntity> allSerialNoList;
	
	//接收修改后的交接单基本信息
	private ConnectionBillEntity connectionBillEntity;
	
	//接收修改后的所有运单信息
	//private List<ConnectionBillDetailEntity> allWaybillList;
	
	//接收修改后的交接单统计信息
	private BigDecimal totalCount;
	private BigDecimal totalPieces;
	private BigDecimal totalCubage;
	private BigDecimal totalWeight;
	
	public Map<String, ConnectionBillDetailEntity> getDeletedWaybillMap() {
		return deletedWaybillMap;
	}
	public void setDeletedWaybillMap(
			Map<String, ConnectionBillDetailEntity> deletedWaybillMap) {
		this.deletedWaybillMap = deletedWaybillMap;
	}
	public Map<String, ConnectionBillDetailEntity> getUpdatedWaybillMap() {
		return updatedWaybillMap;
	}
	public void setUpdatedWaybillMap(
			Map<String, ConnectionBillDetailEntity> updatedWaybillMap) {
		this.updatedWaybillMap = updatedWaybillMap;
	}
	public List<HandOverBillSerialNoDetailEntity> getDeletedSerialNoList() {
		return deletedSerialNoList;
	}
	public void setDeletedSerialNoList(
			List<HandOverBillSerialNoDetailEntity> deletedSerialNoList) {
		this.deletedSerialNoList = deletedSerialNoList;
	}
	
	public ConnectionBillEntity getConnectionBillEntity() {
		return connectionBillEntity;
	}
	public void setConnectionBillEntity(ConnectionBillEntity connectionBillEntity) {
		this.connectionBillEntity = connectionBillEntity;
	}

	public BigDecimal getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(BigDecimal totalCount) {
		this.totalCount = totalCount;
	}
	public BigDecimal getTotalPieces() {
		return totalPieces;
	}
	public void setTotalPieces(BigDecimal totalPieces) {
		this.totalPieces = totalPieces;
	}
	public BigDecimal getTotalCubage() {
		return totalCubage;
	}
	public void setTotalCubage(BigDecimal totalCubage) {
		this.totalCubage = totalCubage;
	}
	public BigDecimal getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}
	
}
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

import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;

/** 
 * @className: UpdateHandOverBillDto
 * @author: ShiWei shiwei@outlook.com
 * @description: 修改交接单后，用来接收修改参数的dto
 * @date: 2012-10-31 下午2:19:27
 * 
 */
public class UpdateHandOverBillDto implements Serializable {

	private static final long serialVersionUID = 2395335465258929607L;
	
	//接收修改后提交的被删除的运单
	private Map<String,HandOverBillDetailEntity> deletedWaybillMap;
	
	//接收修改后提交的新增加的运单
	private Map<String,HandOverBillDetailEntity> addedWaybillMap;
	
	//接收修改后提交的修改的运单
	private Map<String,HandOverBillDetailEntity> updatedWaybillMap;
	
	//接收修改后提交的被删除的流水号
	private List<HandOverBillSerialNoDetailEntity> deletedSerialNoList;
	
	//接收修改后提交的新增的流水号
	private List<HandOverBillSerialNoDetailEntity> addedSerialNoList;
	
	//接收修改后提交的所有流水号list
	private List<HandOverBillSerialNoDetailEntity> allSerialNoList;
	
	//接收修改后的交接单基本信息
	private HandOverBillEntity handOverBillEntity;
	
	//接收修改后的所有运单信息
	private List<HandOverBillDetailEntity> allWaybillList;
	
	//接收修改后的交接单统计信息
	private BigDecimal totalCount;
	private BigDecimal totalPieces;
	private BigDecimal totalCubage;
	private BigDecimal totalWeight;
	private BigDecimal totalMoney;
	private BigDecimal totalCodAmount;
	
	public BigDecimal getTotalCodAmount() {
		return totalCodAmount;
	}

	public void setTotalCodAmount(BigDecimal totalCodAmount) {
		this.totalCodAmount = totalCodAmount;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public List<HandOverBillSerialNoDetailEntity> getAllSerialNoList() {
		return allSerialNoList;
	}

	public void setAllSerialNoList(
			List<HandOverBillSerialNoDetailEntity> allSerialNoList) {
		this.allSerialNoList = allSerialNoList;
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

	public HandOverBillEntity getHandOverBillEntity() {
		return handOverBillEntity;
	}

	public void setHandOverBillEntity(HandOverBillEntity handOverBillEntity) {
		this.handOverBillEntity = handOverBillEntity;
	}

	public List<HandOverBillSerialNoDetailEntity> getDeletedSerialNoList() {
		return deletedSerialNoList;
	}

	public void setDeletedSerialNoList(
			List<HandOverBillSerialNoDetailEntity> deletedSerialNoList) {
		this.deletedSerialNoList = deletedSerialNoList;
	}

	public List<HandOverBillSerialNoDetailEntity> getAddedSerialNoList() {
		return addedSerialNoList;
	}

	public void setAddedSerialNoList(
			List<HandOverBillSerialNoDetailEntity> addedSerialNoList) {
		this.addedSerialNoList = addedSerialNoList;
	}

	public Map<String, HandOverBillDetailEntity> getDeletedWaybillMap() {
		return deletedWaybillMap;
	}

	public void setDeletedWaybillMap(
			Map<String, HandOverBillDetailEntity> deletedWaybillMap) {
		this.deletedWaybillMap = deletedWaybillMap;
	}

	public Map<String, HandOverBillDetailEntity> getAddedWaybillMap() {
		return addedWaybillMap;
	}

	public void setAddedWaybillMap(
			Map<String, HandOverBillDetailEntity> addedWaybillMap) {
		this.addedWaybillMap = addedWaybillMap;
	}

	public Map<String, HandOverBillDetailEntity> getUpdatedWaybillMap() {
		return updatedWaybillMap;
	}

	public void setUpdatedWaybillMap(
			Map<String, HandOverBillDetailEntity> updatedWaybillMap) {
		this.updatedWaybillMap = updatedWaybillMap;
	}

	public List<HandOverBillDetailEntity> getAllWaybillList() {
		return allWaybillList;
	}

	public void setAllWaybillList(List<HandOverBillDetailEntity> allWaybillList) {
		this.allWaybillList = allWaybillList;
	}
	
}
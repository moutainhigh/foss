/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillRfcImportDto.java
 * 
 * FILE NAME        	: WaybillRfcImportDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;

/**
 * 
 * 运单导入DTO
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-11-1 上午11:15:24
 */
public class WaybillRfcImportDto implements Serializable {
	private static final long serialVersionUID = -7909627168547544217L;

	// 货物状态
	private WaybillStockStatusDto stockStatus;

	// 运单
	private WaybillDto waybillDto;
	
	//快递对象
	private WaybillExpressEntity waybillExpressEntity;
	
	// 运输记录
	private List<TransportRecordDto> transportRecordDtos;

	// 运单流水号
	private List<LabeledGoodEntity> labeledGoodEntities;
	//更改单修改件数和打木架数量修改详细信息冗余保存对象
	private List<LabeledGoodChangeHistoryDto> labeledGoodChangeHistoryDtoList;
	// 其他费用
	private List<WaybillOtherChargeDto> otherChargeDtos;
	
	
	
	public WaybillExpressEntity getWaybillExpressEntity() {
		return waybillExpressEntity;
	}
	public void setWaybillExpressEntity(WaybillExpressEntity waybillExpressEntity) {
		this.waybillExpressEntity = waybillExpressEntity;
	}
	/**
	 * @return stockStatus : set the property stockStatus.
	 */
	public WaybillStockStatusDto getStockStatus() {
		return stockStatus;
	}
	/**
	 * @param stockStatus : return the property stockStatus.
	 */
	public void setStockStatus(WaybillStockStatusDto stockStatus) {
		this.stockStatus = stockStatus;
	}
	/**
	 * @return waybillDto : set the property waybillDto.
	 */
	public WaybillDto getWaybillDto() {
		return waybillDto;
	}
	/**
	 * @param waybillDto : return the property waybillDto.
	 */
	public void setWaybillDto(WaybillDto waybillDto) {
		this.waybillDto = waybillDto;
	}
	/**
	 * @return labeledGoodEntities : set the property labeledGoodEntities.
	 */
	public List<LabeledGoodEntity> getLabeledGoodEntities() {
		return labeledGoodEntities;
	}
	/**
	 * @param labeledGoodEntities : return the property labeledGoodEntities.
	 */
	public void setLabeledGoodEntities(List<LabeledGoodEntity> labeledGoodEntities) {
		this.labeledGoodEntities = labeledGoodEntities;
	}
	/**
	 * @return otherChargeDtos : set the property otherChargeDtos.
	 */
	public List<WaybillOtherChargeDto> getOtherChargeDtos() {
		return otherChargeDtos;
	}
	/**
	 * @param otherChargeDtos : return the property otherChargeDtos.
	 */
	public void setOtherChargeDtos(List<WaybillOtherChargeDto> otherChargeDtos) {
		this.otherChargeDtos = otherChargeDtos;
	}
	/**
	 * @return the labeledGoodChangeHistoryDtoList
	 */
	public List<LabeledGoodChangeHistoryDto> getLabeledGoodChangeHistoryDtoList() {
		return labeledGoodChangeHistoryDtoList;
	}
	/**
	 * @param labeledGoodChangeHistoryDtoList the labeledGoodChangeHistoryDtoList to set
	 */
	public void setLabeledGoodChangeHistoryDtoList(
			List<LabeledGoodChangeHistoryDto> labeledGoodChangeHistoryDtoList) {
		this.labeledGoodChangeHistoryDtoList = labeledGoodChangeHistoryDtoList;
	}
	
	/**
	 * @return the transportRecordDtos
	 */
	public List<TransportRecordDto> getTransportRecordDtos() {
		return transportRecordDtos;
	}
	
	/**
	 * @param transportRecordDtos the transportRecordDtos to set
	 */
	public void setTransportRecordDtos(List<TransportRecordDto> transportRecordDtos) {
		this.transportRecordDtos = transportRecordDtos;
	}
	
	
}
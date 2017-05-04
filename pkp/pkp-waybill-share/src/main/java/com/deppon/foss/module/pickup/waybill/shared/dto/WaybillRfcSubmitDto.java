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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/WaybillRfcSubmitDto.java
 * 
 * FILE NAME        	: WaybillRfcSubmitDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.dto
 * FILE    NAME: WaybillRfcSubmitDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.pickup.waybill.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.waybill.shared.domain.InstallationEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcTranferEntity;

/**
 * 更改单提交DTO
 * 
 * @author 102246-foss-shaohongliang
 * @date 2012-11-12 下午1:57:27
 */
public class WaybillRfcSubmitDto implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = -3366977266474232148L;

	/**
	 * 更改单主表信息
	 */
	private WaybillRfcEntity rfcEntity;
	/**
	 * 安装费明细 
	 */
	private List<InstallationEntity> installationEntityList;
	
	//转运或是返货的目的站变更实体
	private List<WaybillRfcTranferEntity> rfcTranferList;
	

	public List<WaybillRfcTranferEntity> getRfcTranferList() {
		return rfcTranferList;
	}

	public void setRfcTranferList(List<WaybillRfcTranferEntity> rfcTranferList) {
		this.rfcTranferList = rfcTranferList;
	}

	/**
	 * 变更明细
	 */
	private List<WaybillRfcChangeDetailDto> rfcChangeDetailDtos;

	/**
	 * 凭证内容
	 */
	private List<UploadVoucherDto> uploadVoucherDtos;

	/**
	 * 运单
	 */
	private WaybillDto waybillDto;

	/**
	 * 打木架流水号
	 */
	private List<LabeledGoodChangeHistoryDto> woodenRequirementLabeledGoods;
	
	/**
	 * 其他的货件信息 比如新增 或者 减少货件
	 */
	private List<LabeledGoodChangeHistoryDto> otherLabeledGoods;


	/**
	 * 库存状态
	 */
	private WaybillStockStatusDto stockStatus;
	
	/**
	 *定价优化项目：降价返券需求
	 *
	 *@author Foss-206860
	 * */
	private QueryBillCacilateDto queryBillCacilateDto;

	public QueryBillCacilateDto getQueryBillCacilateDto() {
		return queryBillCacilateDto;
	}

	public void setQueryBillCacilateDto(QueryBillCacilateDto queryBillCacilateDto) {
		this.queryBillCacilateDto = queryBillCacilateDto;
	}
	
	
	/**
	 * @return the stockStatus
	 */
	public WaybillStockStatusDto getStockStatus() {
		return stockStatus;
	}
	
	/**
	 * @param stockStatus the stockStatus to set
	 */
	public void setStockStatus(WaybillStockStatusDto stockStatus) {
		this.stockStatus = stockStatus;
	}


	/**
	 * @return the otherLabeledGoods
	 */
	public List<LabeledGoodChangeHistoryDto> getOtherLabeledGoods() {
		return otherLabeledGoods;
	}


	/**
	 * @param otherLabeledGoods the otherLabeledGoods to set
	 */
	public void setOtherLabeledGoods(
			List<LabeledGoodChangeHistoryDto> otherLabeledGoods) {
		this.otherLabeledGoods = otherLabeledGoods;
	}


	/**
	 * @return the rfcEntity
	 */
	public WaybillRfcEntity getRfcEntity() {
		return rfcEntity;
	}

	
	/**
	 * @param rfcEntity the rfcEntity to set
	 */
	public void setRfcEntity(WaybillRfcEntity rfcEntity) {
		this.rfcEntity = rfcEntity;
	}

	
	/**
	 * @return the rfcChangeDetailDtos
	 */
	public List<WaybillRfcChangeDetailDto> getRfcChangeDetailDtos() {
		return rfcChangeDetailDtos;
	}

	
	/**
	 * @param rfcChangeDetailDtos the rfcChangeDetailDtos to set
	 */
	public void setRfcChangeDetailDtos(
			List<WaybillRfcChangeDetailDto> rfcChangeDetailDtos) {
		this.rfcChangeDetailDtos = rfcChangeDetailDtos;
	}

	
	/**
	 * @return the uploadVoucherDtos
	 */
	public List<UploadVoucherDto> getUploadVoucherDtos() {
		return uploadVoucherDtos;
	}

	
	/**
	 * @param uploadVoucherDtos the uploadVoucherDtos to set
	 */
	public void setUploadVoucherDtos(List<UploadVoucherDto> uploadVoucherDtos) {
		this.uploadVoucherDtos = uploadVoucherDtos;
	}

	
	/**
	 * @return the waybillDto
	 */
	public WaybillDto getWaybillDto() {
		return waybillDto;
	}

	
	/**
	 * @param waybillDto the waybillDto to set
	 */
	public void setWaybillDto(WaybillDto waybillDto) {
		this.waybillDto = waybillDto;
	}

	
	/**
	 * @return the woodenRequirementLabeledGoods
	 */
	public List<LabeledGoodChangeHistoryDto> getWoodenRequirementLabeledGoods() {
		return woodenRequirementLabeledGoods;
	}

	
	/**
	 * @param woodenRequirementLabeledGoods the woodenRequirementLabeledGoods to set
	 */
	public void setWoodenRequirementLabeledGoods(
			List<LabeledGoodChangeHistoryDto> woodenRequirementLabeledGoods) {
		this.woodenRequirementLabeledGoods = woodenRequirementLabeledGoods;
	}

	public List<InstallationEntity> getInstallationEntityList() {
		return installationEntityList;
	}

	public void setInstallationEntityList(
			List<InstallationEntity> installationEntityList) {
		this.installationEntityList = installationEntityList;
	}

}
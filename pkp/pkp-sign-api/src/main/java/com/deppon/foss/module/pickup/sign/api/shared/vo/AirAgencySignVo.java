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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/vo/AirAgencySignVo.java
 * 
 * FILE NAME        	: AirAgencySignVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.BatchSignDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ExternalBillInfoDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
/**
 * 签收空运偏线货物Vo
 * @author foss-meiying
 * @date 2012-11-15 下午4:09:46
 * @since
 * @version
 */
public class AirAgencySignVo implements Serializable {
	//序列
	private static final long serialVersionUID = -1005901960761003148L;
	/**
	 * 偏线空运查询条件dto
	 */
	private AirAgencyQueryDto airAgencyQueryDto;
	private List<BatchSignDto> batchSignDtos;
	/**
	 * 并发控制  --
	 * 		到达未出库件数
	 */
	private Integer oldArriveNotoutGoodsQty;
	/**
	 * 签收确认收入服务使用DTO
	 */
	private LineSignDto lineSignDto;
	/**
	 * 运单dto
	 */
	private WaybillDto waybillDto;
	/**
	 *  运单签收结果Entity
	 */
	private WaybillSignResultEntity waybillSignResultEntity;
	/**
	 *  空运偏线集合
	 */
	private List<AirAgencyQueryDto> agencyQueryDtos;
	/***
	 * 偏线外发单Dto
	 */
	private ExternalBillInfoDto externalBillInfoDto;

	/**
	 * Gets the 偏线空运查询条件dto.
	 *
	 * @return the 偏线空运查询条件dto
	 */
	public AirAgencyQueryDto getAirAgencyQueryDto() {
		return airAgencyQueryDto;
	}

	/**
	 * Sets the 偏线空运查询条件dto.
	 *
	 * @param airAgencyQueryDto the new 偏线空运查询条件dto
	 */
	public void setAirAgencyQueryDto(AirAgencyQueryDto airAgencyQueryDto) {
		this.airAgencyQueryDto = airAgencyQueryDto;
	}

	/**
	 * Gets the 空运偏线集合.
	 *
	 * @return the 空运偏线集合
	 */
	public List<AirAgencyQueryDto> getAgencyQueryDtos() {
		return agencyQueryDtos;
	}

	/**
	 * Sets the 空运偏线集合.
	 *
	 * @param agencyQueryDtos the new 空运偏线集合
	 */
	public void setAgencyQueryDtos(List<AirAgencyQueryDto> agencyQueryDtos) {
		this.agencyQueryDtos = agencyQueryDtos;
	}

	/**
	 * Gets the 运单dto.
	 *
	 * @return the 运单dto
	 */
	public WaybillDto getWaybillDto() {
		return waybillDto;
	}

	/**
	 * Sets the 运单dto.
	 *
	 * @param waybillDto the new 运单dto
	 */
	public void setWaybillDto(WaybillDto waybillDto) {
		this.waybillDto = waybillDto;
	}

	/**
	 * Gets the 运单签收结果Entity.
	 *
	 * @return the 运单签收结果Entity
	 */
	public WaybillSignResultEntity getWaybillSignResultEntity() {
		return waybillSignResultEntity;
	}

	/**
	 * Sets the 运单签收结果Entity.
	 *
	 * @param waybillSignResultEntity the new 运单签收结果Entity
	 */
	public void setWaybillSignResultEntity(WaybillSignResultEntity waybillSignResultEntity) {
		this.waybillSignResultEntity = waybillSignResultEntity;
	}

	/**
	 * Gets the 签收确认收入服务使用DTO.
	 *
	 * @return the 签收确认收入服务使用DTO
	 */
	public LineSignDto getLineSignDto() {
		return lineSignDto;
	}

	/**
	 * Sets the 签收确认收入服务使用DTO.
	 *
	 * @param lineSignDto the new 签收确认收入服务使用DTO
	 */
	public void setLineSignDto(LineSignDto lineSignDto) {
		this.lineSignDto = lineSignDto;
	}

	/**
	 * Gets the * 偏线外发单Dto.
	 *
	 * @return the * 偏线外发单Dto
	 */
	public ExternalBillInfoDto getExternalBillInfoDto() {
		return externalBillInfoDto;
	}

	/**
	 * Sets the * 偏线外发单Dto.
	 *
	 * @param externalBillInfoDto the new * 偏线外发单Dto
	 */
	public void setExternalBillInfoDto(ExternalBillInfoDto externalBillInfoDto) {
		this.externalBillInfoDto = externalBillInfoDto;
	}

	/**
	 * Gets the 并发控制  -- 到达未出库件数.
	 *
	 * @return the 并发控制  -- 到达未出库件数
	 */
	public Integer getOldArriveNotoutGoodsQty() {
		return oldArriveNotoutGoodsQty;
	}

	/**
	 * Sets the 并发控制  -- 到达未出库件数.
	 *
	 * @param oldArriveNotoutGoodsQty the new 并发控制  -- 到达未出库件数
	 */
	public void setOldArriveNotoutGoodsQty(Integer oldArriveNotoutGoodsQty) {
		this.oldArriveNotoutGoodsQty = oldArriveNotoutGoodsQty;
	}

	public List<BatchSignDto> getBatchSignDtos() {
		return batchSignDtos;
	}

	public void setBatchSignDtos(List<BatchSignDto> batchSignDtos) {
		this.batchSignDtos = batchSignDtos;
	}

}
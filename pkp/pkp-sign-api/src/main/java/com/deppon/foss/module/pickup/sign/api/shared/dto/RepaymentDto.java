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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/dto/RepaymentDto.java
 * 
 * FILE NAME        	: RepaymentDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.dto;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;

/**
 * 付款.
 *
 * @author ibm-
 * 		lizhiguo
 * @date 2012-10-15 
 * 		下午2:06:26
 */
public class RepaymentDto implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6369429486147357666L;
	
	/** 查询dto. */
	private AirAgencyQueryDto airAgencyQueryDto;
	
	/** 待处理list. */
	private List<AirAgencyQueryDto> airAgencyQueryDtoList;
	
	/** 财务信息dto. */
	private FinancialDto financialDto;
	
	/** 付款记录. */
	private List<RepaymentEntity> repaymentEntityList;
	
	/** 运单信息. */
	private WaybillDto waybillDto;
	/**
	 * 付款方式为网上支付的是否付款完成
	 */
	//张新 2015-2-3  判断是否是返单
	private int isback;
	
	
	private String isPayByOL;

	/**
	 * Gets the air agency query dto.
	 *
	 * @return the airAgencyQueryDto
	 */
	public AirAgencyQueryDto getAirAgencyQueryDto() {
		return airAgencyQueryDto;
	}

	/**
	 * Sets the air agency query dto.
	 *
	 * @param airAgencyQueryDto the airAgencyQueryDto to see
	 */
	public void setAirAgencyQueryDto(AirAgencyQueryDto airAgencyQueryDto) {
		this.airAgencyQueryDto = airAgencyQueryDto;
	}

	/**
	 * Gets the air agency query dto list.
	 *
	 * @return the airAgencyQueryDtoList
	 */
	public List<AirAgencyQueryDto> getAirAgencyQueryDtoList() {
		return airAgencyQueryDtoList;
	}

	/**
	 * Sets the air agency query dto list.
	 *
	 * @param airAgencyQueryDtoList the airAgencyQueryDtoList to see
	 */
	public void setAirAgencyQueryDtoList(
			List<AirAgencyQueryDto> airAgencyQueryDtoList) {
		this.airAgencyQueryDtoList = airAgencyQueryDtoList;
	}

	/**
	 * Gets the financial dto.
	 *
	 * @return the financialDto
	 */
	public FinancialDto getFinancialDto() {
		return financialDto;
	}

	/**
	 * Sets the financial dto.
	 *
	 * @param financialDto the financialDto to see
	 */
	public void setFinancialDto(FinancialDto financialDto) {
		this.financialDto = financialDto;
	}

	/**
	 * Gets the repayment entity list.
	 *
	 * @return the repaymentEntityList
	 */
	public List<RepaymentEntity> getRepaymentEntityList() {
		return repaymentEntityList;
	}

	/**
	 * Sets the repayment entity list.
	 *
	 * @param repaymentEntityList the repaymentEntityList to see
	 */
	public void setRepaymentEntityList(List<RepaymentEntity> repaymentEntityList) {
		this.repaymentEntityList = repaymentEntityList;
	}

	/**
	 * Gets the waybill dto.
	 *
	 * @return the waybillDto
	 */
	public WaybillDto getWaybillDto() {
		return waybillDto;
	}

	/**
	 * Sets the waybill dto.
	 *
	 * @param waybillDto the waybillDto to see
	 */
	public void setWaybillDto(WaybillDto waybillDto) {
		this.waybillDto = waybillDto;
	}

	public String getIsPayByOL() {
		return isPayByOL;
	}

	public void setIsPayByOL(String isPayByOL) {
		this.isPayByOL = isPayByOL;
	}

	public int getIsback() {
		return isback;
	}

	public void setIsback(int isback) {
		this.isback = isback;
	}


}
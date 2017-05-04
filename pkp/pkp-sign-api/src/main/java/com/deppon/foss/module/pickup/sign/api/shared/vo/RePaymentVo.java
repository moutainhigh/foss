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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/vo/RePaymentVo.java
 * 
 * FILE NAME        	: RePaymentVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.vo;

import java.io.Serializable;


import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.RepaymentDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;

/**
 * 结清货款VO.
 *
 * @author 043258-
 * 		foss-zhaobin
 * @date 2012-11-20 
 * 		下午3:15:15
 */
public class RePaymentVo implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 付款dto. */
	private RepaymentDto repaymentDto;
	
	/** 付款实体. */
	private RepaymentEntity repaymentEntity;
	/**
	 * 运单实体
	 */
	private WaybillDto waybillDto;

	/**
	 * Gets the repayment dto.
	 *
	 * @return the repaymentDto
	 */
	public RepaymentDto getRepaymentDto() {
		return repaymentDto;
	}

	/**
	 * Sets the repayment dto.
	 *
	 * @param repaymentDto the repaymentDto to see
	 */
	public void setRepaymentDto(RepaymentDto repaymentDto) {
		this.repaymentDto = repaymentDto;
	}

	/**
	 * Gets the repayment entity.
	 *
	 * @return the repaymentEntity
	 */
	public RepaymentEntity getRepaymentEntity() {
		return repaymentEntity;
	}

	/**
	 * Sets the repayment entity.
	 *
	 * @param repaymentEntity the repaymentEntity to see
	 */
	public void setRepaymentEntity(RepaymentEntity repaymentEntity) {
		this.repaymentEntity = repaymentEntity;
	}

	public WaybillDto getWaybillDto() {
		return waybillDto;
	}

	public void setWaybillDto(WaybillDto waybillDto) {
		this.waybillDto = waybillDto;
	}
	
}
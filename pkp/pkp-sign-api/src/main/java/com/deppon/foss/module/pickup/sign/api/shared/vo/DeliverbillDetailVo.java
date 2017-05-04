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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/vo/DeliverbillDetailVo.java
 * 
 * FILE NAME        	: DeliverbillDetailVo.java
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

import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;

/***
 * 派送单明细vo
 * @author foss-meiying
 * @date 2012-10-30 上午10:20:55
 * @since
 * @version
 */
public class DeliverbillDetailVo implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8290780382667916429L;
	/**
	 *  派送单明细dto
	 */
	private DeliverbillDetailDto deliverbillDetailDto;
	/**
	 *  财务信息dto
	 */
	private FinancialDto financialDto;
	/**
	 *  派送单明细dto集合
	 */
	private List<DeliverbillDetailDto> deliverbillDetailDtos;
	/**
	 * 签收明细集合
	 */
	private List<SignDetailEntity> signDetailEntitys;
	
	/**
	 * 子母件查询通用Dto
	 */
	private TwoInOneWaybillDto twoInOneWaybillDto;

	/**
	 * Gets the 派送单明细dto.
	 *
	 * @return the 派送单明细dto
	 */
	public DeliverbillDetailDto getDeliverbillDetailDto() {
		return deliverbillDetailDto;
	}

	/**
	 * Sets the 派送单明细dto.
	 *
	 * @param deliverbillDetailDto the new 派送单明细dto
	 */
	public void setDeliverbillDetailDto(DeliverbillDetailDto deliverbillDetailDto) {
		this.deliverbillDetailDto = deliverbillDetailDto;
	}

	/**
	 * Gets the 派送单明细dto集合.
	 *
	 * @return the 派送单明细dto集合
	 */
	public List<DeliverbillDetailDto> getDeliverbillDetailDtos() {
		return deliverbillDetailDtos;
	}

	/**
	 * Sets the 派送单明细dto集合.
	 *
	 * @param deliverbillDetailDtos the new 派送单明细dto集合
	 */
	public void setDeliverbillDetailDtos(
			List<DeliverbillDetailDto> deliverbillDetailDtos) {
		this.deliverbillDetailDtos = deliverbillDetailDtos;
	}

	/**
	 * Gets the 财务信息dto.
	 *
	 * @return the 财务信息dto
	 */
	public FinancialDto getFinancialDto() {
		return financialDto;
	}

	/**
	 * Sets the 财务信息dto.
	 *
	 * @param financialDto the new 财务信息dto
	 */
	public void setFinancialDto(FinancialDto financialDto) {
		this.financialDto = financialDto;
	}

	/**
	 * Gets the 签收明细集合.
	 *
	 * @return the 签收明细集合
	 */
	public List<SignDetailEntity> getSignDetailEntitys() {
		return signDetailEntitys;
	}

	/**
	 * Sets the 签收明细集合.
	 *
	 * @param signDetailEntitys the new 签收明细集合
	 */
	public void setSignDetailEntitys(List<SignDetailEntity> signDetailEntitys) {
		this.signDetailEntitys = signDetailEntitys;
	}

	public TwoInOneWaybillDto getTwoInOneWaybillDto() {
		return twoInOneWaybillDto;
	}
	public void setTwoInOneWaybillDto(TwoInOneWaybillDto twoInOneWaybillDto) {
		this.twoInOneWaybillDto = twoInOneWaybillDto;
	}

}
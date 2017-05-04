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
 * PROJECT NAME	: pkp-pickup-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/shared/vo/CashDiffReportVo.java
 * 
 * FILE NAME        	: CashDiffReportVo.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.pickup.pickup.api.shared.dto.CashDiffReportDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.RtCashDiffReportDto;

/**
 * 接送货现金差异报表-查看现金差异报表Vo
 *
 */
public class CashDiffReportVo implements Serializable{

	//序列化版本号
	private static final long serialVersionUID = 1L;
		
	
	 //查询条件DTO
	private CashDiffReportDto cashDiffReportDto = new CashDiffReportDto();
	
	//返回查询的结果集合
	private List<RtCashDiffReportDto> rtCashDiffReportDtoList;

	public CashDiffReportDto getCashDiffReportDto() {
		return cashDiffReportDto;
	}

	public void setCashDiffReportDto(CashDiffReportDto cashDiffReportDto) {
		this.cashDiffReportDto = cashDiffReportDto;
	}

	public List<RtCashDiffReportDto> getRtCashDiffReportDtoList() {
		return rtCashDiffReportDtoList;
	}

	public void setRtCashDiffReportDtoList(
			List<RtCashDiffReportDto> rtCashDiffReportDtoList) {
		this.rtCashDiffReportDtoList = rtCashDiffReportDtoList;
	}
	
	
}
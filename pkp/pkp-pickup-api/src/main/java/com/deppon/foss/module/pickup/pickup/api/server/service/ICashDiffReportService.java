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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/api/server/service/ICashDiffReportService.java
 * 
 * FILE NAME        	: ICashDiffReportService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.module.pickup.pickup.api.shared.dto.CashDiffReportDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.RtCashDiffReportDto;

/**
 * 接送货现金差异报表-查看现金差异报表Service
 *
 */
public interface ICashDiffReportService {

	/**
	 * 查看现金差异报表
	 * @param dto
	 * @return
	 */
	List<RtCashDiffReportDto> 
		queryCashDiffReport(CashDiffReportDto dto, int start, int end);

	/**
	 * 查看现金差异报表 Total
	 * @param dto
	 * @return
	 */
	Long queryCashDiffReportTotal(CashDiffReportDto cashDiffReportDto);

	/**
	 * 
	 * 导出接送货现金差异报表数据
	 * @date 2012-11-6 下午4:29:21
	 */
	InputStream queryCashDiffReportInfo(
			CashDiffReportDto cashDiffReportDto);
}
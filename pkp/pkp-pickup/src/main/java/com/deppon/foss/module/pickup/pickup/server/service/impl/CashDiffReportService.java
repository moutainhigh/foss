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
 * PROJECT NAME	: pkp-pickup
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pickup/server/service/impl/CashDiffReportService.java
 * 
 * FILE NAME        	: CashDiffReportService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pickup.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.module.pickup.pickup.api.server.dao.ICashDiffReportDao;
import com.deppon.foss.module.pickup.pickup.api.server.service.ICashDiffReportService;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.CashDiffReportDto;
import com.deppon.foss.module.pickup.pickup.api.shared.dto.RtCashDiffReportDto;
import com.deppon.foss.util.DateUtils;

/**
 * 接送货现金差异报表-查看现金差异报表Service
 * @author admin
 *
 */
public class CashDiffReportService implements ICashDiffReportService {

	//sheet size of excel
	private static final int SHEETSIZE = 20000;
	//现金差异报表Dao
	private ICashDiffReportDao cashDiffReportDao;
	
	/**
	 * 查看现金差异报表
	 * @param dto
	 * @return
	 */
	public List<RtCashDiffReportDto> 
		queryCashDiffReport(CashDiffReportDto dto,int start, int end){
		
		//查现金差异报表
		return cashDiffReportDao.queryCashDiffReport(dto,start, end);
	}
	

	/**
	 * 查看现金差异报表 Total
	 * @param dto
	 * @return
	 */
	public Long queryCashDiffReportTotal(CashDiffReportDto cashDiffReportDto) {
		//查现金差异报表
		return cashDiffReportDao.queryCashDiffReportTotal(cashDiffReportDto);
	}
	
	
	public void setCashDiffReportDao(ICashDiffReportDao cashDiffReportDao) {
		this.cashDiffReportDao = cashDiffReportDao;
	}

	/**
	 * 导出接送货现金差异报表数据
	 * @date 2012-11-6 下午4:29:21
	 *
	 */
	public InputStream queryCashDiffReportInfo(
			CashDiffReportDto dto) {
		
		//导出接送货现金差异报表数据
		List<RtCashDiffReportDto> list = cashDiffReportDao.queryCashDiffReport(dto);
		List<List<String>> rowList = new ArrayList<List<String>>();//报表数据column
		for(RtCashDiffReportDto cashReport : list){
			List<String> columnList = new ArrayList<String>();
			columnList.add(cashReport.getWaybillNo());//运单号
			columnList.add(cashReport.getDriverNoAndName());// 接货司机信息
			columnList.add(cashReport.getDriverDepartmentName());//司机所属部门
			columnList.add(cashReport.getVehicleNo());// 车牌号
			Date date = cashReport.getPickupTime();
			String dateStr = DateUtils.convert(date, DateUtils.DATE_TIME_FORMAT);
			columnList.add(dateStr);//接货时间
			columnList.add(cashReport.getReceiveOrgName());//收货部门
			columnList.add(cashReport.getPdaInputAmount().toString());//PDA录入收款金额
			columnList.add(cashReport.getWaybillHandwriteAmount().toString());//运单手写现付金额
			columnList.add(cashReport.getWaybillPayAmount().toString());//开单应收现付金额
			columnList.add(cashReport.getPdaMinusHandwriteAmount().toString());//PDA录入收款金额-运单手写现付金额
			columnList.add(cashReport.getHandwriteMinusPayAmount().toString());//运单手写现付金额-开单应收现付金额
			columnList.add(cashReport.getCreateUserName());//制单人
			
			rowList.add(columnList);
		}
		//定义表头
		String[] rowHeads = {"运单号",
							 "接货司机信息",
							 "司机所属部门",
						     "车牌号",
							 "接货时间",
							 "收货部门",
							 "PDA录入收款金额",
							 "运单手写现付金额",
							 "开单应收现付金额",
							 "PDA录入收款金额-运单手写现付金额",
							 "运单手写现付金额-开单应收现付金额",
						     "制单人"
							 };
		
		ExportResource exportResource = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName("现金差异报表数据");
		exportSetting.setSize(SHEETSIZE);
		//平台excel导出工具类
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		
		//定义流
        return objExporterExecutor.exportSync(exportResource, exportSetting);
	
	}


	
	
}
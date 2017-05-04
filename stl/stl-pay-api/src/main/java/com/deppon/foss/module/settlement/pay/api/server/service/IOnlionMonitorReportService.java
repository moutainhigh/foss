/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-pay-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.pay.api.server.service
 * FILE    NAME: IOnlionMonitorReportActio.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.settlement.pay.api.server.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OnlionMonitorReportDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.OnlionMonitorReportResultDto;


/**
 * 查询在线支付信息服务
 * @author 045738-foss-maojianqiang
 * @date 2012-12-26 下午8:45:32
 */
public interface IOnlionMonitorReportService extends IService{
	
	/**
	 * 查询在线支付信息
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-27 上午8:31:24
	 * @param queryDto
	 * @return  查询结果
	 */
	public OnlionMonitorReportResultDto queryOnlionMointorList(OnlionMonitorReportDto queryDto,int start,int limit);

	/**
	 * 导出在线支付数据
	 * @author 045738-foss-maojianqiang
	 * @date 2012-12-27 下午7:40:46
	 * @param queryDto
	 * @return
	 */
	HSSFWorkbook exportBillPayable(OnlionMonitorReportDto queryDto);

}

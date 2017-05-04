/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/service/IDeliverLoadGapReportService.java
 *  
 *  FILE NAME          :IDeliverLoadGapReportService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.server.service
 * FILE    NAME: DeliverLoadGapReportService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportSerialEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DiffReportReturnNumEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.QueryDiffReportByWayBillEntity;

/**
 * IDeliverLoadGapReportService
 * @author dp-duyi
 * @date 2012-10-25 下午3:31:52
 */
public interface IDeliverLoadGapReportService {
	public void createDeliverLoadGapReport();
	public List<DeliverLoadGapReportEntity> queryDeliverLoadGapReport(DeliverLoadGapReportEntity report,String queryTimeBegin,String queryTimeEnd,int limit,int start);
	public Long queryDeliverLoadGapReportCount(DeliverLoadGapReportEntity report,String queryTimeBegin,String queryTimeEnd);
	//查询运单明细，界面显示，查询明细
	public List<DeliverLoadGapReportWayBillEntity> queryDeliverLoadGapReportWayBillsById(DeliverLoadGapReportEntity report);
	//接送货接口，根据派送单号查询实际少货的运单（查询装车任务运单表），包括差异报告中的和没在差异报告中的
	public List<DeliverLoadGapReportWayBillEntity> queryDeliverLoadGapReportWayBills(DeliverLoadGapReportEntity report);
	public List<DeliverLoadGapReportSerialEntity> queryDeliverLoadGapReportSerials(DeliverLoadGapReportWayBillEntity reportWayBill);
	@SuppressWarnings("rawtypes")
	public List getDeliverLoadGapDetailExcelInputStream(String taskNo);
	/**
	 * @author 269701--lln
	 * @date 2015-11-11上午10:39:43
	 * @function 根据运单查派送装车差异报告，显示出其中运单差异报告类型为“少货”、“退回”和预计装车件数的记录
	 * @param waybillNo 运单号 
	 * @return 预计装车件数、 差异报告类型
	 */
	List<QueryDiffReportByWayBillEntity> diffReportByWayBill(String waybillNo);
	/**
	 * @author 283250--liuyi
	 * @date 2016-02-25上午10:39:43
	 * @function 根据运单号以及交单开始时间，查询装车“少货”、“退回”次数
	 * @param waybillNo 运单号  surrenderTime 交单开始时间
	 * @return DiffReportReturnNumEntity “少货”、“退回”次数
	 */
	DiffReportReturnNumEntity queryDiffReportReturnNum(String waybillNo,Date surrenderTime);
}
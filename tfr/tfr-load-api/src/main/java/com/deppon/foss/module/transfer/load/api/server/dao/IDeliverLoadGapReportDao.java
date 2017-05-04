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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/IDeliverLoadGapReportDao.java
 *  
 *  FILE NAME          :IDeliverLoadGapReportDao.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.server.dao
 * FILE    NAME: IDeliverLoadGapReportDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportSerialEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DiffReportReturnNumEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.QueryDiffReportByWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.DeliverLoadGapReportWayBillDto;


/**
 * IDeliverLoadGapReportDao
 * @author dp-duyi
 * @date 2012-10-25 下午3:36:33
 */
public interface IDeliverLoadGapReportDao {
	public void createDeliverLoadGapReort();
	public List<DeliverLoadGapReportEntity> queryDeliverLoadGapReport(Map<String,Object> condition,int limit,int start);
	public Long queryDeliverLoadGapReportCount(Map<String,Object> condition);
	//接送货接口，根据派送单号查询实际少货的运单（查询装车任务运单表），包括差异报告中的和没在差异报告中的
	public List<DeliverLoadGapReportWayBillEntity> queryDeliverLoadGapReportWayBills(DeliverLoadGapReportEntity report);
	public List<DeliverLoadGapReportSerialEntity> queryDeliverLoadGapReportSerials(DeliverLoadGapReportWayBillEntity reportWayBill);
	public int updateDeliverLoadGapReportState(DeliverLoadGapReportEntity report);
	//根据派送单号、装车任务id查询全部卸车差异报告（有效，无效）
	public List<DeliverLoadGapReportEntity> queryAllDeliverLoadGapRepByDeliverNo(Map<String,String> condition);
	//查询运单明细，界面显示，查询明细
	public List<DeliverLoadGapReportWayBillEntity> queryDeliverLoadGapReportWayBillsById(DeliverLoadGapReportEntity report);
	public List<String> queryTransferCenterByMotorcade(String motorCadeCode);
	public List<DeliverLoadGapReportWayBillDto> queryExportDeliverLoadGapDetail(String taskNo);
	//查询派送装车运单退回件数
	public Integer querySumWaybillReturn(String taskNo);
	/**
	 * @author 269701--lln
	 * @date 2015-11-11上午10:39:43
	 * @function 根据运单查派送装车差异报告，显示出其中运单差异报告类型为“少货”、“退回”和预计装车件数的记录
	 * @param waybillNo 运单号 
	 * @return 预计装车件数、 差异报告类型
	 */
	List<QueryDiffReportByWayBillEntity> queryDiffReportByWayBill(String waybillNo);
	/**
	 * @author 283250--liuyi
	 * @date 2016-02-25上午10:39:43
	 * @function 根据运单号以及交单开始时间，查询装车“少货”、“退回”次数
	 * @param waybillNo 运单号  surrenderTime 交单开始时间
	 * @return DiffReportReturnNumEntity “少货”、“退回”次数
	 */
	DiffReportReturnNumEntity queryDiffReportReturnNum(Map<String,Object> queryEntity);
}
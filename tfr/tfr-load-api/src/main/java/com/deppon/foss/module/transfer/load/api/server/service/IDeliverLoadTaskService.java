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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/service/IDeliverLoadTaskService.java
 *  
 *  FILE NAME          :IDeliverLoadTaskService.java
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
 * FILE    NAME: IDeliverLoadService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadWaybillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;

/**
 *IDeliverLoadTaskService
 * @author 042795-foss-duyi
 * @date 2012-10-30 上午10:54:52
 */
public interface IDeliverLoadTaskService {
	//确认派送装车
	public int comfirmLoadTask(String deliverBillNo);
	//打回派送装车
	public int takeBackLoadTask(String deliverBillNo);
	//打回派送单
	public int takeBackDeliverBill(String deliverBillNo);
	//查询派送装车差异报告
	public List<DeliverLoadGapReportEntity> queryDeliverLoadGapRep(String taskId,String deliverBillNo);
	//根据派送单号、运单号，返回最新的装车任务流水号列表
	public List<String> queryLastLoadSerialNos(String deliverBillNo,String wayBillNo);
	
	/**
	 * 根据派送单号获取派送单信息
	 * @author 045923-foss-shiwei
	 * @date 2013-4-15 下午4:36:14
	 */
	public DeliverBillEntity queryDeliverBillByNo(String billNo);
	
	/**
	 * 根据派送单号获取派送单详情
	 * @author 045923-foss-shiwei
	 * @date 2013-4-15 下午4:37:00
	 */
	public List<LoadWaybillDetailEntity> queryDeliverBillDetailListByNo(String billNo);
	
	/**
	 * 根据运单号获取流水号库存
	 * @author 045923-foss-shiwei
	 * @date 2013-4-15 下午8:28:26
	 */
	public List<SerialNoStockEntity> querySerialNoStockList(String waybillNo);
	
	/**
	 * 根据到达部门、运单号获取交接过的流水号
	 * @author 045923-foss-shiwei
	 * @date 2013-4-16 上午10:58:02
	 */
	public List<SerialNoStockEntity> queryHandOveredSerialNoListByDestOrgCodeAndWaybillNo(String waybillNo);
	
	/**
	 * 手工确认派送装车任务
	 * @author 045923-foss-shiwei
	 * @date 2013-4-17 上午10:21:40
	 */
	public int confirmDeliverLoadTaskByHand(String billNo,Date operateTime,List<LoadWaybillDetailEntity> waybillList,List<SerialNoStockEntity> serialNoList);

}
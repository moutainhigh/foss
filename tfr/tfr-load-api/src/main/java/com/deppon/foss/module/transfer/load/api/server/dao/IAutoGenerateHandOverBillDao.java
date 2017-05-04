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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/IAutoGenerateHandOverBillDao.java
 *  
 *  FILE NAME          :IAutoGenerateHandOverBillDao.java
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
 * FILE    NAME: IAutoGenerateHandOverBillDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.Date;
import java.util.Map;

/**
 * IAutoGenerateHandOverBillDao
 * @author dp-duyi
 * @date 2012-11-5 上午9:12:18
 */
public interface IAutoGenerateHandOverBillDao {
	public void createHandOverBill(Date bizJobStartTime,Date bizJobEndTime,int threadCount, int threadNo);
	//276198-duhao-20151212-增加交接单号参数
	public String createHandOverBillByTaskNo(String taskNo);
	//322610-songjl-增加零担电子面单交接单号参数
	public String createLTLHandOverBillByTaskNo(String taskNo);
	@SuppressWarnings("rawtypes")
	//276198-duhao-20151212-增加交接单号参数
	public Map generateHandOverBill(Date bizJobStartTime,Date bizJobEndTime,int threadCount, int threadNo,String taskNo);
	//生成快递接货装车任务
	@SuppressWarnings("rawtypes")
	public Map autoCreatePackHandoverbill(String taskNo,String handOverNo);
	//完成接货任务，生成交接单
	@SuppressWarnings("rawtypes")
	public Map autoCreatePKPHandoverbill(String taskID,String orgCode,String vehicleNo,
			String handoverNo,String operatorCode);
	//生成接驳装车任务
	@SuppressWarnings("rawtypes")
	public Map autoCreateConnectionHandover(String taskNo,String handOverNo);
}
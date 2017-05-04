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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/service/impl/AutoGenerateHandOverBillService.java
 *  
 *  FILE NAME          :AutoGenerateHandOverBillService.java
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
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.service.impl
 * FILE    NAME: AutoGenerateHandOverBill.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.service.impl;

import java.util.Date;

import com.deppon.foss.module.transfer.load.api.server.dao.IAutoGenerateHandOverBillDao;
import com.deppon.foss.module.transfer.load.api.server.service.IAutoGenerateHandOverBillService;

/**
 * 自动生成交接单服务
 * @author dp-duyi
 * @date 2012-11-5 上午9:07:13
 */
public class AutoGenerateHandOverBillService implements IAutoGenerateHandOverBillService{

	IAutoGenerateHandOverBillDao autoGenerateHandOverBillDao;
	
	public void setAutoGenerateHandOverBillDao(
			IAutoGenerateHandOverBillDao autoGenerateHandOverBillDao) {
		this.autoGenerateHandOverBillDao = autoGenerateHandOverBillDao;
	}

	/** 
	 * 自动生成交接单
	 * @author dp-duyi
	 * @date 2012-11-5 上午9:25:50
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IAutoGenerateHandOverBillService#createHandOverBill()
	 */
	@Override
	public void createHandOverBill(Date bizJobStartTime,Date bizJobEndTime,int threadCount, int threadNo) {
		autoGenerateHandOverBillDao.createHandOverBill( bizJobStartTime, bizJobEndTime, threadCount, threadNo);
	}

}
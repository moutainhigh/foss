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
 * PROJECT NAME	: pkp-job
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/job/WaybillStockThread.java
 * 
 * FILE NAME        	: WaybillStockThread.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.waybill.server.service.IWaybillStockService;
/**
 * 入库job 改线程
 *
 * @return the int
 * @author 278328-foss-hujinyang
 * @date 2015-10-27 上午9:17:13
 */
public class WaybillStockThread extends OrderThreadPoolCaller{

	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillStockThread.class);
	
	@Autowired
	private IWaybillStockService waybillStockService;
	
	
	@Override
	public int serviceCaller() {
		//开单货件入库，每10秒执行一次 任务改现成
		LOGGER.info("pkp-job-->WaybillStockThread-->serviceCaller开单货件入库，每10秒执行一次,线程处理开始!=============================================");

		try {
			// 执行轮询
			waybillStockService.batchjobs();
			//10秒执行一次
			setSleepSeconds(NumberConstants.NUMBER_10);
			return 0;
		} catch (Exception e) {
			LOGGER.info("pkp-job-->WaybillStockThread-->serviceCaller开单货件入库，每10秒执行一次,线程异常，原因:"+e.getMessage());
			throw  new BusinessException(e.getMessage());
		}finally{
			//开单货件入库，每10秒执行一次
			LOGGER.info("pkp-job-->WaybillStockThread-->serviceCaller开单货件入库，每10秒执行一次,线程处理结束!=============================================");
		}
	}

}
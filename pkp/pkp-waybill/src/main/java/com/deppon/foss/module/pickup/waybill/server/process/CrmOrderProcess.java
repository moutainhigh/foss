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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/process/CrmOrderProcess.java
 * 
 * FILE NAME        	: CrmOrderProcess.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.crm.inteface.foss.domain.UpdateOrderResponse;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;

/**
 * 
 * JMS远端服务请求回调处理
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-11-22 下午5:07:42,
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-11-22 下午5:07:42
 * @since
 * @version
 */
public class CrmOrderProcess implements ICallBackProcess {

	/**
	 * 日志
	 */
	protected final static Logger logger = LoggerFactory
			.getLogger(CrmOrderProcess.class.getName());

	/**
	 * 
	 * <p>CRM端JMS服务正常回调处理</p> 
	 * @author foss-sunrui
	 * @date 2012-12-24 下午8:49:16
	 * @param response
	 * @throws ESBException 
	 * @see com.deppon.esb.core.process.ICallBackProcess#callback(java.lang.Object)
	 */
	@Override
	public void callback(Object response) throws ESBException {
		// TODO Auto-generated method stub
		UpdateOrderResponse res = (UpdateOrderResponse) response;
		logger.info("订单状态修改正常回调：",res.getWaybillNumber());
	}

	/**
	 * 
	 * <p>CRM端JMS服务异常回调处理</p> 
	 * @author foss-sunrui
	 * @date 2012-12-24 下午8:49:19
	 * @param response
	 * @throws ESBException 
	 * @see com.deppon.esb.core.process.ICallBackProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public void errorHandler(Object response) throws ESBException {
		// TODO Auto-generated method stub
		UpdateOrderResponse res = (UpdateOrderResponse) response;
		logger.error("订单状态修改异常回调：",res.getErrorInfo());
	}

}
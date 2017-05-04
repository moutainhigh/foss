/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-web
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/callback/SaleDepartmentResultCallBackProcessor.java
 * 
 * FILE NAME        	: SaleDepartmentResultCallBackProcessor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.crm.inteface.domain.SyncMotorcadeResponse;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SyncMotorcadeResponseTrans;

/**
 * 接收CRM接收车队信息后的返回结果
 * 
 * @author 088933-foss-zhangjihegn
 * @date 2013-7-30 下午3:39:55
 */
public class MotorcadeResultCallBackProcessor implements ICallBackProcess {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(MotorcadeResultCallBackProcessor.class);

    @Override
    public void callback(Object response) throws ESBException {
	if (null != response) {
	    SyncMotorcadeResponse syncMotorcadeResponse = (SyncMotorcadeResponse) response;
	    LOGGER.info("send Motorcade info,then receive callback info.同步车队信息，收到回调信息：\n"
		    + new SyncMotorcadeResponseTrans()
			    .fromMessage(syncMotorcadeResponse));
	}
    }

    @Override
    public void errorHandler(Object errorResponse) throws ESBException {
	ErrorResponse info = (ErrorResponse)errorResponse;
	StringBuffer sb =new StringBuffer();
	sb.append(info.getBusinessId()).append(",描述1：")
		.append(info.getBusinessDesc1()).append(",描述2：")
		.append(info.getBusinessDesc2()).append(",描述3：")
		.append(info.getBusinessDesc3());

	LOGGER.error("ESB处理错误", sb.toString());
    }
}

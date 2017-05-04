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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/callback/DistrictResultCallBackProcessor.java
 * 
 * FILE NAME        	: DistrictResultCallBackProcessor.java
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

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SyncDistrictResponseTrans;
import com.deppon.ows.inteface.domain.SyncDistrictResponse;
 
/**
 * 用来存储行政区域数据更新后调用OWS系统的结果回调同步信息到数据库
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-15 下午5:01:10
 */
public class DistrictResultCallBackProcessor implements  ICallBackProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistrictResultCallBackProcessor.class);
    
    @Override
    public void callback(Object response) throws ESBException {
    	if (null != response) {
			SyncDistrictResponse syncDistrictResponse = (SyncDistrictResponse) response;
			LOGGER.info("send dictirct info,then receive callback info.同步行政区域，收到回调信息：\n"+ new SyncDistrictResponseTrans().fromMessage(syncDistrictResponse));
		}
    }

    @Override
    public void errorHandler(Object errorResponse) throws ESBException {
	ErrorResponse info = (ErrorResponse)errorResponse;
	StringBuilder sb =new StringBuilder();
	sb.append(info.getBusinessId()).append(",描述1：")
		.append(info.getBusinessDesc1()).append(",描述2：")
		.append(info.getBusinessDesc2()).append(",描述3：")
		.append(info.getBusinessDesc3());

	LOGGER.error("ESB处理错误", sb.toString());
    }
}

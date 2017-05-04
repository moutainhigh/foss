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
 * PROJECT NAME	: bse-lms-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/lmsitf/esb/client/TransferSiteInfoResultCallBackProcessor.java
 * 
 * FILE NAME        	: TransferSiteInfoResultCallBackProcessor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.lmsitf.esb.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.inteface.domain.vehicle.SiteInfoDisposeReult;
import com.deppon.esb.inteface.domain.vehicle.SiteInfocationResponse;
import com.deppon.esb.pojo.transformer.jaxb.SiteInfocationResponseTrans;
/**
 * 用来存储发送“外场信息”到LMS验证后的结果回调同步信息到数据库：无SUC
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-26 上午9:13:40</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-26 上午9:13:40
 * @since
 * @version
 */
public class TransferSiteInfoResultCallBackProcessor implements
	ICallBackProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferSiteInfoResultCallBackProcessor.class);
    
    @Override
    public void callback(Object response) throws ESBException {
	LOGGER.info(" ***************************** Began to record data ***************************** ");
	
	if (null != response) {
	    SiteInfocationResponse siteInfocationResponse = (SiteInfocationResponse) response;
	    
	    LOGGER.info(new SiteInfocationResponseTrans().fromMessage(siteInfocationResponse));
	    
	    LOGGER.info(" Success count : " + siteInfocationResponse.getSuccessCount());
	    LOGGER.info(" Failed count : " + siteInfocationResponse.getFailedCount());
	    for (SiteInfoDisposeReult siteInfoDispose : siteInfocationResponse.getSiteInfoDisposeReult()) {
		LOGGER.info("fieldpoleNo : " + siteInfoDispose.getFieldpoleNo());
		LOGGER.info("operateCode : " + siteInfoDispose.getOperateCode());
		LOGGER.info("result : " + siteInfoDispose.isResult());
		LOGGER.info("reason : " + siteInfoDispose.getReason());
		/**
		 * 暂时不提供，等平台做好日志
		 */
	    }
	}
	
	LOGGER.info(" ***************************** End to record data ***************************** ");
    }

    @Override
    public void errorHandler(Object errorResponse) throws ESBException {
	LOGGER.error("ESB处理错误");
    }
}

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
 * PROJECT NAME	: bse-sms-itf
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/smsitf/esb/client/SMSValidateResultCallBackProcessor.java
 * 
 * FILE NAME        	: SMSValidateResultCallBackProcessor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.smsitf.esb.client;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.domain.foss2sms.SmsResult;
import com.deppon.esb.pojo.transformer.json.SmsResultTrans;
import com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来存储发送“短信信息”到ESB验证后的结果回调同步信息到数据库：无SUC 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-19 下午6:51:53</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-19 下午6:51:53
 * @since
 * @version
 */
public class SMSValidateResultCallBackProcessor implements ICallBackProcess{

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSValidateResultCallBackProcessor.class);
    
    //短信验证操作结果Service
    private ISMSSendLogService smsSendLogService;
    
    @Override
    public void callback(Object response) throws ESBException {
	LOGGER.info(" ***************************** Begin to record data ***************************** ");
	
	if (null != response) {
	    SmsResult smsResult = (SmsResult) response;
	    
	    LOGGER.info(new SmsResultTrans().fromMessage(smsResult));
	    
	    List<String> failList = smsResult.getFailList();
	    if(CollectionUtils.isNotEmpty(failList)){
		for (String failIdentity : failList) {
		    SMSSendLogEntity smsSendLog = new SMSSendLogEntity();
		    smsSendLog.setUnionId(failIdentity);
		    smsSendLog = smsSendLogService.querySMSSendLog(smsSendLog);
		    if(null != smsSendLog){
			smsSendLog.setIsValidated(FossConstants.INACTIVE);
			smsSendLog.setReason(smsResult.isReason());
			smsSendLog.setResultCode(smsResult.getResultCode());
			smsSendLogService.updateSMSSendLogBySelective(smsSendLog);
		    }
		}
	    }
	}
	LOGGER.info(" ***************************** End to record data ***************************** ");
    }

    @Override
    public void errorHandler(Object errorResponse) throws ESBException {
	LOGGER.error("ESB处理错误");
    }
    
    /**
     * @param smsSendLogService the smsSendLogService to set
     */
    public void setSmsSendLogService(ISMSSendLogService smsSendLogService) {
        this.smsSendLogService = smsSendLogService;
    }
}

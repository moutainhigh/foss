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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/smsitf/esb/server/SMSSendResultNotificationProcessor.java
 * 
 * FILE NAME        	: SMSSendResultNotificationProcessor.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.smsitf.esb.server;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBBusinessException;
import com.deppon.esb.core.process.IProcess;
import com.deppon.esb.inteface.domain.sms.SMSProcessResultNotificationRequest;
import com.deppon.esb.inteface.domain.sms.SendResultInfo;
import com.deppon.esb.pojo.transformer.jaxb.SMSProcessResultNotificationRequestTrans;
import com.deppon.foss.module.base.common.api.server.service.ISMSFailLogService;
import com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.service.ISyncVoiceSmsStatusService;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来接受“短信发送失败日志”到并更新进入数据库：无SUC 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-19 下午7:29:02</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-19 下午7:29:02
 * @since
 * @version
 */
public class SMSSendResultNotificationProcessor implements IProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMSSendResultNotificationProcessor.class);
    
    //短信发送操作结果Service
    ISMSFailLogService smsFailLogService;
    //短信发送记录service
	ISMSSendLogService smsSendLogService;
	//同步短信语音发送状态service
	ISyncVoiceSmsStatusService  syncVoiceSmsStatusService ;

    @Override
    public Object process(Object req) throws ESBBusinessException {
	LOGGER.info(" ***************************** Begin to record data ***************************** ");
	
	if(null != req){
	    SMSProcessResultNotificationRequest smsProcessResultNotificationRequest = (SMSProcessResultNotificationRequest) req;
	    
	    LOGGER.info(new SMSProcessResultNotificationRequestTrans().fromMessage(smsProcessResultNotificationRequest));
	    
	    List<SendResultInfo> sendResultInfoList = smsProcessResultNotificationRequest.getSendResultInfos();
	    if (CollectionUtils.isNotEmpty(sendResultInfoList)) {
		for (SendResultInfo sendResultInfo : sendResultInfoList) {
		    SMSFailLogEntity smsFailLog = new SMSFailLogEntity();
		    smsFailLog.setFailIdentity(sendResultInfo.getSmsSequence());
		    smsFailLog.setFailReason(sendResultInfo.getFailReason());
		    smsFailLog.setIsSuccessed(sendResultInfo.isResult() ? FossConstants.ACTIVE : FossConstants.INACTIVE);
		    smsFailLogService.addSMSFailLog(smsFailLog);
		   //将返回结果传送给接送货
		    SMSSendLogEntity smsSendLog = new SMSSendLogEntity();
		    smsSendLog.setUnionId(smsFailLog.getFailIdentity());
		    SMSSendLogEntity   smsSendResultLog=smsSendLogService.querySMSSendLog(smsSendLog);
		    syncVoiceSmsStatusService.syncStatus(smsFailLog, smsSendResultLog);
		}
	    }
	}
	LOGGER.info(" ***************************** End to record data ***************************** ");
	//不需要响应返回值
	return null;
    }

    @Override
    public Object errorHandler(Object req) throws ESBBusinessException {
	LOGGER.error("ESB处理错误");
	return null;
    }

    /**
     * @param smsFailLogService the smsFailLogService to set
     */
    public void setSmsFailLogService(ISMSFailLogService smsFailLogService) {
        this.smsFailLogService = smsFailLogService;
    }

	public void setSmsSendLogService(ISMSSendLogService smsSendLogService) {
		this.smsSendLogService = smsSendLogService;
	}

	public void setSyncVoiceSmsStatusService(
			ISyncVoiceSmsStatusService syncVoiceSmsStatusService) {
		this.syncVoiceSmsStatusService = syncVoiceSmsStatusService;
	}

}

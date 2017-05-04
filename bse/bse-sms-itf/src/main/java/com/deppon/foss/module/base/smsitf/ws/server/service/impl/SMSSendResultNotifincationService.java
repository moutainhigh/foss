/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.base.smsitf.ws.server.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.common.api.server.service.ISMSFailLogService;
import com.deppon.foss.module.base.common.api.server.service.ISMSSendLogService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SendResultInfo;
import com.deppon.foss.module.base.smsitf.ws.server.service.ISMSSendResultNotifincationService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.ISyncVoiceSmsStatusService;
import com.deppon.foss.util.define.FossConstants;

/**
 * 获取发送短信结果服务端接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-6-9 上午10:42:43
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-6-9 上午10:42:43
 * @since
 * @version
 */
public class SMSSendResultNotifincationService implements ISMSSendResultNotifincationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SMSSendResultNotifincationService.class);
	// 短信发送操作结果Service
	ISMSFailLogService smsFailLogService;

	// 短信发送记录service
	ISMSSendLogService smsSendLogService;

	// 同步短信语音发送状态service
	ISyncVoiceSmsStatusService syncVoiceSmsStatusService;

	@Override
	@Transactional
	public void smsSendResult(List<SendResultInfo> smsReturnList) {
		if (CollectionUtils.isEmpty(smsReturnList)) {
			return;
		}
		LOGGER.info(" ***************************** Start to record data ***************************** ");
		for (SendResultInfo sendResultInfo : smsReturnList) {
			SMSFailLogEntity smsFailLog = new SMSFailLogEntity();
			smsFailLog.setFailIdentity(sendResultInfo.getSmsSequence());
			smsFailLog.setFailReason(sendResultInfo.getFailReason());
			smsFailLog.setIsSuccessed(sendResultInfo.isResult() ? FossConstants.ACTIVE
							: FossConstants.INACTIVE);
			smsFailLogService.addSMSFailLog(smsFailLog);
			// 将返回结果传送给接送货
			SMSSendLogEntity smsSendLog = new SMSSendLogEntity();
			smsSendLog.setUnionId(smsFailLog.getFailIdentity());
			SMSSendLogEntity smsSendResultLog = smsSendLogService.querySMSSendLog(smsSendLog);
			if(null != smsSendResultLog){
				syncVoiceSmsStatusService.syncStatus(smsFailLog, smsSendResultLog);	
			}
		}
		LOGGER.info(" ***************************** End to record data ***************************** ");
	} 
	public ISMSFailLogService getSmsFailLogService() {
		return smsFailLogService;
	}

	
	public void setSmsFailLogService(ISMSFailLogService smsFailLogService) {
		this.smsFailLogService = smsFailLogService;
	}

	
	public ISMSSendLogService getSmsSendLogService() {
		return smsSendLogService;
	}

	
	public void setSmsSendLogService(ISMSSendLogService smsSendLogService) {
		this.smsSendLogService = smsSendLogService;
	}

	
	public ISyncVoiceSmsStatusService getSyncVoiceSmsStatusService() {
		return syncVoiceSmsStatusService;
	}

	
	public void setSyncVoiceSmsStatusService(
			ISyncVoiceSmsStatusService syncVoiceSmsStatusService) {
		this.syncVoiceSmsStatusService = syncVoiceSmsStatusService;
	}
}

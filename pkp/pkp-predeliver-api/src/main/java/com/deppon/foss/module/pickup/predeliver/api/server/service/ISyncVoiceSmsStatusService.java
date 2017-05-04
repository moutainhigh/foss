package com.deppon.foss.module.pickup.predeliver.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.common.api.shared.domain.SMSFailLogEntity;
import com.deppon.foss.module.base.common.api.shared.domain.SMSSendLogEntity;

/**
 * 综合同步语音短信状态（仅限业务类型为NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY和NotifyCustomerConstants.BS_TYPE_PKP_SIGNRETURNPROCESS）
 * @author 038590-foss-wanghui
 * @date 2013-6-6 下午5:44:46
 */
public interface ISyncVoiceSmsStatusService extends IService {

	/**
	 * 
	 * 同步语音短信状态（仅限业务类型为NotifyCustomerConstants.BS_TYPE_PKP_NOTIFY和NotifyCustomerConstants.BS_TYPE_PKP_SIGNRETURNPROCESS）
	 * 
	 * @author 038590-foss-wanghui
	 * @date 2013-6-6 下午5:49:10
	 */
	void syncStatus(SMSFailLogEntity smsFailLog, SMSSendLogEntity smsSendLog);
}

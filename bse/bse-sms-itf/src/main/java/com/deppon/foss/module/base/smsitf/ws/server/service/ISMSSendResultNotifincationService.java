 
package com.deppon.foss.module.base.smsitf.ws.server.service;

import java.util.List;

import com.deppon.foss.module.base.common.api.shared.domain.SendResultInfo;

 
public interface ISMSSendResultNotifincationService {
 
	void smsSendResult(List<SendResultInfo> smsReturnList);
}

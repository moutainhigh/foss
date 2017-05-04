package com.deppon.foss.module.pickup.sign.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.sign.api.shared.dto.ValidateRfcAndCancelDto;

/** 
 * foss对接整车运单更改、作废财务规则校验
 * @author foss结算-306579-guoxinru 
 * @date 2016-5-18 下午4:46:18    
 */
public interface IValidateRfcAndCancelService extends IService {

	/**
	 * foss对接整车运单更改、作废财务规则校验
	 * @param validateDto
	 * @param currentInfo
	 */
	void validateRfcAndCancel(ValidateRfcAndCancelDto validateDto,CurrentInfo currentInfo);

	
}

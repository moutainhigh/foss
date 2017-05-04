package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.framework.service.IService;

public interface IPartnerStatementOfAwardReDeductService extends IService {
	
	/**
	 * 合伙人奖罚对账单重推
	 * @author 367752 
	 * @date 2016-10-14 
	 */
	public void reDeductPartnerStatementOfAward();

}

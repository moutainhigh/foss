package com.deppon.foss.module.settlement.pay.api.server.service;


import com.deppon.foss.framework.service.IService;

public interface IPartnerStatementOfAwardAutoDeductService extends IService {
	
	/**
	 * 合伙人奖罚对账单自动扣款接口
	 * @author 367752 
	 * @date 2016-09-01 
	 */
	public void autoDeductPartnerStatementOfAward();

}

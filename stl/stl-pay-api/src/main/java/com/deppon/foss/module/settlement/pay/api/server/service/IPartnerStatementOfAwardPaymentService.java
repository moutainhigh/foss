package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;

/** 
 * 合伙人奖罚对账单管理API接口
 * @author foss结算-306579-guoxinru 
 * @date 2016-2-25 下午5:15:25    
 */
public interface IPartnerStatementOfAwardPaymentService extends IService{

	/**
	 * 对账单付款
	 * @author 306579-guoxinru
	 * @date 2016-02-25
	 */
	String partnerStatementOfAwardToPayment(BillPaymentEntity paymentEntity,
			String statementBillNo, CurrentInfo currentInfo);

	
}

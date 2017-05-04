package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;

/**
 * 家装对账单付款
 * 
 * @ClassName: IHomeStatementPaymentService
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-12-10 下午3:12:07
 */
public interface IDopStatementPaymentService {
	/**
	 * 家装对账单付款
	 * 
	 * @Title: dopToPayment
	 * @author： 269052 |zhouyuan008@deppon.com
	 */
	public String dopToPayment(BillPaymentEntity paymentEntity,
			String statementBillNo, CurrentInfo currentInfo);

}

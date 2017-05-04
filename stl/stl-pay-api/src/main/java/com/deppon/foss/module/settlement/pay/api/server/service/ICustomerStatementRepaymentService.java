package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillStatementToPaymentQueryDto;

/**
 * 大客户对账单还款service
 * 
 *  @author 269044
 *  @date 2015-12-3
 */
public interface ICustomerStatementRepaymentService {

	/**
	 * 大客户对账单还款
	 * 
	 *  @author 269044
	 *  @date 2015-12-3
	 */
	String customerToRepayment(BillStatementToPaymentQueryDto billStatementToPaymentQueryDto,CurrentInfo cInfo);
}

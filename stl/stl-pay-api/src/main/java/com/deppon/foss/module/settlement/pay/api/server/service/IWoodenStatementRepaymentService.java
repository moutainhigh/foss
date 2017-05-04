package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillStatementToPaymentQueryDto;

public interface IWoodenStatementRepaymentService {

	String woodenToRepayment(BillStatementToPaymentQueryDto billStatementToPaymentQueryDto,CurrentInfo cInfo);

}

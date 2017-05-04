package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;

public interface IWoodenStatementPaymentService {

	String woodenToPayment(BillPaymentEntity paymentEntity, String statementBillNo,	CurrentInfo currentInfo, String factoring);

}

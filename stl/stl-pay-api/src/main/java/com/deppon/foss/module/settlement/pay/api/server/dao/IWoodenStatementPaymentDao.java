package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.Map;

public interface IWoodenStatementPaymentDao {
	
	void addBillPaymentD(String paymentNum, String statementBillNo);

	@SuppressWarnings("rawtypes")
	void payForBillPayable(Map map);

}
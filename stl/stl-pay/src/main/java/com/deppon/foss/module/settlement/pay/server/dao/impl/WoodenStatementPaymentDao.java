package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.HashMap;
import java.util.Map;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.IWoodenStatementPaymentDao;

public class WoodenStatementPaymentDao extends iBatis3DaoImpl implements IWoodenStatementPaymentDao {
	public static final String NAMESPACES = "foss.stl.WoodenStatementPaymentDao.";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addBillPaymentD(String paymentNum, String statementBillNo) {
		Map map = new HashMap();
		map.put("paymentNum", paymentNum);
		map.put("statementBillNo", statementBillNo);
		this.getSqlSession().insert(NAMESPACES + "addBillPaymentD", map);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void payForBillPayable(Map map) {
		this.getSqlSession().update(NAMESPACES + "payForBillPayable", map);
	}

}

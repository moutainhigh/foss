package com.deppon.foss.module.settlement.pay.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.pay.api.server.dao.IPartnerStatementPaymentDao;

public class PartnerStatementPaymentDao extends iBatis3DaoImpl implements IPartnerStatementPaymentDao {
	public static final String NAMESPACES = "foss.stl.PartnerStatementPaymentDao.";

	/**
	 * 添加付款单明细
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addBillPaymentD(String paymentNum, List<String> statementBillNos) {
		Map map = new HashMap();
		map.put("paymentNum", paymentNum);
		map.put("statementBillNos", statementBillNos);
		this.getSqlSession().insert(NAMESPACES + "addBillPaymentD", map);
	}
	/**
	 * 付款更新应付单信息
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void payForBillPayable(Map map) {
		this.getSqlSession().update(NAMESPACES + "payForBillPayable", map);
	}
	
	/**
	 * 回调更新应付单信息
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void updatePayableForRollBack(Map map) {
		this.getSqlSession().update(NAMESPACES + "updatePayableForRollBack", map);
	}
	
	/**
	 * 删除明细更新应付单信息 
	 * @param map
	 * @return
	 */
	@Override
	public int updatePayableForDel(Map<String, String> map){
		return this.getSqlSession().update(NAMESPACES + "updatePayableForDel", map);
	}
	
	/* 
	 * 批量更新应付单中的对账单号
	 * @see com.deppon.foss.module.settlement.pay.api.server.dao.IPartnerStatementPaymentDao#batchUpdatePayableStatementBillNo(java.util.Map)
	 */
	@Override
	public int batchUpdatePayableStatementBillNo(Map<String, Object> params) {
		return this.getSqlSession().update(NAMESPACES + "batchUpdatePayableStatementBillNo", params);
	}
}

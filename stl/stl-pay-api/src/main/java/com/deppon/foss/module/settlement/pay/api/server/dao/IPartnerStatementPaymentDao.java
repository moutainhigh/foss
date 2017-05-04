package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;
import java.util.Map;

public interface IPartnerStatementPaymentDao {
	/**
	 * 添加付款单明细
	 * @param paymentNum
	 * @param statementBillNo
	 */
	void addBillPaymentD(String paymentNum, List<String> statementBillNos);

	/**
	 * 付款更新应付单信息
	 * @param map
	 */
	@SuppressWarnings("rawtypes")
	void payForBillPayable(Map map);

	/**
	 * 回调更新应付单信息
	 * @author 311396 wwb
	 * @date 2016-03-04 下午 3:35:32
	 */
	@SuppressWarnings("rawtypes")
	void updatePayableForRollBack(Map map);
	
	/**
	 * 删除明细更新应付单信息 
	 * @param map
	 * @return
	 */
	int updatePayableForDel(Map<String, String> map);

	/**
	 * 删除对账单明细批量更新应付单中的对账单号
	 * @author gpz
	 * @date 2016年12月7日
	 */
	int batchUpdatePayableStatementBillNo(Map<String, Object> params);
}
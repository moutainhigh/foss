package com.deppon.foss.module.settlement.pay.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;

public interface IPartnerStatementPaymentService {
	/**
	 * 付款添加付款单
	 * @author 311396 wwb
	 * @date 2016-03-04 下午 3:35:32
	 * @param paymentNum
	 * @param statementBillNo
	 */
	void addBillPaymentD(String paymentNum, List<String> statementBillNos);

	/**
	 * 付款更新应付单信息
	 * @author 311396 wwb
	 * @date 2016-03-04 下午 3:35:32
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
	 * @author 311396 wwb
	 * @date 2016-03-04 下午 3:35:32
	 */
	int updatePayableForDel(Map<String, String> map);
	/**
	 * 根据应付单号查询应付单
	 * @author 311396 wwb
	 * @date 2016-03-11 下午 3:35:32
	 */
	BillPayableEntity queryPayableByPayableNo(String payableNo);

	/**
	 * 批量更新应付单中的对账单号
	 * @author gpz
	 * @date 2016年12月7日
	 */
	int batchUpdatePayableStatementBillNo(Map<String, Object> params);
	
}

package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.Map;

/** 
 * 合伙人奖罚对账单管理付款
 * @author foss结算-306579-guoxinru 
 * @date 2016-2-25 下午6:33:39    
 */
public interface IPartnerStatementOfAwardPaymentDao {

	/** 
	 * 新增合伙人奖罚付款明细
	 * @author foss结算-306579-guoxinru 
	 * @date 2016-2-26  
	 */
	void addBillPaymentD(String paymentNum, String statementBillNo);

	/** 
	 * 更新付款单状态、版本号、付款金额
	 * @author foss结算-306579-guoxinru 
	 * @date 2016-2-26  
	 */
	void payForBillPayable(Map payableMap);

	
}

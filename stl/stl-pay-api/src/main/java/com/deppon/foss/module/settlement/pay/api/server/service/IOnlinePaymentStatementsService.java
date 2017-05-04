package com.deppon.foss.module.settlement.pay.api.server.service;



import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOAOnlineResultListDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.StatementOnlineQueryDto;
/**
 * 
 * SERVICE:网上支付对账单
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-30 上午8:18:38
 */
public interface IOnlinePaymentStatementsService extends IService {
	/**
	 * 
	 * 查询对账单
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 上午8:40:38
	 */
	BillSOAOnlineResultListDto queryStatementOnline(StatementOnlineQueryDto queryDto);
	
	/**
	 * 
	 * 锁定对账单
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 上午8:40:42
	 */
	void lockStatementOnline(StatementOnlineQueryDto queryDto);
	
	/**
	 * 
	 * 按对账单还款
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-30 上午8:40:46
	 */
	void paymentStatementOnline(StatementOnlineQueryDto queryDto);
	
}

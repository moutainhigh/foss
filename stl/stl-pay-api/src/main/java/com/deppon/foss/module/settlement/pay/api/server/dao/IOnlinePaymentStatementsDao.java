package com.deppon.foss.module.settlement.pay.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOAOnlineResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillSOAOnlineResultListDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.StatementOnlineQueryDto;

/**
 * 
 * DAO：对账单网上支付服务接口
 * @author 088933-foss-zhangjiheng
 * @date 2012-11-29 下午5:38:32
 */
public interface IOnlinePaymentStatementsDao {
	/**
	 * 
	 * 按客户编码和日期查询对账单信息(求合计信息)
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-29 下午6:49:36
	 */
	BillSOAOnlineResultListDto queryCountStatementByCustomer(StatementOnlineQueryDto queryDto);
	
	/**
	 * 
	 * 按客户编码和日期查询对账单信息（分页）
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-29 下午6:49:40
	 */
	 List<BillSOAOnlineResultDto> queryStatementByCustomer(StatementOnlineQueryDto queryDto,int pageNo,int pageSize);
	
	/**
	 * 
	 * 按对账单号查询对账单
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-29 下午6:49:44
	 */
	BillSOAOnlineResultDto queryStatementByNo(StatementOnlineQueryDto queryDto);
	
	/**
	 * 
	 * 锁定对账单信息
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-29 下午6:54:15
	 */
	int lockOnlinePaymentStatementBill(StatementOnlineQueryDto queryDto);
}

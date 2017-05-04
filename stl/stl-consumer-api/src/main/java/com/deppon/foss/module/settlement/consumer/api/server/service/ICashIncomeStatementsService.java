package com.deppon.foss.module.settlement.consumer.api.server.service;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsResultDto;

/**
 * 现金收入明细报表Service 接口
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-20 上午10:52:49
 * @since
 * @version
 */
public interface ICashIncomeStatementsService {

	/**
	 * 根据不同的查询条件查询所有的（所有）现金收入明细记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午3:19:25
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	CashIncomeStatementsResultDto queryByCondition(
			CashIncomeStatementsQueryDto dto, Integer start, Integer limit);
	
	
	/**
	 * 统计各种汇总金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:07:52
	 * @param dto
	 * @return
	 */
	CashIncomeStatementsResultDto queryTotalAmountByCondition(
			CashIncomeStatementsQueryDto dto);

	/**
	 * 当选择单据类型为现金收款单时,查询符合条件的现金收款单记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:56:08
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	CashIncomeStatementsResultDto queryBillCashByCondition(
			CashIncomeStatementsQueryDto dto, Integer start, Integer limit);
	
	/**
	 *当选择单据类型为现金收款单时,统计符合条件的现金收款单的总金额
	 *
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:08:51
	 * @param dto
	 * @return
	 */
	CashIncomeStatementsResultDto queryBillCashTotalAmountByCondition(CashIncomeStatementsQueryDto dto);

	/**
	 * 根据运单号查询所有的现金收款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:56:08
	 * @param dto
	 * @return
	 * @see
	 */
	CashIncomeStatementsResultDto queryByWaybillNos(
			CashIncomeStatementsQueryDto dto);
	
	/**
	 * 根据运单号集合，统计所有现金收款单的金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:09:55
	 * @param dto
	 * @return
	 */
	CashIncomeStatementsResultDto queryTotalAmountByWaybillNos(CashIncomeStatementsQueryDto dto);

	/**
	 * 选择的单据类型为还款单时，查询符合条件的还款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:56:08
	 * @param dto
	 * @param start
	 * @param limit
	 * @see
	 */
	CashIncomeStatementsResultDto queryBillRepaymentByCondition(
			CashIncomeStatementsQueryDto dto, Integer start, Integer limit);
	
	/**
	 * 选择的单据类型为还款单时，查询符合条件的还款单总金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午5:10:49
	 * @param dto
	 * @return
	 */
	CashIncomeStatementsResultDto queryBillRepaymentTotalAmountByCondition(
			CashIncomeStatementsQueryDto dto);

	/**
	 * 根据对账单号集合，查询还款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:56:08
	 * @param dto
	 * @see
	 */
	CashIncomeStatementsResultDto queryBillRepaymentByStatementBillNOs(
			CashIncomeStatementsQueryDto dto);
	
	/**
	 * 根据对账单号集合，统计还款单金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:54:28
	 * @param dto
	 * @return
	 */
	CashIncomeStatementsResultDto queryBillRepaymentTotalAmountByStatementBillNOs(CashIncomeStatementsQueryDto dto);

	/**
	 * 根据还款单号集合,查询还款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:56:08
	 * @param dto
	 * @return
	 * @see
	 */
	CashIncomeStatementsResultDto queryBillRepaymentByNOs(
			CashIncomeStatementsQueryDto dto);

	/**
	 * 根据还款单号集合，查询还款单的金额信息
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:55:09
	 * @param dto
	 * @return
	 */
	CashIncomeStatementsResultDto queryBillRepaymentTotalAmountByNOs(CashIncomeStatementsQueryDto dto);
	
	/**
	 * 选择的单据类型为预收单时，统计符合条件的预收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:56:08
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	CashIncomeStatementsResultDto queryBillDepositReceivedByCondition(
			CashIncomeStatementsQueryDto dto, Integer start, Integer limit);
	
	/**
	 * 选择的单据类型为预收单时，统计符合条件的预收单金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午5:21:43
	 * @param dto
	 * @return
	 */
	CashIncomeStatementsResultDto queryBillDepositReceivedTotalAmountByCondition(
			CashIncomeStatementsQueryDto dto);

	/**
	 * 根据预收单号集合，查询预收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:56:08
	 * @param dto
	 * @return
	 * @see
	 */
	CashIncomeStatementsResultDto queryBillDepositReceivedByNOs(
			CashIncomeStatementsQueryDto dto);
	
	/**
	 * 根据预收单号集合，统计预收单金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:57:24
	 * @param dto
	 * @return
	 */
	CashIncomeStatementsResultDto queryBillDepositReceivedTotalAmountByNOs(CashIncomeStatementsQueryDto dto);

}

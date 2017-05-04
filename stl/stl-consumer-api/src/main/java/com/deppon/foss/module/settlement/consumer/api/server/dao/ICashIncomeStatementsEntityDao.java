package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsQueryDto;

/**
 * 现金收入明细报表 DAO
 * 包含：（现金收款单、还款单、预收单）
 * @author 099995-foss-wujiangtao
 * @date 2012-11-7 下午3:11:37
 * @since
 * @version
 */
public interface ICashIncomeStatementsEntityDao {
	
	/**
	 * 根据不同的查询条件查询所有的（所有）现金收入明细记录
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 下午5:13:47
	 * @param dto
	 * @param start  开始记录数
	 * @param limit  分页最大记录数
	 * @return
	 * @see
	 */
	List<CashIncomeStatementsDto>   queryByCondition(CashIncomeStatementsQueryDto dto,Integer start,Integer limit);

	/**
	 * 根据不同的查询条件查询所有的（实收单）现金收入明细记录总行数 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午1:54:08
	 * @param dto
	 * @return
	 * @see
	 */
	java.lang.Long queryTotalCountByConition(CashIncomeStatementsQueryDto dto);
	
	/**
	 * 统计各种汇总金额
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午5:39:02
	 * @param dto
	 * @return
	 * @see
	 */
	List<CashIncomeStatementsDto>	queryTotalAmountByCondition(CashIncomeStatementsQueryDto dto);
	
	
	/**
	 * 当选择单据类型为现金收款单时,查询符合条件的现金收款单记录
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午7:40:59
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<CashIncomeStatementsDto> queryBillCashByCondition(CashIncomeStatementsQueryDto dto,Integer start,Integer limit);
	
	/**
	 * 当选择单据类型为现金收款单时,统计符合条件的记录总行数
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午7:43:10
	 * @param dto
	 * @return
	 * @see
	 */
	java.lang.Long queryBillCashTotalCountByCondition(CashIncomeStatementsQueryDto dto);
	
	/**
	 * 当选择单据类型为现金收款单时,统计符合条件的总金额
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午7:47:46
	 * @param dto
	 * @see
	 */
	List<CashIncomeStatementsDto> queryBillCashTotalAmountByCondition(CashIncomeStatementsQueryDto dto);
	
	/**
	 * 根据运单号查询所有的现金收款单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 下午5:18:14
	 * @param dto
	 * @return
	 * @see
	 */
	List<CashIncomeStatementsDto>   queryByWaybillNos(CashIncomeStatementsQueryDto dto);
	
	/**
	 * 根据运单号集合，统计所有的现金收款单的总金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:10:57
	 * @param dto
	 * @return
	 */
	List<CashIncomeStatementsDto> queryTotalAmountByWaybillNos(CashIncomeStatementsQueryDto dto);
	
	
	/**
	 * 选择的单据类型为还款单时，查询还款单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午8:19:05
	 * @param dto
	 * @param start
	 * @param limit
	 * @see
	 */
	List<CashIncomeStatementsDto> queryBillRepaymentByCondition(CashIncomeStatementsQueryDto dto,Integer start,Integer limit);
	
	/**
	 * 选择的单据类型为还款单时，统计符合条件的还款单总行数
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午8:20:25
	 * @param dto
	 * @return
	 * @see
	 */
	java.lang.Long queryBillRepaymentTotalCountByCondition(CashIncomeStatementsQueryDto dto);
	
	/**
	 * 选择的单据类型为还款单时，统计符合条件的还款单总金额
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午8:21:14
	 * @param dto
	 * @return
	 * @see
	 */
	List<CashIncomeStatementsDto> queryBillRepaymentTotalAmountByCondition(CashIncomeStatementsQueryDto dto);
	
	/**
	 * 根据对账单号查询还款单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午8:22:03
	 * @param dto
	 * @see
	 */
	List<CashIncomeStatementsDto> queryBillRepaymentByStatementBillNOs(CashIncomeStatementsQueryDto dto);
	
	/**
	 * 根据对账单号集合，统计还款单金额信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:12:17
	 * @param dto
	 * @return
	 */
	List<CashIncomeStatementsDto> queryBillRepaymentTotalAmountByStatementBillNOs(CashIncomeStatementsQueryDto dto);
	
	/**
	 * 根据还款单号集合查询还款单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午8:22:52
	 * @param dto
	 * @return
	 * @see
	 */
	List<CashIncomeStatementsDto> queryBillRepaymentByNOs(CashIncomeStatementsQueryDto dto);
	
	/**
	 * 根据还款单号集合，查询还款单的金额信息
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:12:53
	 * @param dto
	 * @return
	 */
	List<CashIncomeStatementsDto> queryBillRepaymentTotalAmountByNOs(CashIncomeStatementsQueryDto dto);
	
	/**
	 *  选择的单据类型为预收单时，统计符合条件的预收单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午8:23:40
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<CashIncomeStatementsDto> queryBillDepositReceivedByCondition(CashIncomeStatementsQueryDto dto,Integer start,Integer limit);
	
	/**
	 * 选择的单据类型为预收单时，统计符合条件的预收单的总行数
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午8:24:34
	 * @param dto
	 * @return
	 * @see
	 */
	java.lang.Long queryBillDepositReceivedTotalCountByCondition(CashIncomeStatementsQueryDto dto);
	
	
	/**
	 * 选择的单据类型为预收单时，统计符合条件的预收单的总金额
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午8:25:19
	 * @param dto
	 * @return
	 * @see
	 */
	List<CashIncomeStatementsDto> queryBillDepositReceivedTotalAmountByCondition(CashIncomeStatementsQueryDto dto);
	
	/**
	 * 根据预收单号集合，查询预收单信息
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午8:27:18
	 * @param dto
	 * @return
	 * @see
	 */
	List<CashIncomeStatementsDto> queryBillDepositReceivedByNOs(CashIncomeStatementsQueryDto dto);
	
	/**
	 * 根据预收单号集合，统计预收单金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:13:51
	 * @param dto
	 * @return
	 */
	List<CashIncomeStatementsDto> queryBillDepositReceivedTotalAmountByNOs(CashIncomeStatementsQueryDto dto);
	
	
	
	
	
	
}

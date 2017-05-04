package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICashIncomeStatementsEntityDao;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsQueryDto;

/**
 * 现金收入明细报表 DAO 包含：（现金收款单、还款单、预收单）
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-7 下午5:40:44
 * @since
 * @version
 */
public class CashIncomeStatementsEntityDao extends iBatis3DaoImpl implements
		ICashIncomeStatementsEntityDao {

	private static final String NAMESPACE = "foss.stl.CashIncomeStatementsEntityDao.";

	/**
	 * 根据不同的查询条件查询所有的（实收单）现金收入明细记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 下午5:40:37
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashIncomeStatementsDto> queryByCondition(
			CashIncomeStatementsQueryDto dto, Integer start, Integer limit) {
		List<CashIncomeStatementsDto> list = new ArrayList<CashIncomeStatementsDto>();
		if (dto != null && (!dto.isPaging() || validateParam(start, limit))) {
			if (dto.isPaging()) {
				RowBounds rowBounds = new RowBounds(start, limit);
				return this.getSqlSession()
						.selectList(NAMESPACE + "selectCashInStByCondition",
								dto, rowBounds);
			} else {
				return this.getSqlSession().selectList(
						NAMESPACE + "selectCashInStByCondition", dto);
			}
		}
		return list;
	}

	/**
	 * 验证数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:49:10
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	private boolean validateParam(Integer start, Integer limit) {
		if (start != null && limit != null) {
			return true;
		}
		return false;
	}

	/**
	 * 根据不同的查询条件查询所有的（实收单）现金收入明细记录总行数
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午1:51:24
	 * @param dto
	 * @return
	 * @see
	 */
	public java.lang.Long queryTotalCountByConition(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null) {
			Object obj = this.getSqlSession().selectOne(
					NAMESPACE + "selectCashInStTotalCountByCondition", dto);
			if (obj != null) {
				return Long.valueOf(obj.toString());
			}
		}
		return null;
	}

	/**
	 * 统计各种汇总金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午5:40:48
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CashIncomeStatementsDto> queryTotalAmountByCondition(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null) {
			return this.getSqlSession().selectList(
					NAMESPACE + "selectTotalAmountByCondition", dto);
		}
		return null;
	}

	/**
	 * 当选择单据类型为现金收款单时,查询符合条件的现金收款单记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午7:48:54
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashIncomeStatementsDto> queryBillCashByCondition(
			CashIncomeStatementsQueryDto dto, Integer start, Integer limit) {
		if (dto != null && (!dto.isPaging() || validateParam(start, limit))) {
			if (dto.isPaging()) {// 需要分页
				RowBounds rowBounds = new RowBounds(start, limit);
				return this.getSqlSession()
						.selectList(NAMESPACE + "selectBillCashByCondition",
								dto, rowBounds);
			} else {
				return this.getSqlSession().selectList(
						NAMESPACE + "selectBillCashByCondition", dto);
			}
		}
		return null;
	}

	/**
	 * 当选择单据类型为现金收款单时,统计符合条件的记录总行数
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午7:49:45
	 * @param dto
	 * @return
	 */
	@Override
	public Long queryBillCashTotalCountByCondition(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null) {
			Object obj = this.getSqlSession().selectOne(
					NAMESPACE + "selectBillCashTotalCountByCondition", dto);
			if (obj != null) {
				return Long.valueOf(obj.toString());
			}
		}
		return null;
	}

	/**
	 * 当选择单据类型为现金收款单时,统计符合条件的总金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午7:49:58
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashIncomeStatementsDto> queryBillCashTotalAmountByCondition(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null) {
			return this.getSqlSession().selectList(
					NAMESPACE + "selectBillCashTotalAmountByCondition", dto);
		}
		return null;
	}

	/**
	 * 根据运单号查询所有的现金收款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-7 下午5:42:17
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashIncomeStatementsDto> queryByWaybillNos(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null) {
			return this.getSqlSession().selectList(
					NAMESPACE + "selectBillCashByWaybillNOs", dto);
		}
		return null;
	}

	/**
	 * 选择的单据类型为还款单时，查询还款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:11:07
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashIncomeStatementsDto> queryBillRepaymentByCondition(
			CashIncomeStatementsQueryDto dto, Integer start, Integer limit) {
		if (dto != null && (!dto.isPaging() || validateParam(start, limit))) {
			if (dto.isPaging()) {
				RowBounds rowBounds = new RowBounds(start, limit);
				return this.getSqlSession().selectList(
						NAMESPACE + "selectBillRepaymentByCondition", dto,
						rowBounds);
			} else {// 不分页后续可能会考虑只能查询出多少条数据
				return this.getSqlSession().selectList(
						NAMESPACE + "selectBillRepaymentByCondition", dto);
			}

		}
		return null;
	}

	/**
	 * 选择的单据类型为还款单时，统计符合条件的还款单总行数
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:20:02
	 * @param dto
	 * @return
	 */
	@Override
	public Long queryBillRepaymentTotalCountByCondition(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null) {
			Object obj = this
					.getSqlSession()
					.selectOne(
							NAMESPACE
									+ "selectBillRepaymentTotalCountByCondition",
							dto);
			if (obj != null) {
				return Long.valueOf(obj.toString());
			}
		}
		return null;
	}

	/**
	 * 选择的单据类型为还款单时，统计符合条件的还款单总金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:20:58
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashIncomeStatementsDto> queryBillRepaymentTotalAmountByCondition(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null) {
			return this.getSqlSession().selectList(
					NAMESPACE + "selectBillRepaymentTotalAmountByCondition",
					dto);
		}
		return null;
	}

	/**
	 * 根据对账单号查询还款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:22:42
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashIncomeStatementsDto> queryBillRepaymentByStatementBillNOs(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null) {
			return this.getSqlSession().selectList(
					NAMESPACE + "selectBillRepaymentByStatementBillNOs", dto);
		}
		return null;
	}

	/**
	 * 根据还款单号集合查询还款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:24:10
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashIncomeStatementsDto> queryBillRepaymentByNOs(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null) {
			return this.getSqlSession().selectList(
					NAMESPACE + "selectBillRepaymentByNOs", dto);
		}
		return null;
	}

	/**
	 * 选择的单据类型为预收单时，统计符合条件的预收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:24:52
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashIncomeStatementsDto> queryBillDepositReceivedByCondition(
			CashIncomeStatementsQueryDto dto, Integer start, Integer limit) {
		if (dto != null && (!dto.isPaging() || validateParam(start, limit))) {
			if (dto.isPaging()) {
				RowBounds rowBounds = new RowBounds(start, limit);
				return this.getSqlSession().selectList(
						NAMESPACE + "selectBillDepositReceivedByCondition",
						dto, rowBounds);
			} else {
				return this
						.getSqlSession()
						.selectList(
								NAMESPACE
										+ "selectBillDepositReceivedByCondition",
								dto);
			}
		}
		return null;
	}

	/**
	 * 选择的单据类型为预收单时，统计符合条件的预收单的总行数
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:26:26
	 * @param dto
	 * @return
	 */
	@Override
	public Long queryBillDepositReceivedTotalCountByCondition(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null) {
			Object obj = this.getSqlSession().selectOne(
					NAMESPACE
							+ "selectBillDepositReceivedTotalCountByCondition",
					dto);
			if (obj != null) {
				return Long.valueOf(obj.toString());
			}
		}
		return null;
	}

	/**
	 * 选择的单据类型为预收单时，统计符合条件的预收单的总金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:27:42
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashIncomeStatementsDto> queryBillDepositReceivedTotalAmountByCondition(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null) {
			return this
					.getSqlSession()
					.selectList(
							NAMESPACE
									+ "selectBillDepositReceivedTotalAmountByCondition",
							dto);
		}
		return null;
	}

	/**
	 * 根据预收单号集合，查询预收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午8:28:32
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashIncomeStatementsDto> queryBillDepositReceivedByNOs(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null) {
			return this.getSqlSession().selectList(
					NAMESPACE + "selectBillDepositReceivedByNOs", dto);
		}
		return null;
	}

	/********************************************** 打印打印模板中只需要：总金额信息 *********************************/

	/**
	 * 根据运单号集合，统计所有的现金收款单的总金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:15:32
	 * @param dto
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<CashIncomeStatementsDto> queryTotalAmountByWaybillNos(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null && CollectionUtils.isNotEmpty(dto.getWaybillNos())) {
			return this.getSqlSession().selectList(
					NAMESPACE + "selectTotalAmountByWaybillNos", dto);
		}
		return null;
	}

	/**
	 * 根据对账单号集合，统计还款单金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:16:24
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashIncomeStatementsDto> queryBillRepaymentTotalAmountByStatementBillNOs(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null
				&& CollectionUtils.isNotEmpty(dto.getStatementBillNos())) {
			return this
					.getSqlSession()
					.selectList(
							NAMESPACE
									+ "selectBillRepaymentTotalAmountByStatementBillNOs",
							dto);
		}
		return null;
	}

	/**
	 * 根据还款单号集合，查询还款单的金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:30:19
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashIncomeStatementsDto> queryBillRepaymentTotalAmountByNOs(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null && CollectionUtils.isNotEmpty(dto.getRepaymentNos())) {
			return this.getSqlSession().selectList(
					NAMESPACE + "selectBillRepaymentTotalAmountByNOs", dto);
		}
		return null;
	}

	/**
	 * 根据预收单号集合，统计预收单金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:30:28
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CashIncomeStatementsDto> queryBillDepositReceivedTotalAmountByNOs(
			CashIncomeStatementsQueryDto dto) {
		if (dto != null
				&& CollectionUtils.isNotEmpty(dto.getDepositReceivedNos())) {
			return this.getSqlSession().selectList(
					NAMESPACE + "selectBillDepositReceivedTotalAmountByNOs",
					dto);
		}
		return null;
	}
}

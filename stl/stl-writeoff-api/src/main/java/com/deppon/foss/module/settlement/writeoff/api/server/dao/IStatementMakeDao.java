package com.deppon.foss.module.settlement.writeoff.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementOfAccountMakeQueryDto;

/**
 * 制作对账单DAO接口类
 * 
 * @author dp-zhangjiheng
 * @date 2012-10-12 上午8:51:02
 */

public interface IStatementMakeDao {

	/**
	 * 制作对账单，按日期查询应收单信息
	 * 
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 * @param statementOfAccountMakeQueryDto
	 *            制作对账单查询Dto
	 * @return List<StatementOfAccountDEntity>
	 * 				对账单明细集合
	 */
	List<StatementOfAccountDEntity> queryYSMakeSOAByParams(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);

	/**
	 * 制作对账单，按应收单号查询应收单信息
	 * 
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 * @param statementOfAccountMakeQueryDto
	 *            制作对账单查询Dto
	 * @return List<StatementOfAccountDEntity>
	 * 				对账单明细集合
	 */
	List<StatementOfAccountDEntity> queryYSMakeSOAByReceivabeNos(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);

	/**
	 * 制作对账单，按运单号查询应收单信息
	 * 
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 * @param statementOfAccountMakeQueryDto
	 *            制作对账单查询Dto
	 * @return List<StatementOfAccountDEntity>
	 * 				对账单明细集合
	 */
	List<StatementOfAccountDEntity> queryYSMakeSOAByWaybillNos(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);

	/**
	 * 
	 * 制作对账单，按航空正单开始和结束单号查询应收单信息
	 * @author foss-wangxuemin
	 * @date May 27, 2013 11:32:22 AM
	 */
	List<StatementOfAccountDEntity> queryYSMakeSOAByAirWaybillBeginAndEndNo(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);

	/**
	 * 制作对账单，按日期查询应付单信息
	 * 
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 * @param statementOfAccountMakeQueryDto
	 *            制作对账单查询Dto
	 * @return List<StatementOfAccountDEntity>
	 * 				对账单明细集合
	 */
	List<StatementOfAccountDEntity> queryYFMakeSOAByParams(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);
	
	List<StatementOfAccountDEntity> queryYFKYMakeSOAByParams(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);

	/**
	 * 制作对账单，按应付单号查询应付单信息
	 * 
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 * @param statementOfAccountMakeQueryDto
	 *            制作对账单查询Dto
	 * @return List<StatementOfAccountDEntity>
	 * 				对账单明细集合
	 */
	List<StatementOfAccountDEntity> queryYFMakeSOAByPayableNos(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);

	List<StatementOfAccountDEntity> queryYFKYMakeSOAByPayableNos(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);

	/**
	 * 制作对账单，按运单号查询应付单信息
	 * 
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 * @param statementOfAccountMakeQueryDto
	 *            制作对账单查询Dto
	 * @return List<StatementOfAccountDEntity>
	 * 				对账单明细集合
	 */
	List<StatementOfAccountDEntity> queryYFMakeSOAByWaybillNos(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);
	

	List<StatementOfAccountDEntity> queryYFKYMakeSOAByWaybillNos(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);

	/**
	 * 
	 * 制作对账单，按航空正单开始和结束单号查询应付单信息
	 * @author foss-wangxuemin
	 * @date May 27, 2013 11:31:46 AM
	 */
	List<StatementOfAccountDEntity> queryYFMakeSOAByAirWaybillBeginAndEndNo(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);
	

	List<StatementOfAccountDEntity> queryYFKYMakeSOAByAirWaybillBeginAndEndNo(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);

	/**
	 * 制作对账单，按日期查询预收单信息
	 * 
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 * @param statementOfAccountMakeQueryDto
	 *            制作对账单查询Dto
	 * @return List<StatementOfAccountDEntity>
	 * 				对账单明细集合
	 */
	List<StatementOfAccountDEntity> queryUSMakeSOAByParams(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);

	/**
	 * 制作对账单，按预收单号查询预收单信息
	 * 
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 * @param statementOfAccountMakeQueryDto
	 *            制作对账单查询Dto
	 * @return List<StatementOfAccountDEntity>
	 * 				对账单明细集合
	 */
	List<StatementOfAccountDEntity> queryUSMakeSOAByReceivedNos(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);

	/**
	 * 制作对账单，按日期查询预付单信息
	 * 
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 * @param statementOfAccountMakeQueryDto
	 *            制作对账单查询Dto
	 * @return List<StatementOfAccountDEntity>
	 * 				对账单明细集合
	 */
	List<StatementOfAccountDEntity> queryUFMakeSOAByParams(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);

	/**
	 * 制作对账单，按日期查询预付单信息
	 * 
	 * @author dp-zhangjiheng
	 * @date 2012-10-12 上午8:58:31
	 * @param statementOfAccountMakeQueryDto
	 *            制作对账单查询Dto
	 * @return List<StatementOfAccountDEntity>
	 * 				对账单明细集合
	 */
	List<StatementOfAccountDEntity> queryUFMakeSOAByAdvancedNos(
			StatementOfAccountMakeQueryDto statementOfAccountMakeQueryDto);

	/**
	 * 按单号查询应收单
	 * @author dengdawei
	 * @param recNos
	 * @param active
	 * @return
	 */
	List<BillReceivableEntity> queryByReceivableNOs(List<String> recNos,
			String active);
	
	/**
	 * 查询对账单信息
	 * @author ddw
	 * @date 2014-08-19
	 */
	List<StatementOfAccountEntity> queryStatementBIllEntity(StatementOfAccountMakeQueryDto statementQueryDto);
	
	/**
	 * 查询对账单本期应收总数
	 * @author cjy
	 * @date 2016-12-07
	 */
	public int queryYSCountByParms(StatementOfAccountMakeQueryDto statementQueryDto);
}

package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountDetailCountDto;


/**
 * 对账单明细DAO公共接口
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-25 上午11:42:38
 */
public interface IStatementOfAccountDEntityDao {

	/**
	 * 保存对账单明细
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-25 上午11:43:22
	 * @param entity 对账单明细
	 * @return
	 */
	int add(StatementOfAccountDEntity entity);

	/**
	 * 修改对账单明细的删除状态
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午12:47:49
	 * @param entity 对账单明细
	 * @return
	 */
	int updateStatementDetailByDeleteFlag(StatementOfAccountDEntity entity);

	/**
	 * 根据金额信息修改对账单明细
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 上午11:27:30
	 * @param entity 对账单明细
	 * @return
	 */
	int updateStatementDetailByAmount(StatementOfAccountDEntity entity);

	/**
	 * 根据对账单号查询对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 上午11:58:43
	 * @param statementBillNo 对账单号
	 * @return
	 */
	List<StatementOfAccountDEntity> queryByStatementBillNo(
			String statementBillNo);

	/**
	 * 根据对账单明细来源单号查询对账单明细
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 上午8:48:04
	 * @param list     来源单号集合
	 * @param isDelete 是否删除
	 * @return
	 */
	List<StatementOfAccountDEntity> queryByResourceNos(List<String> list,
			String isDelete);

	/**
	 * 根据对账单号查询对账单明细信息(分页查询)
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 上午11:58:43
	 * @param statementBillNo 对账单号
	 * @param offset          偏移量
	 * @param limit           限制记录数
	 * @return
	 */
	List<StatementOfAccountDEntity> queryByStatementBillNo(
			String statementBillNo, int offset, int limit);

	/**
	 * 根据对账单号查询对账单明细信息(总条数)
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 上午11:58:43
	 * @param statementBillNo 对账单号
	 * @return
	 */
	StatementOfAccountDetailCountDto queryCountByStatementBillNo(
			String statementBillNo);

	/**
	 * 根据对账单号、来源单号、及删除标记查询对账单明细单据
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 下午3:43:27
	 * @param resourceNo
	 *            来源单号
	 * @param statementNo
	 *            对账单号
	 * @param isDelete
	 *            是否删除
	 * @return
	 */
	StatementOfAccountDEntity queryByResourceAndStatementNo(String resourceNo,
			String statementNo, String isDelete);

	/**
	 * 根据运单号集合及删除标记查询对账单明细单据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 下午3:43:27
	 * @param waybillNos
	 *            运单号集合
	 * @param deleteFlag
	 *            删除标记
	 * @return
	 */
	List<StatementOfAccountDEntity> queryByWaybillNos(List<String> waybillNos,
			String deleteFlag);
	
	/**
	 * 根据原始来源单号集合及删除标记查询对账单明细单据
	 * @author foss-wangxuemin
	 * @date Apr 19, 2013 11:19:15 AM
	 */
	List<StatementOfAccountDEntity> queryByOrigSourceNos(List<String> origSourceBillNos,
			String deleteFlag);
	/**
	 * 查询单号List中是否至少一个单存在于对账单中
	 * @author 105762-Yang Shushuo
	 * @date 2014-07-16
	 */
	public int queryIfAtLeastOneBillExistsInStatement(List<String> billNoList);
}

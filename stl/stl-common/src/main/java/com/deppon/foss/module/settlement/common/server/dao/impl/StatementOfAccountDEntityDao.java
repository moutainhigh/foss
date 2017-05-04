package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountDEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountDetailCountDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 对账单明细DAO公共接口实现类
 * 
 * @author 088933-foss-zhangjiheng
 * @date 2012-10-25 上午11:42:38
 */
public class StatementOfAccountDEntityDao extends iBatis3DaoImpl implements
		IStatementOfAccountDEntityDao {

	private static final String NAMESPACE = "foss.stl.StatementOfAccountDEntityDao.";

	/**
	 * 保存对账单明细
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-25 上午11:43:22
	 * @param entity
	 *            对账单明细
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountDEntityDao#add(com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity)
	 */
	@Override
	public int add(StatementOfAccountDEntity entity) {
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}

	/** 
	 * 修改对账单明细的删除状态
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 下午12:47:49
	 * @param entity 对账单明细
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountDEntityDao#updateStatementDetailByDeleteFlag(com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity)
	 */
	@Override
	public int updateStatementDetailByDeleteFlag(
			StatementOfAccountDEntity entity) {
		return this.getSqlSession().update(NAMESPACE + "updateByDeleteFlag",
				entity);
	}

	/** 
	 * 根据金额信息修改对账单明细
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 上午11:27:30
	 * @param entity 对账单明细
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountDEntityDao#updateStatementDetailByAmount(com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity)
	 */
	@Override
	public int updateStatementDetailByAmount(StatementOfAccountDEntity entity) {
		return this.getSqlSession()
				.update(NAMESPACE + "updateByAmount", entity);
	}

	/**
	 * 根据对账单号查询对账单明细信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 上午11:58:43
	 * @param statementBillNo
	 *            对账单号
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountDEntityDao#queryByStatementBillNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryByStatementBillNo(
			String statementBillNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statementBillNo", statementBillNo);
		map.put("isDelete", FossConstants.NO);
		return (List<StatementOfAccountDEntity>) this.getSqlSession()
				.selectList(NAMESPACE + "selectByStatementBillNo", map);
	}

	/** 
	 * 根据对账单明细来源单号查询对账单明细
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-10-26 上午8:48:04
	 * @param list     来源单号集合
	 * @param isDelete 是否删除
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountDEntityDao#queryByResourceNos(java.util.List, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryByResourceNos(
			List<String> list, String isDelete) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceBillNos", list);
		map.put("isDelete", isDelete);
		return this.getSqlSession().selectList(
				NAMESPACE + "selectBySourceBillNos", map);
	}

	/** 
	 * 根据对账单号查询对账单明细信息(分页查询)
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 上午11:58:43
	 * @param statementBillNo 对账单号
	 * @param offset          偏移量
	 * @param limit           限制记录数
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountDEntityDao#queryByStatementBillNo(java.lang.String, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryByStatementBillNo(
			String statementBillNo, int offset, int limit) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statementBillNo", statementBillNo);
		map.put("isDelete", FossConstants.NO);
		RowBounds rowBounds = new RowBounds(offset*limit, limit);
		return (List<StatementOfAccountDEntity>) this.getSqlSession()
				.selectList(NAMESPACE + "selectByStatementBillNo", map,
						rowBounds);
	}

	/** 
	 * 根据对账单号查询对账单明细信息(总条数)
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-5 上午11:58:43
	 * @param statementBillNo 对账单号
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountDEntityDao#queryCountByStatementBillNo(java.lang.String)
	 */
	@Override
	public StatementOfAccountDetailCountDto queryCountByStatementBillNo(
			String statementBillNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statementBillNo", statementBillNo);
		map.put("isDelete", FossConstants.NO);
		return (StatementOfAccountDetailCountDto) this.getSqlSession()
				.selectOne(NAMESPACE + "selectCountByStatementBillNo", map);
	}

	/** 
	 * 根据对账单号、来源单号、及删除标记查询对账单明细单据
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2012-11-7 下午3:43:27
	 * @param resourceNo
	 *            来源单号
	 * @param statementNo
	 *            对账单号
	 * @param isDelete
	 *            是否删除
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountDEntityDao#queryByResourceAndStatementNo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public StatementOfAccountDEntity queryByResourceAndStatementNo(
			String sourceBillNo, String statementBillNo, String isDelete) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sourceBillNo", sourceBillNo);
		map.put("statementBillNo", statementBillNo);
		map.put("isDelete", isDelete);
		return (StatementOfAccountDEntity) this.getSqlSession().selectOne(
				NAMESPACE + "selectBySourceAndStatementBillNo", map);
	}

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
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountDEntityDao#queryByWaybillNos(java.util.List, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryByWaybillNos(
			List<String> waybillNos, String deleteFlag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNos", waybillNos);
		map.put("isDelete", deleteFlag);
		return this.getSqlSession().selectList(
				NAMESPACE + "selectStatementByWaybillNos", map);
	}

	/**
	 * 
	 * 根据原始来源单号集合及删除标记查询对账单明细单据
	 * @author foss-wangxuemin
	 * @date Apr 19, 2013 11:20:57 AM
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.IStatementOfAccountDEntityDao#queryByOrigSourceNos(java.util.List, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountDEntity> queryByOrigSourceNos(List<String> origSourceBillNos,
			String deleteFlag){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("origSourceBillNos", origSourceBillNos);
		map.put("isDelete", deleteFlag);
		return this.getSqlSession().selectList(
				NAMESPACE + "selectStatementByOrigSourceBillNos", map);
	}
	/**
	 * 查询单号List中是否至少一个单存在于对账单中
	 * @author 105762-Yang Shushuo
	 * @date 2014-07-16
	 */
	@Override
	public int queryIfAtLeastOneBillExistsInStatement(List<String> billNoList) {
		return (Integer)this.getSqlSession().selectOne(NAMESPACE + "queryIfAtLeastOneBillExistsInStatement", billNoList);
	}
}

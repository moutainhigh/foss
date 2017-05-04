package com.deppon.foss.module.settlement.writeoff.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountQueryResultDto;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IStatementByFailingInvoiceDao;

public class StatementByFailingInvoiceDao extends iBatis3DaoImpl implements IStatementByFailingInvoiceDao {
	private static final String NAMESPACE = "foss.stl.StatementByFailingInvoiceDao.";
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<StatementOfAccountEntity> queryStatementByFailingInvoice(Map map, int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		List<StatementOfAccountEntity> list = getSqlSession().selectList(NAMESPACE + "selectByFailingInvoice", map,rb);
		return list;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public StatementOfAccountQueryResultDto countStatementByFailingInvoice(Map map) {
		StatementOfAccountQueryResultDto dto = (StatementOfAccountQueryResultDto)getSqlSession().selectOne(NAMESPACE + "countSelectByFailingInvoice", map);
		return dto;
	}

	@Override
	public int appliedStatement(List<StatementOfAccountEntity> list,CurrentInfo currentInfo) {
		return getSqlSession().update(NAMESPACE + "updateInvoiceStatusToApplied", list);
	}

	@Override
	public int notApplyStatement(List<StatementOfAccountEntity> list,CurrentInfo currentInfo) {
		return getSqlSession().update(NAMESPACE + "updateInvoiceStatusToNotApply", list);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> queryInstationMsgByIds(StatementOfAccountEntity entity) {
		Map map = new HashMap();
		map.put("statementBillNo", entity.getStatementBillNo());
		List<String> list = getSqlSession().selectList(NAMESPACE + "queryInstationMsgByIds", map);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StatementOfAccountEntity> queryStatementBystatementBillNo(List<StatementOfAccountEntity> list) {
		List<StatementOfAccountEntity> statementOfAccountList = getSqlSession().selectList(NAMESPACE + "queryStatementBystatementBillNo", list);
		return statementOfAccountList;
	}
}

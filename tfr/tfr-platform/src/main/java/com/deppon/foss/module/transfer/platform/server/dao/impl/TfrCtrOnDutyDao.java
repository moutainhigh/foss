package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.ITfrCtrOnDutyDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.TfrCtrOnDutyEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.TfrCtrOnDutyQcDto;

public class TfrCtrOnDutyDao extends iBatis3DaoImpl implements ITfrCtrOnDutyDao {

	private final String namespace = "com.deppon.foss.module.transfer.platform.api.server.dao.ITfrCtrOnDutyDao.";

	@Override
	public void insertTfrCtrOnDuty(TfrCtrOnDutyEntity parameter) {
		getSqlSession().insert(namespace + "insertTfrCtrOnDuty", parameter);
	}

	@Override
	public int updateTfrCtrOnDuty(TfrCtrOnDutyEntity parameter) {
		return getSqlSession().update(namespace + "updateTfrCtrOnDuty",
				parameter);
	}

	@Override
	public int deleteTfrCtrOnDuty(String id) {
		return getSqlSession().delete(namespace + "deleteTfrCtrOnDuty", id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TfrCtrOnDutyEntity> findTfrCtrOnDutys(
			TfrCtrOnDutyQcDto parameter, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(namespace + "findTfrCtrOnDutys",
				parameter, rowBounds);
	}

	@Override
	public Long cntTfrCtrOnDutys(TfrCtrOnDutyQcDto parameter) {
		return (Long) getSqlSession().selectOne(namespace + "cntTfrCtrOnDutys",
				parameter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TfrCtrOnDutyEntity> findInfos4Add(TfrCtrOnDutyQcDto parameter) {
		return getSqlSession().selectList(namespace + "findInfos4Add",
				parameter);
	}

}

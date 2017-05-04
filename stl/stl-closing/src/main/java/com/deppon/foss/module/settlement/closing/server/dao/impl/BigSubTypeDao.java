package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IBigSubTypeDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.BigSubTypeEntity;

public class BigSubTypeDao extends iBatis3DaoImpl implements IBigSubTypeDao{
	private static final String NAMESPACE = "foss.stv.VoucherDetailEntityMapper.";

	@Override
	public List<BigSubTypeEntity> queryBigType(BigSubTypeEntity dto) {
		
		return getSqlSession().selectList(NAMESPACE + "queryBigType",
				dto);
	}

	@Override
	public List<BigSubTypeEntity> querySubType(BigSubTypeEntity dto) {		
		return getSqlSession().selectList(NAMESPACE + "querySubType",
				dto);
	}

	@Override
	public List<BigSubTypeEntity> queryAllTypes(BigSubTypeEntity dto) {
		return getSqlSession().selectList(NAMESPACE + "queryAllTypes",
				dto);		
	}

}

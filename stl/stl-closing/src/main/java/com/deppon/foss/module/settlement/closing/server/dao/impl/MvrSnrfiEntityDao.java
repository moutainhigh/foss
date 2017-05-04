package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrSnrfiEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrNrfiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrNrfiDto;

public class MvrSnrfiEntityDao extends iBatis3DaoImpl implements IMvrSnrfiEntityDao{
	private static final String NAMESPACE = "foss.stv.MvrSnrfiEntityMapper.";
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrNrfiEntity> selectColumnsByConditions(MvrNrfiDto dto,
			int start, int limit) {
		RowBounds rb = new RowBounds(start, limit);
		return getSqlSession().selectList(
				NAMESPACE + "selectColumnsByConditions", dto, rb);
	}

	@Override
	public MvrNrfiEntity selectTotalByConditions(MvrNrfiDto dto) {
		return (MvrNrfiEntity) getSqlSession().selectOne(
				NAMESPACE + "selectTotalByConditions", dto);
	}

}

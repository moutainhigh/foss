package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrOrcciDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrOrccIEntity;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrPliEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrOrcciQueryDto;

public class MvrOrcciDao extends iBatis3DaoImpl implements IMvrOrcciDao {
	
	private static final String NAMESPACE = "foss.stv.OrcciEntityMapper.";

	
	@Override
	public List<MvrOrccIEntity> selectMvrOrcciByConditions(
			MvrOrcciQueryDto mvrOrccDto, int start, int limit) {
		RowBounds rowbounds = new RowBounds(start, limit);		
		return getSqlSession().selectList(NAMESPACE+"selectByConditions", mvrOrccDto,rowbounds);

	}

	@Override
	public List<MvrOrccIEntity> selectMvrOrcciByConditions(
			MvrOrcciQueryDto mvrOrccDto) {
		return getSqlSession().selectList(NAMESPACE+"selectByConditions", mvrOrccDto);
	}

	@Override
	public Long selectMvrOrcciByConditionsCount(MvrOrcciQueryDto mvrOrccDto) {
		return (Long) getSqlSession().selectOne(NAMESPACE+"selectByConditionsToltal", mvrOrccDto);
	}

	@Override
	public MvrPliEntity selectMvrOrcciByConditionsSum(MvrOrcciQueryDto mvrOrccDto) {
		// TODO Auto-generated method stub
		return null;
	}

}

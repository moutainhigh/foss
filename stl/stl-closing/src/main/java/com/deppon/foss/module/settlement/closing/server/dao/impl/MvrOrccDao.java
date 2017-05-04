package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrOrccDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrOrccEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrOrccQueryDto;

public class MvrOrccDao extends iBatis3DaoImpl implements IMvrOrccDao {
	
	private static final String NAMESPACE = "foss.stv.OrccEntityMapper.";

	/**
	 * 根据条件查询出始发外请车报表数据
	 */
	@Override
	public List<MvrOrccEntity> selectMvrOrccByConditions(
			MvrOrccQueryDto mvrOrccDto, int start, int limit) {
        RowBounds rowBounds = new RowBounds(start, limit);
        List<MvrOrccEntity> list = (List<MvrOrccEntity>)getSqlSession().selectList(NAMESPACE+"selectByConditions", mvrOrccDto, rowBounds);
		return list;
	}

	@Override
	public List<MvrOrccEntity> selectMvrOrccByConditions(
			MvrOrccQueryDto mvrOrccDto) {
        List<MvrOrccEntity> list = (List<MvrOrccEntity>)getSqlSession().selectList(NAMESPACE+"selectByConditions", mvrOrccDto);
		return list;
	}

	@Override
	public Long selectMvrOrccByConditionsCount(MvrOrccQueryDto mvrOrccDto) {
		return (Long) getSqlSession().selectOne(NAMESPACE+"selectByConditionsToltal", mvrOrccDto);
	}

	@Override
	public MvrOrccEntity selectMvrOrccByConditionsSum(MvrOrccQueryDto mvrOrccDto) {
		return (MvrOrccEntity) getSqlSession().selectOne(NAMESPACE+"selectAmountSum",mvrOrccDto);
	}

}

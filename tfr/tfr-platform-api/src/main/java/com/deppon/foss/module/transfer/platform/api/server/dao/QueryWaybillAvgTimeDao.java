package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.shared.domain.WaybillAvgTimeEntity;
import com.deppon.foss.module.transfer.platform.api.shared.vo.DeliverGoodsAreaQueryVo;

public class QueryWaybillAvgTimeDao extends iBatis3DaoImpl implements IQueryWaybillAvgTimeDao  {

	
	private static final String NAME_SPACE = "foss.platform.waybillAvgTime.";
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillAvgTimeEntity> queryWaybillAvgTimeEntityList(
			DeliverGoodsAreaQueryVo deliverGoodsAreaQueryVo, int start,
			int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return this.getSqlSession().selectList(NAME_SPACE+"queryWaybillAvgTimeEntityList",deliverGoodsAreaQueryVo,rowBounds);
	}

	@Override
	public long queryWaybillAvgTimeEntityListCount(
			DeliverGoodsAreaQueryVo deliverGoodsAreaQueryVo) {
		
		return (Long) this.getSqlSession().selectOne(NAME_SPACE+"queryWaybillAvgTimeEntityListCount",deliverGoodsAreaQueryVo);
	}
	
}



package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrLwoEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrLwoEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLwoDto;

public class MvrLwoEntityDao extends iBatis3DaoImpl implements IMvrLwoEntityDao {

	/** 命名空间. */
	private static final String NAMESPACE = "foss.stv.MvrLwoEntityMapper.";// 命名预付单空间

	/**
	 * 
	 * 查询报表列表
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午8:57:23
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrLwoEntity> queryMvrLwo(MvrLwoDto dto, int offset, int limit) {
		RowBounds rb = new RowBounds(offset, limit);
		return this.getSqlSession().selectList(NAMESPACE+"selectByConditions",dto,rb);
	}

	/**
	 * 
	 * 查询报表汇总
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午9:54:51
	 */
	@Override
	public MvrLwoDto queryMvrLwoTotal(MvrLwoDto dto) {
		return (MvrLwoDto) this.getSqlSession().selectOne(NAMESPACE+"selectTotalCounts",dto);
	}

}

package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrLdiEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrLdiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLdiDto;

/**
 * 快递代理往来报表查询dto.
 *
 * @author 045738-foss-maojianqiang
 * @date 2013-3-7 下午2:33:34
 */
public class MvrLdiEntityDao extends iBatis3DaoImpl implements IMvrLdiEntityDao {

	/** 命名空间. */
	private static final String NAMESPACE = "foss.stv.MvrLdiEntityMapper.";// 命名预付单空间

	/**
	 * 查询快递代理往来报表.
	 *
	 * @param rb the rb
	 * @param dto the dto
	 * @param isPaging the is paging
	 * @return the list
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午2:33:34
	 * @see com.deppon.foss.module.settlement.closing.api.server.dao.IMvrLdiEntityDao#selectByConditions()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrLdiEntity> selectByConditions(RowBounds rb,MvrLdiDto dto) {
		return this.getSqlSession().selectList(NAMESPACE+"selectByConditions",dto,rb);
	}

	/**
	 * 查询始发到达往来报表总条数.
	 *
	 * @param dto the dto
	 * @return the mvr rfi entity
	 * @author 045738-foss-maojianqiang
	 * @date 2013-3-7 下午2:50:57
	 */
	public MvrLdiDto queryTotalCounts(MvrLdiDto dto) {
		return (MvrLdiDto) this.getSqlSession().selectOne(NAMESPACE+"selectTotalCounts",dto);
	}

}

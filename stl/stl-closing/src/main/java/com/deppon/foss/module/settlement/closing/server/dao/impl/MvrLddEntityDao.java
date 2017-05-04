package com.deppon.foss.module.settlement.closing.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrLddEntityDao;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrLddEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLddDto;

/**
 * 快递代理月报表DAO实现.
 *
 * @author 046644-foss-zengbinwen
 * @date 2013-3-7 上午10:00:01
 */
public class MvrLddEntityDao extends iBatis3DaoImpl implements IMvrLddEntityDao {

	/** 实例mybatis配置文件空间. */
	private static final String NAMESPACE = "foss.stv.MvrLddEntityMapper.";

	/**
	 * 查询快递代理月报表列表.
	 *
	 * @param dto the dto
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午9:55:37
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrLddQueryService#queryMvrLdd(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLddDto,
	 * int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MvrLddEntity> queryMvrLdd(MvrLddDto dto, int offset, int limit) {

		// 分页
		RowBounds rb = new RowBounds(offset, limit);

		// 返回结果
		return getSqlSession().selectList(NAMESPACE + "selectListByConditions",
				dto, rb);
	}

	/**
	 * 查询快递代理月报表汇总.
	 *
	 * @param dto the dto
	 * @return the mvr afr dto
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-7 上午9:55:37
	 * @see com.deppon.foss.module.settlement.closing.api.server.service.IMvrLddQueryService#queryMvrLddTotal(com.deppon.foss.module.settlement.closing.api.shared.dto.MvrLddDto)
	 */
	@Override
	public MvrLddDto queryMvrLddTotal(MvrLddDto dto) {

		// 返回结果
		return (MvrLddDto) getSqlSession().selectOne(
				NAMESPACE + "selectTotalByConditions", dto);
	}

}

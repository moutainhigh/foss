package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IComplementOptimizedDao;
import com.deppon.foss.module.transfer.load.api.shared.dto.ComplementQueryDto;

/**
 * @className: IComplementOptimizedDao
 * @author: 335284
 * @description: 补码界面和退回界面DAO
 * @since 2016年9月7日 13:58:16
 * @description 用于优化补码查询的效率，新建的服务套件
 */
public class ComplementOptimizedDao extends iBatis3DaoImpl implements IComplementOptimizedDao {

	private static final String NAMESPACE = "foss.load.express.complement.optimized.";

	private static final Logger LOGGER = LoggerFactory.getLogger(ComplementOptimizedDao.class);

	@Override
	public List<ComplementQueryDto> queryComplementList(ComplementQueryDto queryDto, int start, int limit) {
		LOGGER.info("查询补码信息列表");
		RowBounds rw = new RowBounds(start, limit);
		@SuppressWarnings("unchecked")
		List<ComplementQueryDto> selectList = getSqlSession().selectList(NAMESPACE + "queryComplementList", queryDto,
				rw);
		return selectList;
	}

	@Override
	public List<ComplementQueryDto> queryComplementByWaybillno(ComplementQueryDto queryDto) {
		LOGGER.info("查询补码信息");
		@SuppressWarnings("unchecked")
		List<ComplementQueryDto> selectList = getSqlSession().selectList(NAMESPACE + "queryComplementByWaybillno",
				queryDto);
		return selectList;
	}

	@Override
	public ComplementQueryDto queryComplementAddressInfo(ComplementQueryDto info) {
		LOGGER.info("查询补码address信息");
		ComplementQueryDto addressDto = (ComplementQueryDto) getSqlSession()
				.selectOne(NAMESPACE + "queryComplementAddressInfo", info);
		return addressDto;
	}

	@Override
	public Long queryComplementCount(ComplementQueryDto queryDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryComplementCount", queryDto);
	}

	@Override
	public Long queryComplementCountByWaybillno(ComplementQueryDto queryDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryComplementCountByWaybillno", queryDto);
	}

	@Override
	public List<ComplementQueryDto> queryComplementBackList(ComplementQueryDto queryDto, int start, int limit) {
		RowBounds rw = new RowBounds(start, limit);
		@SuppressWarnings("unchecked")
		List<ComplementQueryDto> selectList = getSqlSession().selectList(NAMESPACE + "queryComplementBackList",
				queryDto, rw);
		return selectList;
	}

	@Override
	public List<ComplementQueryDto> queryComplementBackByWaybillno(ComplementQueryDto queryDto) {
		@SuppressWarnings("unchecked")
		List<ComplementQueryDto> selectList = getSqlSession().selectList(NAMESPACE + "queryComplementBackByWaybillno",
				queryDto);
		return selectList;
	}
	
	@Override
	public Long queryComplementBackCount(ComplementQueryDto queryDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryComplementBackCount", queryDto);
	}
	
	@Override
	public Long queryComplementBackCountByWaybillno(ComplementQueryDto queryDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryComplementBackCountByWaybillno", queryDto);
	}

}

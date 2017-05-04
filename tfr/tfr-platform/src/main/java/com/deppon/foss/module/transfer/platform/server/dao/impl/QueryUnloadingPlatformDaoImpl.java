package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.IQueryUnloadingPlatformDao;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryBillInfoResultPlatformDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryUnloadingPlatformConditionDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryUnloadingPlatformResultDto;

public class QueryUnloadingPlatformDaoImpl extends iBatis3DaoImpl implements
		IQueryUnloadingPlatformDao {
	private static final String NAMESPACE = "foss.platform.unloadingprogress.";
	/** 
	 * 查询卸车进度信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-12 下午3:34:38
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IQueryUnloadingProgressDao#queryUnloadingProgressInfo(com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressConditionDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryUnloadingPlatformResultDto> queryUnloadingProgressInfo(
			QueryUnloadingPlatformConditionDto queryUnloadingProgressConditionDto,int limit,int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"queryUnloadingProgress", 
				queryUnloadingProgressConditionDto,
				rowBounds);
	}
	
	/** 
	 * 查询卸车进度信息
	 * @author 134019
	 * @date 2013年7月15日 19:10:34
	 * @see com.deppon.foss.module.transfer.unload.api.server.dao.IQueryUnloadingProgressDao#queryUnloadingProgressInfoCount(com.deppon.foss.module.transfer.unload.api.shared.dto.QueryUnloadingProgressConditionDto)
	 */
	@Override
	public Long queryUnloadingProgressInfoCount(
			QueryUnloadingPlatformConditionDto queryUnloadingProgressConditionDto) {
		return (Long) getSqlSession().selectOne(NAMESPACE+"queryUnloadingProgressCount", 
				queryUnloadingProgressConditionDto);
	}
	
	
	/**
	 * 
	 * 查询单据信息
	 * @param map{id:卸车任务id，cancel：取消状态}
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-13 下午4:52:42
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryBillInfoResultPlatformDto> queryBillInfo(Map<String, String> map) {
		
		return getSqlSession().selectList(NAMESPACE+"queryBillInfoResult", map);
	}

}

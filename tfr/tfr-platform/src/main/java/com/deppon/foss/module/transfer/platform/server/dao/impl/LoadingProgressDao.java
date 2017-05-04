package com.deppon.foss.module.transfer.platform.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.platform.api.server.dao.ILoadingProgressDao;
import com.deppon.foss.module.transfer.platform.api.shared.dto.DeparturePlanDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LoadingProgressConditionDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LoadingProgressResultDto;

/**
* @ClassName: QueryLoadingProgressDao 
* @Description: 月台项目-查询装车任务进度-dao类
* @author shiwei shiwei@outlook.com
* @date 2014年4月12日 下午4:16:21 
*
 */
public class LoadingProgressDao extends iBatis3DaoImpl implements
		ILoadingProgressDao {
	private static final String NAMESPACE = "foss.platform.loadingProgress.";
	
	/**
	 * 
	 * 查询装车进度dao
	 * @param queryLoadingProgressConditionDto 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-19 下午4:41:58
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LoadingProgressResultDto> queryLoadingProgressInfo(
			LoadingProgressConditionDto loadingProgressConditionDto,int limit,int start) {
		//RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(NAMESPACE+"queryLoadingProgress",	loadingProgressConditionDto);
	}
	/**
	 * 
	 *  根据任务id查询发车计划
	 * @param taskId 任务id
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-27 下午7:48:59
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IQueryLoadingProgressDao#queryDeparturePlan(java.lang.String)
	 */
	@Override
	public DeparturePlanDto queryDeparturePlan(String taskId) {
		return (DeparturePlanDto)getSqlSession().selectOne(NAMESPACE+"queryDeparturePlan", taskId);
	}	
	
	
}
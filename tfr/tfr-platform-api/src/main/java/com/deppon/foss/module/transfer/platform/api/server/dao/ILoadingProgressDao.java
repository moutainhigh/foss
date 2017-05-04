package com.deppon.foss.module.transfer.platform.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.dto.DeparturePlanDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LoadingProgressConditionDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LoadingProgressResultDto;

/**
* @ClassName: IQueryLoadingProgressDao 
* @Description: 月台项目-查询装车任务进度-dao接口
* @author shiwei shiwei@outlook.com
* @date 2014年4月12日 下午4:52:34 
*
 */
public interface ILoadingProgressDao {
	/**
	 * 
	 * 查询装车进度dao
	 * @param queryLoadingProgressConditionDto 查询条件
	 * @author shiwei
	 * @date 2014-04-12 下午4:55:58
	 */
	List<LoadingProgressResultDto> queryLoadingProgressInfo(
			LoadingProgressConditionDto loadingProgressConditionDto,int limit,int start);
	
	/**
	 * 
	 * 根据任务id查询发车计划
	 * @param taskId 任务id
	 * @author shiwei
	 * @date 2014-04-12 下午7:47:47
	 */
	DeparturePlanDto queryDeparturePlan(String taskId);
}
package com.deppon.foss.module.transfer.platform.api.server.dao;


import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryBillInfoResultPlatformDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryUnloadingPlatformConditionDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QueryUnloadingPlatformResultDto;


/**
* @description 查询卸车进度DAO
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年4月11日 下午3:08:12
*/
public interface IQueryUnloadingPlatformDao {
	
	/**
	 * 
	 * 查询卸车进度信息
	 * @param queryUnloadingProgressConditionDto 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-12 下午3:25:46
	 */
	List<QueryUnloadingPlatformResultDto> 
	queryUnloadingProgressInfo(QueryUnloadingPlatformConditionDto queryUnloadingProgressConditionDto,int limit,int start);
	
	/**
	 * 
	 * 查询卸车进度信息 count
	 * @param queryUnloadingProgressConditionDto 查询条件
	 * @author 134019-yuyongxiang
	 * @date 2013年7月15日 19:10:04
	 */
	Long queryUnloadingProgressInfoCount(
			QueryUnloadingPlatformConditionDto queryUnloadingProgressConditionDto);
	
	/**
	 * 
	 * 查询单据信息
	 * @param map{id:卸车任务id，cancel：取消状态}
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-13 下午4:52:42
	 */
	List<QueryBillInfoResultPlatformDto> queryBillInfo(Map<String,String> map);
	
}

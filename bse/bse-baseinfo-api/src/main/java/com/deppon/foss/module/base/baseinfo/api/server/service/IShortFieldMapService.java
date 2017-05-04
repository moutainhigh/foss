package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ShortFieldMapEntity;

public interface IShortFieldMapService extends IService  {

	/**
	 * <p> 通过所有条件进行分页查询</p> 
	 * @author 232607 
	 * @date 2015-4-1 下午2:37:05
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<ShortFieldMapEntity> queryShortFieldMapListByCondition(ShortFieldMapEntity entity, int start, int limit);
	
	/**
	 * <p> 通过所有条件进行分页查询的查询总数</p> 
	 * @author 232607 
	 * @date 2015-4-1 下午2:37:59
	 * @param entity
	 * @return
	 * @see
	 */
	long queryShortFieldMapListByConditionCount(ShortFieldMapEntity entity);
	
	/**
	 * <p> 批量作废（非物理删除，是将数据的状态改为不可用）</p> 
	 * @author 232607 
	 * @date 2015-4-1 下午2:38:11
	 * @param ids
	 * @return
	 * @see
	 */
	long deleteShortFieldMap(List<String> ids);
	
	/**
	 * <p> 新增映射关系</p> 
	 * @author 232607 
	 * @date 2015-4-1 下午2:38:30
	 * @param entity
	 * @return
	 * @see
	 */
	ShortFieldMapEntity addShortFieldMap(ShortFieldMapEntity entity); 
	
	/**
	 * <p> 修改映射关系</p> 
	 * @author 232607 
	 * @date 2015-4-1 下午2:38:45
	 * @param entity
	 * @return
	 * @see
	 */
	ShortFieldMapEntity updateShortFieldMap(ShortFieldMapEntity entity);


	
	
}

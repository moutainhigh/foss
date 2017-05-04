package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ShortFieldMapEntity;

public interface IShortFieldMapDao {

	/**
	 * <p>  通过所有条件进行分页查询</p> 
	 * @author 232607 
	 * @date 2015-4-1 下午2:40:20
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
	 * @date 2015-4-1 下午2:40:47
	 * @param entity
	 * @return
	 * @see
	 */
	long queryShortFieldMapListByConditionCount(ShortFieldMapEntity entity);
	
	/**
	 * <p> 批量删除映射关系</p> 
	 * @author 232607 
	 * @date 2015-4-1 下午2:41:02
	 * @param ids
	 * @return
	 * @see
	 */
	long deleteShortFieldMap(List<String> ids); 
	
	/**
	 * <p> 新增映射关系</p> 
	 * @author 232607 
	 * @date 2015-4-1 下午2:41:21
	 * @param entity
	 * @return
	 * @see
	 */
	ShortFieldMapEntity addShortFieldMap(ShortFieldMapEntity entity);


	
}

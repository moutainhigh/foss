package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSalesAgentMapEntity;

public interface IExpressSalesAgentMapDao {
	/**
	 * <p>通过所有条件进行分页查询</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午4:21:25
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<ExpressSalesAgentMapEntity> queryExpressSalesAgentMapListByCondition(
			ExpressSalesAgentMapEntity entity, int start, int limit);
	/**
	 * <p>通过所有条件进行分页查询的查询总数</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午4:52:45
	 * @param entity
	 * @return
	 * @see
	 */
	long queryExpressSalesAgentMapListByConditionCount(
			ExpressSalesAgentMapEntity entity);
	/**
	 * <p>批量删除映射关系</p> 
	 * @author 232607 
	 * @date 2015-5-22 上午10:06:02
	 * @param ids
	 * @return
	 * @see
	 */
	long deleteExpressSalesAgentMap(List<String> ids); 
	/**
	 * <p>新增映射关系</p> 
	 * @author 232607 
	 * @date 2015-5-22 上午10:06:27
	 * @param entity
	 * @return
	 * @see
	 */
	ExpressSalesAgentMapEntity addExpressSalesAgentMap(ExpressSalesAgentMapEntity entity);
	/**
	 * <p>用于查重</p> 
	 * @author 232607 
	 * @date 2015-5-22 下午4:12:14
	 * @param entity
	 * @return
	 * @see
	 */
	List<ExpressSalesAgentMapEntity> queryRepeat(
			ExpressSalesAgentMapEntity entity);
	/**
	 * <p>作为接口给中转调用，
	 *        查询参数为：虚拟营业部code集合，
	 *        返回映射关系实体集合</p> 
	 * @author 232607 
	 * @date 2015-5-29 上午10:33:27
	 * @param codes
	 * @return
	 * @see
	 */
	List<ExpressSalesAgentMapEntity> queryExpressSalesAgentMapBySalesCodes(
			List<String> codes);
	
}

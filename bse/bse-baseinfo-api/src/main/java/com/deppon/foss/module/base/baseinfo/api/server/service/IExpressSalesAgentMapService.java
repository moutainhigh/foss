package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSalesAgentMapEntity;

public interface IExpressSalesAgentMapService extends IService  {
	/**
	 * <p>通过所有条件进行分页查询</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午4:11:40
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
	 * @date 2015-5-21 下午4:52:13
	 * @param expressSalesAgentMapEntity
	 * @return
	 * @see
	 */
	long queryExpressSalesAgentMapListByConditionCount(
			ExpressSalesAgentMapEntity expressSalesAgentMapEntity);
	/**
	 * <p>批量作废（非物理删除，是将数据的状态改为不可用）</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午5:31:32
	 * @param ids
	 * @return
	 * @see
	 */
	long deleteExpressSalesAgentMap(List<String> ids);
	/**
	 * <p>新增映射关系</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午5:31:52
	 * @param entity
	 * @return
	 * @see
	 */
	ExpressSalesAgentMapEntity addExpressSalesAgentMap(
			ExpressSalesAgentMapEntity entity);
	/**
	 * <p>修改映射关系</p> 
	 * @author 232607 
	 * @date 2015-5-21 下午5:32:06
	 * @param entity
	 * @return
	 * @see
	 */
	ExpressSalesAgentMapEntity updateExpressSalesAgentMap(
			ExpressSalesAgentMapEntity entity);
	/**
	 * <p>查询重复的方法，只要存在相同的虚拟营业部就算重复，重复则抛出异常</p> 
	 * @author 232607 
	 * @date 2015-5-22 下午4:00:33
	 * @param entity
	 * @see
	 */
	void queryRepeat(ExpressSalesAgentMapEntity entity);
	/**
	 * <p>作为接口给中转调用，
	 *        查询参数为：虚拟营业部code集合，
	 *        返回参数为：映射关系map，其中key为虚拟营业部code，value为快递代理网点code</p> 
	 * @author 232607 
	 * @date 2015-5-29 上午8:37:19
	 * @param codes
	 * @return
	 * @see
	 */
	Map<String,String> queryExpressSalesAgentMapBySalesCodes(List<String> codes);

}

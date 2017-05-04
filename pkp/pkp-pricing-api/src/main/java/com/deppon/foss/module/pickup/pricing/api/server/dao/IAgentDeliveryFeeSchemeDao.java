package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.AgentDeliveryFeeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AgentDeliveryFeeSchemeEntity;

/**
 * @author 092020-lipengfei
 * 偏线代理送货费方案dao
 */
public interface IAgentDeliveryFeeSchemeDao {
	/**
	 * @author 092020-lipengfei
	 * @description 根据参数查询偏线代理送货费方案
	 * @param entity
	 * @return String
	 */
	List<AgentDeliveryFeeSchemeEntity> queryAgentDeliveryFeeSchemeByParams(AgentDeliveryFeeSchemeEntity entity,int limit, int start);
	/**
	 * @author 092020-lipengfei
	 * @description 根据ID查询偏线代理送货费方案
	 * @param schemeId
	 * @return AgentDeliveryFeeSchemeEntity
	 */
	AgentDeliveryFeeSchemeEntity queryAgentDeliveryFeeSchemeById(String schemeId);
	/**
	 * @author 092020-lipengfei
	 * @description 根据方案名称查询偏线代理送货费方案
	 * @param schemeName
	 * @return AgentDeliveryFeeSchemeEntity
	 */
	AgentDeliveryFeeSchemeEntity queryAgentDeliveryFeeSchemeByName(String schemeName);
	/**
	 * @author 092020-lipengfei
	 * @description 根据方案ID查询偏线代理送货费
	 * @param schemeId
	 * @return AgentDeliveryFeeEntity
	 */
	List<AgentDeliveryFeeEntity> queryAgentDeliveryFeeBySchemeId(String schemeId);
	/**
	 * @author 092020-lipengfei
	 * @description 查询偏线代理送货费方案总量
	 * @param entity
	 * @return Long
	 */
	Long queryRecordCount(AgentDeliveryFeeSchemeEntity entity);
	/**
	 * @author 092020-lipengfei
	 * @description 新增偏线代理送货费方案
	 * @param entity
	 * @return Integer
	 */
	int addAgentDeliveryFeeScheme(AgentDeliveryFeeSchemeEntity entity);
	/**
	 * @author 092020-lipengfei
	 * @description 新增偏线代理送货费
	 * @param entityList
	 * @return Integer
	 */
	int addAgentDeliveryFee(List<AgentDeliveryFeeEntity> entityList);
	/**
	 * @author 092020-lipengfei
	 * @description 根据ID删除偏线代理送货费方案
	 * @param idList
	 * @return Integer
	 */
	int deleteAgentDeliveryFeeSchemeById(List<String> idList);
	/**
	 * @author 092020-lipengfei
	 * @description 根据方案Id删除偏线代理送货费
	 * @param idList
	 * @return Integer
	 */
	int deleteAgentDeliveryFeeByschemeId(List<String> schemeIdList);
	/**
	 * @author 092020-lipengfei
	 * @description 更新偏线代理送货费方案
	 * @param entity
	 * @return Integer
	 */
	int updateAgentDeliveryFeeScheme(AgentDeliveryFeeSchemeEntity entity);
	/**
	 * @author 092020-lipengfei
	 * @description 根据目的站统计送货费方案
	 * @param agentDeptCode
	 * @return Integer
	 */
	int countSchemeByagentDeptCode(String agentDeptCode);
	/**
	 * @author 092020-lipengfei
	 * @description 根据目的站查询送货费方案
	 * @param agentDeptCode
	 * @return AgentDeliveryFeeSchemeEntity
	 */
	AgentDeliveryFeeSchemeEntity querySchemeByagentDeptCode(String agentDeptCode);
	
	/**
	 * 目的站检验是否重复
	 * @author 200945-wutao
	 * @param deptCode
	 * @return AgentDeliveryFeeSchemeEntity
	 */
	public AgentDeliveryFeeSchemeEntity queryAgentDeliveryFeeSchemeByDeptCode(String deptCode);
}

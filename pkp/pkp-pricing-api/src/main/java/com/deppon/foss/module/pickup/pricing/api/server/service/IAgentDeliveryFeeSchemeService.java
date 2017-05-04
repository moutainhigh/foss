package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AgentDeliveryFeeSchemeEntity;

/**
 * @author 092020-lipengfei
 * 偏线代理送货费方案service
 */
public interface IAgentDeliveryFeeSchemeService extends IService{
	/**
	 * @author 092020-lipengfei
	 * @description 根据参数查询偏线代理送货费方案
	 * @param entity
	 * @return List<AgentDeliveryFeeSchemeEntity>
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
	 * @description 根据ID偏线代理送货费方案
	 * @param idList
	 * @return Integer
	 */
	int deleteAgentDeliveryFeeSchemeById(List<String> idList);
	/**
	 * @author 092020-lipengfei
	 * @description 更新偏线代理送货费方案
	 * @param entity
	 * @return Integer
	 */
	int updateAgentDeliveryFeeScheme(AgentDeliveryFeeSchemeEntity entity);
	/**
	 * @author 092020-lipengfei
	 * @description 更新偏线代理送货费方案（产品类型，目的站，重量，体积）
	 * @param productType
	 * @param targetOrgCode
	 * @param weight
	 * @param volumn
	 * @return Integer
	 */
	AgentDeliveryFeeSchemeEntity queryAgentDeliveryCharge(String productType,String targetOrgCode,BigDecimal weight,BigDecimal volumn);
}

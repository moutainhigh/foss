package com.deppon.foss.module.pickup.pricing.dubbo.api.server.service;

import java.math.BigDecimal;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.AgentDeliveryFeeSchemeEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.exception.AgentDeliveryFeeSchemeException;

/**
 * @author 092020-lipengfei
 * 偏线代理送货费方案service
 */
public interface IAgentDeliveryFeeSchemeService extends IService{
	/**
	 * @author 092020-lipengfei
	 * @description 更新偏线代理送货费方案（产品类型，目的站，重量，体积）
	 * @param productType
	 * @param targetOrgCode
	 * @param weight
	 * @param volumn
	 * @return Integer
	 */
	AgentDeliveryFeeSchemeEntity queryAgentDeliveryCharge(String productType,String targetOrgCode,BigDecimal weight,BigDecimal volumn)
			throws AgentDeliveryFeeSchemeException;
}

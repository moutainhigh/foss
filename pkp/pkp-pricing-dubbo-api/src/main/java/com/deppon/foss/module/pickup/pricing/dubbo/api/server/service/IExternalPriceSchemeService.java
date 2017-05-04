package com.deppon.foss.module.pickup.pricing.dubbo.api.server.service;

//import java.io.InputStream;
import java.util.Date;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.ExternalPriceSchemeEntity;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.exception.ExternalPriceSchemeException;
/**
 * @author 092020-lipengfei
 *	偏线外发价格方案Service
 */
public interface IExternalPriceSchemeService extends IService {
	/**
	 * @author 092020-lipengfei
	 * @description 根据目的站，外发外场，运输类型，外发单生成时间 查询偏线外发价格方案
	 * @param targetOrgCode
	 * @param outOrgCode
	 * @param transportType
	 * @param externalBillCreateTime
	 * @return ExternalPriceSchemeEntity
	 */
	ExternalPriceSchemeEntity queryAgentOutPriceInfo(String targetOrgCode,String outOrgCode,String transportType,Date externalBillCreateTime)
		throws ExternalPriceSchemeException;
}

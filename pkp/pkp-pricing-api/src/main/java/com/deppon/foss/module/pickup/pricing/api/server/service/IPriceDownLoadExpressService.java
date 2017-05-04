/**
 * 
 */
package com.deppon.foss.module.pickup.pricing.api.server.service;

import com.deppon.foss.base.util.ClientUpdateDataPack;
import com.deppon.foss.base.util.DataBundle;

/**
 * @author ibm-foss-sxw
 *
 */
public interface IPriceDownLoadExpressService {

	/**
	 * @param clientInfo
	 * @return
	 */
	DataBundle downPriceRegionExpressServerData(ClientUpdateDataPack clientInfo);

	/**
	 * @param clientInfo
	 * @return
	 */
	DataBundle downPriceRegionExpressOrgDetailServerData(
			ClientUpdateDataPack clientInfo);

	/**
	 * @param clientInfo
	 * @return
	 */
	DataBundle downPricingValuationRegionValueAddServerData(
			ClientUpdateDataPack clientInfo);

	/**
	 * @param clientInfo
	 * @return
	 */
	DataBundle downPricePlanServerData(ClientUpdateDataPack clientInfo);
	DataBundle downPricingValuationPricingServerData(ClientUpdateDataPack clientInfo);
}

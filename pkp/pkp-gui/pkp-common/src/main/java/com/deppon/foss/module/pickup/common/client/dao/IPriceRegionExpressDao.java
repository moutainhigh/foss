/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.dao;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;

/**
 * @author ibm-foss-sxw
 *
 */
public interface IPriceRegionExpressDao {

	/**
	 * @param priceRegionExpress
	 */
	boolean addPriceRegionExpress(PriceRegionExpressEntity priceRegionExpress);

	/**
	 * @param priceRegionExpress
	 */
	void updatePriceRegionExpress(PriceRegionExpressEntity priceRegionExpress);

}

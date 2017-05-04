/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;

/**
 * @author ibm-foss-sxw
 *
 */
public interface IPriceRegionExpressService {

	 void addPriceRegionExpress(PriceRegionExpressEntity priceRegionExpress) ;

	/**
	 * 
	 * 功能：更新条记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	 void updatePriceRegion(PriceRegionExpressEntity priceRegionExpress) ;

	/**
	 * 
	 * 功能：新增或更新记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	 void saveOrUpdate(PriceRegionExpressEntity priceRegionExpress);
}

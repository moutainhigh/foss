/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.dao;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnExpressEntity;

/**
 * @author ibm-foss-sxw
 *
 */
public interface IPriceRegioExpressOrgDao {

	/**
	 * @param priceRegioExpressOrg
	 */
	boolean addPriceRegioExpressOrg(
			PriceRegioOrgnExpressEntity priceRegioExpressOrg);

	/**
	 * @param priceRegioExpressOrg
	 */
	void updatePriceRegioExpressOrg(
			PriceRegioOrgnExpressEntity priceRegioExpressOrg);

}

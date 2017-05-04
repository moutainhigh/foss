/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnExpressEntity;

/**
 * @author ibm-foss-sxw
 *
 */
public interface IPriceRegioExpressOrgService {
	 void addPriceRegioExpressOrg(PriceRegioOrgnExpressEntity priceRegioExpressOrg);
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	 void updatePriceRegioExpressOrg(PriceRegioOrgnExpressEntity priceRegioExpressOrg);
	
	/**
	 * 
	 * 功能：新增或更新记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */ void saveOrUpdate(PriceRegioOrgnExpressEntity priceRegioExpressOrg) ;

}

/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.module.pickup.common.client.dao.IPriceRegionExpressDao;
import com.deppon.foss.module.pickup.common.client.service.IPriceRegionExpressService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.google.inject.Inject;

/**
 * @author ibm-foss-sxw
 *
 */
public class PriceRegionExpressService  implements IPriceRegionExpressService {

	@Inject
	private IPriceRegionExpressDao priceRegionExpressDao;

	/**
	 * 
	 * 功能：插条记录
	 * 
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void addPriceRegionExpress(PriceRegionExpressEntity priceRegionExpress) {
		priceRegionExpressDao.addPriceRegionExpress(priceRegionExpress);

	}

	/**
	 * 
	 * 功能：更新条记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void updatePriceRegion(PriceRegionExpressEntity priceRegionExpress) {
		priceRegionExpressDao.updatePriceRegionExpress(priceRegionExpress);

	}

	/**
	 * 
	 * 功能：新增或更新记录
	 * 
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void saveOrUpdate(PriceRegionExpressEntity priceRegionExpress) {
		if(!priceRegionExpressDao.addPriceRegionExpress(priceRegionExpress)){
			priceRegionExpressDao.updatePriceRegionExpress(priceRegionExpress);
		}

	}

}

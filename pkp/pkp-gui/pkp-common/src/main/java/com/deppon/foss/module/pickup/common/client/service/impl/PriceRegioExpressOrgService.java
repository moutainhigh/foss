/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.module.pickup.common.client.dao.IPriceRegioExpressOrgDao;
import com.deppon.foss.module.pickup.common.client.service.IPriceRegioExpressOrgService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnExpressEntity;
import com.google.inject.Inject;

/**
 * @author ibm-foss-sxw
 *
 */
public class PriceRegioExpressOrgService  implements IPriceRegioExpressOrgService {
	
	@Inject
	IPriceRegioExpressOrgDao priceRegioExpressOrgDao;
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void addPriceRegioExpressOrg(PriceRegioOrgnExpressEntity priceRegioExpressOrg) {
		priceRegioExpressOrgDao.addPriceRegioExpressOrg(priceRegioExpressOrg);

	}
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void updatePriceRegioExpressOrg(PriceRegioOrgnExpressEntity priceRegioExpressOrg) {
		priceRegioExpressOrgDao.updatePriceRegioExpressOrg(priceRegioExpressOrg);

	}
	
	/**
	 * 
	 * 功能：新增或更新记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void saveOrUpdate(PriceRegioOrgnExpressEntity priceRegioExpressOrg) {
		if(!priceRegioExpressOrgDao.addPriceRegioExpressOrg(priceRegioExpressOrg)){
			priceRegioExpressOrgDao.updatePriceRegioExpressOrg(priceRegioExpressOrg);
		}

	}

}
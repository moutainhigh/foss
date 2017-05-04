/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;
import com.deppon.foss.module.pickup.common.client.dao.IBillingGroupTransFerDao;
import com.deppon.foss.module.pickup.common.client.service.IBillingGroupTransFerService;
import com.google.inject.Inject;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class BillingGroupTransFerService  implements IBillingGroupTransFerService {
	
	@Inject
	IBillingGroupTransFerDao  billingGroupTransFerDao;
	
	/**
	 * @param salesBillingGroupDao the salesBillingGroupDao to set
	 */
	public void setBillingGroupTransFerDao(IBillingGroupTransFerDao billingGroupTransFer) {
		this.billingGroupTransFerDao = billingGroupTransFer;
	}
	
	/**
     * 插条记录
     */
	public boolean addBillingGroupTransFer(BillingGroupTransFerEntity e) {
		return billingGroupTransFerDao.addBillingGroupTransFer(e);
		
	}
	/**
	 * 更新条记录
	 */
	public void updateBillingGroupTransFer(
			BillingGroupTransFerEntity e) {
		billingGroupTransFerDao.updateBillingGroupTransFer(e);
		
	}
	/**
	 * 新增或更新记录
	 */
	public void saveOrUpdate(BillingGroupTransFerEntity e) {
		if(!billingGroupTransFerDao.addBillingGroupTransFer(e)){
			billingGroupTransFerDao.updateBillingGroupTransFer(e);
		}
	}

	/**
	 * @param delete
	 */
	public void delete(BillingGroupTransFerEntity e) {
		billingGroupTransFerDao.delete(e);
	}
	
}


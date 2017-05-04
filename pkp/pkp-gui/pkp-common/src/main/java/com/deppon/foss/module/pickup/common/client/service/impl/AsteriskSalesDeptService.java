/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AsteriskSalesDeptEntity;
import com.deppon.foss.module.pickup.common.client.dao.IAsteriskSalesDeptDao;
import com.deppon.foss.module.pickup.common.client.service.IAsteriskSalesDeptService;
import com.google.inject.Inject;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class AsteriskSalesDeptService implements IAsteriskSalesDeptService {
	
	@Inject
	IAsteriskSalesDeptDao  asteriskSalesDeptDao;
	
	/**
	 * @param salesBillingGroupDao the salesBillingGroupDao to set
	 */
	public void setAsteriskSalesDeptDao(IAsteriskSalesDeptDao asteriskSalesDept) {
		this.asteriskSalesDeptDao = asteriskSalesDept;
	}
	
	/**
     * 插条记录
     */
	public boolean addAsteriskSalesDept(AsteriskSalesDeptEntity asteriskSalesDept) {
		return asteriskSalesDeptDao.addAsteriskSalesDept(asteriskSalesDept);
		
	}
	/**
	 * 更新条记录
	 */
	public void updateAsteriskSalesDept(
			AsteriskSalesDeptEntity asteriskSalesDept) {
		asteriskSalesDeptDao.updateAsteriskSalesDept(asteriskSalesDept);
		
	}
	/**
	 * 新增或更新记录
	 */
	public void saveOrUpdate(AsteriskSalesDeptEntity asteriskSalesDept) {
		if(!asteriskSalesDeptDao.addAsteriskSalesDept(asteriskSalesDept)){
			asteriskSalesDeptDao.updateAsteriskSalesDept(asteriskSalesDept);
		}
	}

	/**
	 * @param delete
	 */
	public void delete(AsteriskSalesDeptEntity asteriskSalesDept) {
		asteriskSalesDeptDao.delete(asteriskSalesDept);
	}
	
}

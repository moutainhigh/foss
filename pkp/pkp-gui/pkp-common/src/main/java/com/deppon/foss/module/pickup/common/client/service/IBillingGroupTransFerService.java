/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;

/**
 * @author 026123-foss-lifengteng
 *
 */
public interface IBillingGroupTransFerService {

	/**
     * 插条记录
     */
	boolean addBillingGroupTransFer(BillingGroupTransFerEntity e);
	/**
	 * 更新条记录
	 */
	void updateBillingGroupTransFer(BillingGroupTransFerEntity e);
	/**
	 * 新增或更新记录
	 */
	void saveOrUpdate(BillingGroupTransFerEntity e);
	/**
	 * 删除
	 * @param delete
	 */
	void delete(BillingGroupTransFerEntity e);
}

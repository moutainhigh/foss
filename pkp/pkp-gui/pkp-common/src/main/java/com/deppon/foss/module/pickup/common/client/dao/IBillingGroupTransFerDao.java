/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillingGroupTransFerEntity;

/**
 * @author 026123-foss-lifengteng
 *
 */
public interface IBillingGroupTransFerDao {
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	boolean addBillingGroupTransFer(BillingGroupTransFerEntity e);
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateBillingGroupTransFer(BillingGroupTransFerEntity e);
	
	/**
	 * 删除
	 */
	void delete(BillingGroupTransFerEntity e);
}

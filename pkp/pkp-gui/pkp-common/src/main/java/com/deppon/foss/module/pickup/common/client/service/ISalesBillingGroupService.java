/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;

/**
 * @author 026123-foss-lifengteng
 *
 */
public interface ISalesBillingGroupService {
	/**
     * 插条记录
     */
	boolean addSalesBillingGroup(SalesBillingGroupEntity salesBillingGroup);
	/**
	 * 更新条记录
	 */
	void updateSalesBillingGroup(SalesBillingGroupEntity salesBillingGroup);
	/**
	 * 新增或更新记录
	 */
	void saveOrUpdate(SalesBillingGroupEntity salesBillingGroup);
	/**
	 * @param delete
	 */
	void delete(SalesBillingGroupEntity salesBillingGroup) ;
	
	/**
	 * 根据集中开单部门编码查询旗下所有营业部
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 下午6:33:35
	 */
	List<SalesBillingGroupEntity> querySalesListByBillingGroupCode(String code);
	
	/**
	 * 根据营业部编码查询所属集中开单组
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 下午6:33:35
	 */
	List<SalesBillingGroupEntity> queryBillingGroupListBySalesCode(String code);
	
	
}

/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;

/**
 * 集中开单部门
 * @author 026123-foss-lifengteng
 *
 */
public interface ISalesBillingGroupDao {
	   /**
		 * 
		 * 功能：插条记录
		 * @param: orgInfo
		 * @return void
		 * @since:1.6
		 */
	    boolean addSalesBillingGroup(SalesBillingGroupEntity salesBillingGroup);

		/**
		 * 
		 * 功能：更新条记录
		 * @param:
		 * @return void
		 * @since:1.6
		 */
		void updateSalesBillingGroup(SalesBillingGroupEntity salesBillingGroup);

		/**
		 * @param netGroupEntity
		 */
		void delete(SalesBillingGroupEntity salesBillingGroup);

		/**
		 * 根据集中开单部门编码查询旗下所有营业部
		 * @author 026123-foss-lifengteng
		 * @date 2013-5-6 下午6:33:35
		 */
		List<SalesBillingGroupEntity> querySalesListByBillingGroupCode(List<String> code);

		/**
		 * 根据营业部编码查询所属集中开单组
		 * @author 026123-foss-lifengteng
		 * @date 2013-5-6 下午6:33:35
		 */
		List<SalesBillingGroupEntity> queryBillingGroupListBySalesCode(String code);
}

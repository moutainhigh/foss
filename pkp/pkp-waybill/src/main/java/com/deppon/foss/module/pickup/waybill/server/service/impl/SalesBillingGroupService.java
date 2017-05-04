/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ISalesBillingGroupDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.ISalesBillingGroupService;
/**
 * @author 026123-foss-lifengteng
 *
 */
public class SalesBillingGroupService  implements ISalesBillingGroupService {
	ISalesBillingGroupDao  salesBillGroupDao;
	
	public void setSalesBillGroupDao(ISalesBillingGroupDao salesBillGroupDao) {
		this.salesBillGroupDao = salesBillGroupDao;
	}
	
	/**
     * 插条记录
     */
	@Override
	public boolean addSalesBillingGroup(SalesBillingGroupEntity salesBillingGroup) {
		return salesBillGroupDao.addSalesBillingGroup(salesBillingGroup);
		
	}
	/**
	 * 更新条记录
	 */
	@Override
	public void updateSalesBillingGroup(
			SalesBillingGroupEntity salesBillingGroup) {
		salesBillGroupDao.updateSalesBillingGroup(salesBillingGroup);
	}
	/**
	 * 新增或更新记录
	 */
	@Override
	public void saveOrUpdate(SalesBillingGroupEntity salesBillingGroup) {
		
		if(!salesBillGroupDao.addSalesBillingGroup(salesBillingGroup)){
			salesBillGroupDao.updateSalesBillingGroup(salesBillingGroup);
		}
		
	}

	/**
	 * @param delete
	 */
	public void delete(SalesBillingGroupEntity salesBillingGroup) {
		salesBillGroupDao.delete(salesBillingGroup);
	}
	
	/**
	 * 根据集中开单部门编码查询旗下所有营业部
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 下午6:33:35
	 */
	@Override
	public List<SalesBillingGroupEntity> querySalesListByBillingGroupCode(String code){
		List<String> lists = new ArrayList<String>();
	   lists.add(code);
	   return salesBillGroupDao.querySalesListByBillingGroupCode(lists);
	}
	
	/**
	 * 根据营业部编码查询所属集中开单组
	 * @author 026123-foss-lifengteng
	 * @date 2013-5-6 下午6:33:35
	 */
	@Override
	public List<SalesBillingGroupEntity> queryBillingGroupListBySalesCode(String code){
		return salesBillGroupDao.queryBillingGroupListBySalesCode(code);
	}

}
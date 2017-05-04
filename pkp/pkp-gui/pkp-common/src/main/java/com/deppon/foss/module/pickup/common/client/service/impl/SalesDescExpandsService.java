/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity;
import com.deppon.foss.module.pickup.common.client.dao.ISalesDescExpandsDao;
import com.deppon.foss.module.pickup.common.client.service.ISalesDescExpandsService;
import com.google.inject.Inject;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class SalesDescExpandsService  implements ISalesDescExpandsService {
	
	@Inject
	ISalesDescExpandsDao  salesDescExpandsDao;
	
	/**
	 * @param salesDescExpandsDao the salesDescExpandsDao to set
	 */
	public void setSalesDescExpandsDao(ISalesDescExpandsDao salesDescExpandsDao) {
		this.salesDescExpandsDao = salesDescExpandsDao;
	}

	/**
     * 插条记录
     */
	public boolean addSalesDescExpands(SalesDescExpandEntity e) {
		return salesDescExpandsDao.addSalesDescExpandEntity(e);
	}

	/**
	 * 更新条记录
	 */
	public void updateSalesDescExpands(SalesDescExpandEntity e) {
		salesDescExpandsDao.updateSalesDescExpandEntity(e);
		
	}

	/**
	 * 新增或更新记录
	 */
	public void saveOrUpdate(SalesDescExpandEntity e) {
		if(!salesDescExpandsDao.addSalesDescExpandEntity(e)){
			salesDescExpandsDao.updateSalesDescExpandEntity(e);
		}
	}

	/**
	 * 删除
	 * @param delete
	 */
	public void delete(SalesDescExpandEntity e) {
		salesDescExpandsDao.delete(e);
		
	}

	/**
	 * 
	 * 查询网点自提区域信息
	 * @author 025000-FOSS-helong
	 * @date 2013-6-26
	 */
	@Override
	public List<String> queryByCodeAndPickup(String code) {
		return salesDescExpandsDao.queryByCodeAndPickup(code);
	}

	/**
	 * 
	 * 查询网点自提区域信息
	 * @author 025000-FOSS-helong
	 * @date 2013-6-26
	 */
	@Override
	public List<String> queryByCodeAndDelivery(String code) {
		return salesDescExpandsDao.queryByCodeAndDelivery(code);
	}
	
	
	
}


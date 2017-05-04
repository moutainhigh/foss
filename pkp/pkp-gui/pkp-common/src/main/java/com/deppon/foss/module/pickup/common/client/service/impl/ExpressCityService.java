/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressCityEntity;
import com.deppon.foss.module.pickup.common.client.dao.IExpressCityDao;
import com.deppon.foss.module.pickup.common.client.service.IExpressCityService;
import com.google.inject.Inject;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpressCityService implements IExpressCityService {
	
	@Inject
	IExpressCityDao  expressCityDao;
	
	

	public IExpressCityDao getExpressCityDao() {
		return expressCityDao;
	}

	public void setExpressCityDao(IExpressCityDao expressCityDao) {
		this.expressCityDao = expressCityDao;
	}

	/**
     * 插条记录
     */
	public boolean addExpressCity(ExpressCityEntity e) {
		return expressCityDao.addExpressCityEntity(e);
	}

	/**
	 * 更新条记录
	 */
	public void updateExpressCity(ExpressCityEntity e) {
		expressCityDao.updateExpressCityEntity(e);
		
	}

	/**
	 * 新增或更新记录
	 */
	public void saveOrUpdate(ExpressCityEntity e) {
		if(!expressCityDao.addExpressCityEntity(e)){
			expressCityDao.updateExpressCityEntity(e);
		}
	}

	/**
	 * 删除
	 * @param delete
	 */
	public void delete(ExpressCityEntity e) {
		expressCityDao.delete(e);
		
	}
}

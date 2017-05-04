package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ISyncCustomerCircleRelationDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncCustomerCircleRelationService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity;

/**
 * 
 * 客户圈信息service实现
 * @author 308861 
 * @date 2016-12-21 下午3:17:28
 * @since
 * @version
 */
public class SyncCustomerCircleRelationService implements ISyncCustomerCircleRelationService{
	/**
	 * 注入syncCustomerCircleRelationDao客户圈信息
	 */
	private ISyncCustomerCircleRelationDao  syncCustomerCircleRelationDao;
	//setter方法
	public void setSyncCustomerCircleRelationDao(
			ISyncCustomerCircleRelationDao syncCustomerCircleRelationDao) {
		this.syncCustomerCircleRelationDao = syncCustomerCircleRelationDao;
	}

	/**
	 * 
	 * 根据crmId查询客户圈客户信息  
	 * @author 308861 
	 * @date 2017-2-14 下午6:16:06
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncCustomerCircleRelationService#getCustomerCircleById(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity)
	 */
	@Override
	public CustomerCircleEntity getCustomerCircleById(CustomerCircleEntity entity) {
		return syncCustomerCircleRelationDao.getCustomerCircleById(entity.getCrmId());
	}
	
	/**
	 * 
	 * 作废 
	 * @author 308861 
	 * @date 2017-2-14 下午6:24:30
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncCustomerCircleRelationService#delCustomerCircle(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity)
	 */
	@Override
	public int delCustomerCircle(CustomerCircleEntity entity) {
		return syncCustomerCircleRelationDao.delCustomerCircle(entity);
	}

	/**
	 * 
	 * 新增 
	 * @author 308861 
	 * @date 2017-2-14 下午6:37:20
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncCustomerCircleRelationService#addCustomerCircle(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity)
	 */
	@Override
	public int addCustomerCircle(CustomerCircleEntity entity) {
		return syncCustomerCircleRelationDao.addCustomerCircle(entity);
	}
}

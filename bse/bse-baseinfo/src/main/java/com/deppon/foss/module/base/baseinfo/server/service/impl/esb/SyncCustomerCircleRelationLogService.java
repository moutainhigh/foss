package com.deppon.foss.module.base.baseinfo.server.service.impl.esb;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ISyncCustomerCircleRelationLogDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncCustomerCircleRelationLogService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleLogEntity;
/**
 * 
 * 客户圈信息service实现
 * @author 308861 
 * @date 2016-12-21 下午3:17:28
 * @since
 * @version
 */
public class SyncCustomerCircleRelationLogService implements ISyncCustomerCircleRelationLogService{
	/**
	 * 注入customerCircleRelationLogDao客户圈信息日志
	 */
	private ISyncCustomerCircleRelationLogDao customerCircleRelationLogDao;
	//setter方法
	public void setCustomerCircleRelationLogDao(
			ISyncCustomerCircleRelationLogDao customerCircleRelationLogDao) {
		this.customerCircleRelationLogDao = customerCircleRelationLogDao;
	}
	/**
	 * 
	 * 新增客户圈客户日志信息 
	 * @author 308861 
	 * @date 2017-2-14 下午7:21:33
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncCustomerCircleRelationLogService#addCustomerCircleLog(com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleLogEntity)
	 */
	@Override
	public int addCustomerCircleLog(CustomerCircleLogEntity entity) {
		return customerCircleRelationLogDao.addCustomerCircleLog(entity);
	}
	
	
	
	
}

package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleLogEntity;

/**
 * 
 *  CRM-FOSS客户圈信息日志dao接口
 * @author 308861 
 * @date 2016-12-29 上午11:35:19
 * @since
 * @version
 */
public interface ISyncCustomerCircleRelationLogDao {
	
	/**
	 * 
	 * 插入   
	 * @author 308861 
	 * @date 2016-12-29 上午11:35:11
	 * @param entity
	 * @return
	 * @see
	 */
	int addCustomerCircleLog(CustomerCircleLogEntity entity);
}

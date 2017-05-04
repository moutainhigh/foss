package com.deppon.foss.module.base.baseinfo.api.server.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity;

/**
 * 
 * 同步客户圈信息dao接口
 * @author 308861 
 * @date 2016-12-21 下午3:29:36
 * @since
 * @version
 */
public interface ISyncCustomerCircleRelationDao {
	
	/**
	 * 
	 * 插入  
	 * @author 308861 
	 * @date 2016-12-21 下午4:11:32
	 * @param entity
	 * @return
	 * @see
	 */
	int addCustomerCircle(CustomerCircleEntity entity);
	
	/**
	 * 
	 * 作废  
	 * @author 308861 
	 * @date 2016-12-26 下午4:33:49
	 * @param entity
	 * @return
	 * @see
	 */
	int delCustomerCircle(CustomerCircleEntity entity);
	
	/**
	 * 
	 * 根据 crmId 精确查询有效的客户圈信息数据  
	 * @author 308861 
	 * @date 2016-12-21 下午4:13:16
	 * @param crmId
	 * @return
	 * @see
	 */
	CustomerCircleEntity getCustomerCircleById(String crmId);
}

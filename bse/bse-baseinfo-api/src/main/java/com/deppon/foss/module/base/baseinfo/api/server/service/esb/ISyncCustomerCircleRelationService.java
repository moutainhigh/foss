package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleEntity;
/**
 * 
 * 客户圈信息service接口
 * @author 308861 
 * @date 2016-12-21 下午2:01:17
 * @since
 * @version
 */
public interface ISyncCustomerCircleRelationService extends IService{
	
	/**
	 * 
	 * 根据crmId查询客户圈客户信息  
	 * @author 308861 
	 * @date 2017-2-14 下午6:15:51
	 * @param entity
	 * @return
	 * @see
	 */
	CustomerCircleEntity getCustomerCircleById(CustomerCircleEntity entity);
	/**
	 * 
	 * 作废 
	 * @author 308861 
	 * @date 2017-2-14 下午6:24:09
	 * @param entity
	 * @return
	 * @see
	 */
	int delCustomerCircle(CustomerCircleEntity entity);
	
	/**
	 * 
	 * 新增 
	 * @author 308861 
	 * @date 2017-2-14 下午6:36:17
	 * @param entity
	 * @return
	 * @see
	 */
	int addCustomerCircle(CustomerCircleEntity entity);
	
}

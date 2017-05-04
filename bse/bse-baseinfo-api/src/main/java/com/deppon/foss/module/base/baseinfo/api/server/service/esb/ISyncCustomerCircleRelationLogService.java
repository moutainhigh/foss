package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerCircleLogEntity;
/**
 * 
 * 客户圈信息service接口
 * @author 308861 
 * @date 2017-2-14 下午7:18:14
 * @since
 * @version
 */
public interface ISyncCustomerCircleRelationLogService extends IService{
	/**
	 * 
	 * 新增客户圈客户日志信息 
	 * @author 308861 
	 * @date 2017-2-14 下午7:19:22
	 * @param entity
	 * @return
	 * @see
	 */
	int addCustomerCircleLog(CustomerCircleLogEntity entity);
}

package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;

/**
 * 仓管组提供对应开单营业部映射接口
 * 
 * @author wusuhua
 * @date   2015-06-1 2:31PM
 *
 */
public interface IStoreBillingMapService extends IService{

	/**
	 * 通过仓管组 查询对应开单营业部
	 * 
	 * @author wusuhua
	 * @date   2015-06-1 2:31PM
	 *
	 */
	String queryBillingByStore(String storeCode);
}

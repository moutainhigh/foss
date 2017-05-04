package com.deppon.foss.module.transfer.partialline.api.server.service;

import com.deppon.foss.framework.service.IService;

/**
 * @author 269701--lln
 * @function 德邦家装送装签收信息，自动审核service interface
 */
public interface IAutoCheckDpjzSignInService extends IService{
	
	/**
	 * @author lln
	 * @date 2015-12-12下午2:35:36
	 * @function 处理24h未审核的德邦家装送装信息
	 * @param threadNo
	 * @param threadCount
	 */
	void doAutoCheckSignInInfo();
}

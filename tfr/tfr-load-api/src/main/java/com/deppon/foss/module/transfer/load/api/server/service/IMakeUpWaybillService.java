/**
 * 
 */
package com.deppon.foss.module.transfer.load.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity;

/**
 * @author niuly
 * @function 运单补录service interface
 */
public interface IMakeUpWaybillService extends IService{
	void addWaybillInfoForTfr(MakeUpWaybillEntity entity);

	/**
	 * @author niuly
	 * @date 2014-2-12下午2:35:36
	 * @function 处理运单补录或PDA更新重量体积数据
	 * @param threadNo
	 * @param threadCount
	 */
	void doMakeUpWaybillInfo(int threadNo, int threadCount);
}

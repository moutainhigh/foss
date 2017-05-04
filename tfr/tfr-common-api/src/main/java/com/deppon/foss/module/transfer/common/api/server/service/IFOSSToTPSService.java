package com.deppon.foss.module.transfer.common.api.server.service;

import com.deppon.foss.module.transfer.common.api.shared.domain.RequestParameterEntity;
import com.deppon.foss.module.transfer.common.api.shared.domain.ResponseParameterEntity;

/**
 * foss调用TPS接口
 * @author 200978  xiaobingcheng
 * 2014-12-29
 */
public interface IFOSSToTPSService {

	/**
	 * 同步约车信息
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-29
	 * @param requestParameter
	 * @return
	 * @throws Exception 
	 */
	ResponseParameterEntity updateOrderVehicleInfo(RequestParameterEntity requestParameter) throws Exception;
	
	/***
	 * foss 出发 到达，同步约车信息给tps
	 * @Author: 200978  xiaobingcheng
	 * 2014-12-30
	 * @param requestParameter
	 * @return
	 * @throws Exception
	 */
	ResponseParameterEntity updateDepartArriveinfo(RequestParameterEntity requestParameter) throws Exception;
	
	
}

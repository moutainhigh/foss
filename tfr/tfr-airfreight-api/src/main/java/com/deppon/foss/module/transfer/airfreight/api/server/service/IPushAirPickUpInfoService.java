package com.deppon.foss.module.transfer.airfreight.api.server.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillTempEntity;

public interface IPushAirPickUpInfoService  extends IService{
	
	/**
	 * @author lln
	 * @date 2016-04-05下午2:35:36
	 * @function 推送合大票清单数据至OPP系统
	 * @param threadNo
	 * @param threadCount
	 */
	void doPushAirPickUpInfo() throws Exception;
	/**
	 * 
	* @description 保存数据至临时表
	* @version 1.0
	* @author 269701-foss-lln
	* @update 2016年5月11日 上午9:50:35
	 */
	void addAirPickToTemp(AirWaybillTempEntity temEntity);
}

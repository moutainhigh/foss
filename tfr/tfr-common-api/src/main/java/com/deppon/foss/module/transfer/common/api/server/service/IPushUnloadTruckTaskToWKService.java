package com.deppon.foss.module.transfer.common.api.server.service;

import java.util.Map;

import com.deppon.foss.framework.service.IService;


/**
* @description FOSS将车辆卸车任务相关信息同步给WK
* @version 1.0
* @author 328768-foss-gaojianfu
* @update 2016年5月10日 上午10:00:00
 */
public interface IPushUnloadTruckTaskToWKService  extends IService  {
	
	/**
	* @description FOSS生成车辆卸车任务，并推送给WK(线程调用)
	* @param param
	* @return
	* @version 1.0
	* @author 328768-foss-gaojianfu
	* @update 2016年5月10日 上午10:01:29
	 */
	public int pushUnloadTruckTaskToWk(Map<String,Object> param);
	
}

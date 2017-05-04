package com.deppon.foss.module.transfer.common.api.server.service;

import java.util.Map;

import com.deppon.foss.framework.service.IService;


/**
* @description FOSS将任务车辆相关信息同步给WK(信息同步)
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年4月22日 下午2:09:55
*/
public interface IPushTruckTaskToWkService  extends IService  {
	
	/**
	* @description FOSS生成任务车辆，并推送给WK(线程调用)
	* @param param
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月30日 上午11:14:51
	*/
	public int pushTruckTaskToWk(Map<String,Object> param);
	
}

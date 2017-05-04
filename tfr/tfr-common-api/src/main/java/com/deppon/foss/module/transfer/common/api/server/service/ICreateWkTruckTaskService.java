package com.deppon.foss.module.transfer.common.api.server.service;

import java.util.Map;


/**
* @description 生成快递系统车辆任务
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年5月9日 上午8:47:01
*/
public interface ICreateWkTruckTaskService {
	
	/**
	* @description 生成快递系统车辆任务
	* @param param
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年5月9日 上午8:47:57
	*/
	public int createWkTruckTask(Map<String,Object> param);
}

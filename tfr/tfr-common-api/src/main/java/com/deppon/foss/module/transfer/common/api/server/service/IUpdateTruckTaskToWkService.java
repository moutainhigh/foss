package com.deppon.foss.module.transfer.common.api.server.service;

import java.util.Map;


/**
* @description FOSS将任务车辆相关信息同步给WK(更新相关)
* @version 1.0
* @author 283250-foss-liuyi
* @update 2016年4月30日 上午11:27:04
*/
public interface IUpdateTruckTaskToWkService {
	/**
	* @description FOSS将出发、到达确认，出发、到达取消，分配、提交卸车任务操作以后
	* 				被更新的车辆任务信息同步给快递系统
	* @param param
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年4月30日 上午11:14:00
	*/
	public int updateTruckTaskToWk(Map<String,Object> param);
}

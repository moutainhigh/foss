package com.deppon.foss.module.transfer.stock.api.server.service;

import java.util.Date;

import com.deppon.foss.framework.service.IService;


/**
* @description 出发丢货上报OA的Service;发货部门在库时长超过3天
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年12月22日 下午2:00:48
*/
public interface IErrorLoseStartingService extends IService {
	
	/**
	* @description 查找出发部门在库时间超过3的运单；上报oa丢货
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年12月22日 下午2:16:16
	*/
	public void execStockOvertime(Date doTime,int threadNo,int threadCount);
	
	
	/**
	* @description 出发丢货进行少货找到，并更新oa少货状态为已找到。
	* @param doTime
	* @param threadNo
	* @param threadCount
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年12月25日 上午11:31:54
	*/
	public void execFindErrorLoseStarting(Date doTime,int threadNo,int threadCount);
}

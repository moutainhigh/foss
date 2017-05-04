package com.deppon.foss.module.pickup.order.api.server.service;


/**
 * 零担电子面单激活线程Service
 * 
 * @author 323098-wangpengtao
 * @date 2016-08-01
 *
 */
public interface IAutoLTLEwaybillPendingService {

	/**
	 * process
	 * 
	 * @param waybillProcessEntityList 运单激活处理记录List
	 */
	void process(String jobId);
}

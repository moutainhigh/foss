package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.dto.PDAUnloadAsyncBillMsgDto;

public interface  IPDAUnloadInstockService {
	/**
	 * 卸车入库方法
	 * @author 105869
	 * @date 2015年3月20日 19:22:39
	 */
	void unloadInstock(int threadNo, int threadCount);
	/**
	 * 通过jobId查询卸车入库运单信息
	 * @author 105869
	 * @date 2015年3月20日 19:23:30
	 * @param jobId
	 * @return 
	 */
	List<PDAUnloadAsyncBillMsgDto> queryInStockMsg(String jobId);
	
	/**
	 * 入库卸车运单
	 * @author 105869
	 * @date 2015年3月20日 19:34:31
	 * @param inOutStockEntitys
	 * 
	 */
	int  unloadInstockList(List<PDAUnloadAsyncBillMsgDto> inOutStockEntitys);
	/**
	 * 加业务锁
	 */
	String upateUnloadMsgForJob();
	//重置数据
	void reSetUnloadInstockMsg();

}

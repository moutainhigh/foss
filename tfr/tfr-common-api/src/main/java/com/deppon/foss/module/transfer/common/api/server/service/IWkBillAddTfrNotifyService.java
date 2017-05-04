package com.deppon.foss.module.transfer.common.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.WkHandOverBillEntity;

/**
 * 这个接口主要提供的方法是根据明细ID查询出来所有的悟空交接单，
 * 然后根据查询出来的交接单List循环插入到临时通知表里
 * @author 332209
 */
public interface IWkBillAddTfrNotifyService {

	/**
	 * 根据车辆任务明细ID查询悟空交接单号
	 * @author 332209
	 * @param truckTaskDetailId
	 */
	List<WkHandOverBillEntity> queryWkHandOverBillByTruckTaskDetailId(String truckTaskDetailId);
}

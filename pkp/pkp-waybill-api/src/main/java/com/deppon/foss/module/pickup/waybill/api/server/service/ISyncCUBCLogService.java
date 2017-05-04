package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.CUBCLogEntity;

/**
 * 
 * ISyncCUBCLogService
 * @author 198771-zhangwei
 * 2016-10-13 上午9:35:28
 */
public interface ISyncCUBCLogService {
	
	/**
	 * 
	 * void
	 * @author 198771-zhangwei
	 * 2016-10-13 上午9:35:47
	 */
	public void insert(CUBCLogEntity cubcLogEntity);
	
	/**
	 * 作废数据
	 * void
	 * @author 198771-zhangwei
	 * 2016-11-10 下午5:14:59
	 */
	public void updateByWaybillNoAndCode(CUBCLogEntity cubcLogEntity);
	
	/**
	 * 根据id修改状态
	 * void
	 * @author 198771-zhangwei
	 * 2016-11-11 上午9:40:09
	 */
	public void updateById(CUBCLogEntity cubcLogEntity);
}

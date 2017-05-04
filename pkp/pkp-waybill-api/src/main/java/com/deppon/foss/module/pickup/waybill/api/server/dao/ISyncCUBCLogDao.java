package com.deppon.foss.module.pickup.waybill.api.server.dao;

import com.deppon.foss.module.pickup.waybill.shared.domain.CUBCLogEntity;

/**
 * 向cubc推送运单信息 日志
 * ISyncCUBCLogDao
 * @author 198771-zhangwei
 * 2016-10-12 下午6:37:12
 */
public interface ISyncCUBCLogDao {

	/**
	 * 
	 * void
	 * @author 198771-zhangwei
	 * 2016-10-12 下午6:37:39
	 */
	void insertCUBCLog(CUBCLogEntity cubcLogEntity);
	
	/**
	 * 根据运单号和服务编码作废
	 * void
	 * @author 198771-zhangwei
	 * 2016-11-10 下午6:08:47
	 */
	void updateByWaybillNoAndCode(CUBCLogEntity cubcLogEntity);
	
	/**
	 * 根据id修改状态
	 * void
	 * @author 198771-zhangwei
	 * 2016-11-11 上午9:37:05
	 */
	void updateById(CUBCLogEntity cubcLogEntity);
}

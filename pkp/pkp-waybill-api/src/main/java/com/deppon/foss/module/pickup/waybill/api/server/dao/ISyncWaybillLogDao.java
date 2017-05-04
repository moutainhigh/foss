package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillLogEntityQueryDto;

/**
 * 向cubc推送运单更改单信息 日志
 * ISyncWaybillLogDao
 * @author 198771-zhangwei
 * 2016-10-12 下午6:37:12
 */
public interface ISyncWaybillLogDao {

	/**
	 * 
	 * void
	 * @author 198771-zhangwei
	 * 2016-10-12 下午6:37:39
	 */
	void insertWaybillLog(WaybillLogEntity waybillLogEntity);
	
	/**
	 * 根据id作废
	 * void
	 * @author 198771-zhangwei
	 * 2016-11-10 下午6:08:47
	 */
	void deleteById(WaybillLogEntity waybillLogEntity);
	
	/**
	 * 根据id修改状态
	 * void
	 * @author 198771-zhangwei
	 * 2016-11-11 上午9:37:05
	 */
	void updateById(WaybillLogEntity waybillLogEntity);
	/**
	 * 根据条件查询推送CUBC接口日志
	 * @param queryDto
	 * @return
	 */
	public List<WaybillLogEntity> queryLogEntityByCondition(
			WaybillLogEntityQueryDto queryDto);
}

package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillLogEntityQueryDto;

/**
 * 
 * ISyncCUBCLogService
 * @author 198771-zhangwei
 * 2016-10-13 上午9:35:28
 */
public interface ISyncWaybillLogService {
	
	/**
	 * 
	 * void
	 * @author 198771-zhangwei
	 * 2016-10-13 上午9:35:47
	 */
	public void insert(WaybillLogEntity waybillLogEntity);
	
	/**
	 * 作废数据
	 * void
	 * @author 198771-zhangwei
	 * 2016-11-10 下午5:14:59
	 */
	public void deleteById(WaybillLogEntity waybillLogEntity);
	
	/**
	 * 根据id修改状态
	 * void
	 * @author 198771-zhangwei
	 * 2016-11-11 上午9:40:09
	 */
	public void updateById(WaybillLogEntity waybillLogEntity);
	
	/**
	 * 根据条件查询推送CUBC接口日志
	 * @return
	 */
	public List<WaybillLogEntity> queryLogEntityByCondition(
			WaybillLogEntityQueryDto queryDto);
}

package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.common.api.shared.dto.TruckArriveConfirmDto;

/**
 * @description:车辆到达确认对外服务
 * @author 073615
 *
 */
public interface ITruckArriveConfirmService extends IService {
	/**
	 * handleNo 配载单号
	 * confirmTime 确认时间
	 * confirmType 确认类型
	 * @param dto
	 */
	public void truckConfirm(TruckArriveConfirmDto dto);
	/**
	 * 通过实体直接博阿村
	 * @param dto truckArriveConfirmEntity 插入对象
	 */
    public void truckConfirmByEntity(TruckArriveConfirmDto dto);

}

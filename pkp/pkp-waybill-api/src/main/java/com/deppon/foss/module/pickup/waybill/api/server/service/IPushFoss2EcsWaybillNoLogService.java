package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.PushFoss2EcsWaybillNoLogEntity;

/*
 * FOSS推送运单号给ECS记录日志
 * 2016年7月23日 08:29:30 葛亮亮
 */
public interface IPushFoss2EcsWaybillNoLogService {
	
	//保存日志
	public void insertWaybillNoLog(PushFoss2EcsWaybillNoLogEntity logEntity);

}

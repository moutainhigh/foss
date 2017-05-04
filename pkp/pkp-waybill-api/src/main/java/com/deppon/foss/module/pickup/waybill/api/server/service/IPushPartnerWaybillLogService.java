package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.Map;

import com.deppon.foss.module.pickup.waybill.shared.domain.PushPartnerWaybillLogEntity;

/**
 * 向ptp推送运单信息 日志
 * @author 272311-sangwenhao
 * @date 2016-2-17
 */
public interface IPushPartnerWaybillLogService {
	
	/**
	 * 插入日志
	 * @param pushPartnerWaybillLogEntity
	 * @author 272311-sangwenhao
	 * @date 2016-2-17
	 */
	public void insert(PushPartnerWaybillLogEntity pushPartnerWaybillLogEntity);
	
	//根据订单创建时间和订单编号查询发送日志信息 2016年3月29日 16:28:09 葛亮亮
	public PushPartnerWaybillLogEntity queryPushPartnerWaybillLogByMap(Map<String, Object> waybillLogMap);
}

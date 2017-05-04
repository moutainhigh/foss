package com.deppon.foss.dubbo.ecs.api.service;

import java.util.List;

import com.deppon.foss.dubbo.ecs.api.define.EcsResponseDto;

public interface EcsDeliverForSettlementDubboService {
	/**
	 * @author 327090
	 * @param waybillNOs
	 * @return
	 */
	public EcsResponseDto queryUnpaidOnlinePay(List<String> waybillNOs);

}

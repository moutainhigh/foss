package com.deppon.foss.module.pickup.order.api.server.service;

import java.util.List;

import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;

public interface IAutoExpressOrderService {
	/**
	 * 加入线程池
	 * @param changeList
	 */
	public void aotoDispatchOrderList(List<DispatchOrderChangeEntity> changeList);
}

package com.deppon.foss.module.pickup.order.api.server.service;

import com.deppon.foss.module.pickup.order.api.shared.dto.AppOrderDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.ows.inteface.domain.orderstate.OrderStateRequest;

public interface IAppOrderJMSService {
	
	ResultDto sendOrderState(AppOrderDto request);
	
	ResultDto sendOrderStateToApp(OrderStateRequest request);
}

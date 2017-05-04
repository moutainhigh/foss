package com.deppon.foss.module.pickup.order.api.server.service;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.EWaybillProcessEntity;

public interface IAutoEWaybillActiveHandleService {
	void process(List<EWaybillProcessEntity> list);
}
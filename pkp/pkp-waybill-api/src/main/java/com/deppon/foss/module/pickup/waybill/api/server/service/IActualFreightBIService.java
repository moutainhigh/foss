package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightBIEntity;

public interface IActualFreightBIService  extends IService {
	
	int insert(ActualFreightBIEntity actualFreightBIEntity);

}

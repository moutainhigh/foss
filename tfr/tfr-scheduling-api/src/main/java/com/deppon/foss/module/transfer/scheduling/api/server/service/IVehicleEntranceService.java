package com.deppon.foss.module.transfer.scheduling.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.VehicleEntranceEntity;

public interface IVehicleEntranceService extends IService{

	void saveVehicleEntrance(VehicleEntranceEntity entity);
	
}

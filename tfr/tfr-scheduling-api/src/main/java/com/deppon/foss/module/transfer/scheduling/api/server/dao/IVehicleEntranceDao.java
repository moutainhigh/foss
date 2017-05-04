package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.VehicleEntranceEntity;

public interface IVehicleEntranceDao {

	void insertVehicleEntrance(VehicleEntranceEntity entity);
	
	int updateVehicleEntrance(VehicleEntranceEntity entity);
}

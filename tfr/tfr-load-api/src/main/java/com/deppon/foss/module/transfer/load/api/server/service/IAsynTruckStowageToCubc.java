package com.deppon.foss.module.transfer.load.api.server.service;

import com.deppon.foss.module.transfer.common.api.shared.dto.CubcVehicleAssembleBillRequest;
import com.deppon.foss.module.transfer.common.api.shared.dto.CubcVehicleAssembleBillResponse;


public interface IAsynTruckStowageToCubc {

	public CubcVehicleAssembleBillResponse pushAddTruckStowage(CubcVehicleAssembleBillRequest cubcVehicleAssembleBillDto);
}

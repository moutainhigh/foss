package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.dto.VtsStlVehicleAssembleBillDto;

public interface IAddVtsBillPayableService {
	
	
	public  void addvtsBillPayable(VtsStlVehicleAssembleBillDto dto,CurrentInfo currentInfo);
	
}

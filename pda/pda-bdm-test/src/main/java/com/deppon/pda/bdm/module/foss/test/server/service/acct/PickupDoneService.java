package com.deppon.pda.bdm.module.foss.test.server.service.acct;

import com.deppon.foss.module.pickup.pda.api.server.service.IPickupDoneService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PickupDoneDto;

public class PickupDoneService implements IPickupDoneService {

	@Override
	public boolean donePickUp(PickupDoneDto record) {
		return true;
	}

}

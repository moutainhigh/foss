package com.deppon.pda.bdm.module.foss.test.server.service.acct;

import com.deppon.foss.module.pickup.pda.api.server.service.IPdaOrderTaskHandleService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaOrderDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;

public class PdaOrderTaskHandleService implements IPdaOrderTaskHandleService{

	@Override
	public String udpateOrder(PdaOrderDto arg0) throws PdaProcessException {
		return "success";
	}


}

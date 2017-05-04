package com.deppon.foss.module.pickup.predeliver.api.server.service;

import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverSignDto;


public interface IFimsPdaService {
	
	void asynSendOtherRenueInfoToFims(PdaDeliverSignDto dto);
}

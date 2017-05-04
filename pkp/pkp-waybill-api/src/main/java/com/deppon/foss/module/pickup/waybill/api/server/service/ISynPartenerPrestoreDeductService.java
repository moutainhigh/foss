package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerPrestoreDeductResponse;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerPrestoreDeductDto;

//合伙人预存资金校验
public interface ISynPartenerPrestoreDeductService {
	
	SynPartenerPrestoreDeductResponse partenerPrestoreDeductResponse(PartenerPrestoreDeductDto prestoreDeductDto);
	
}

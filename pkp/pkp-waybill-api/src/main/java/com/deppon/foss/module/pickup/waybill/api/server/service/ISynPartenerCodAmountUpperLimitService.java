package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.SynPartenerCodAmountUpperLimitResponse;
import com.deppon.foss.module.pickup.waybill.shared.dto.PartenerCodAmountUpperLimitDto;

//合伙人代收货款上限额度校验
public interface ISynPartenerCodAmountUpperLimitService {
	
	SynPartenerCodAmountUpperLimitResponse partenerCodAmountUpperLimitResponse(PartenerCodAmountUpperLimitDto partenerCodAmountUpperLimit);
	
}

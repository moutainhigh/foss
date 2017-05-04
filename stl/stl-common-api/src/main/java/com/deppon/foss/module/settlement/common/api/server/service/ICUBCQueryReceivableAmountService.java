package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

public interface ICUBCQueryReceivableAmountService {
	
	List<BillReceivableEntity> queryReceivableAmount(String waybillNo);
	
}

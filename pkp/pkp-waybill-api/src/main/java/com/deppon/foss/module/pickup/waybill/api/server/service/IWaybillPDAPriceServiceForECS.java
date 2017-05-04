package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateDto;
import com.deppon.foss.module.pickup.waybill.shared.response.EscWayPDABillResponseEntity;

/**
 * 提供给悟空PDA开单计算运费
 */
public interface IWaybillPDAPriceServiceForECS {
	
	EscWayPDABillResponseEntity queryPDABillPrice(PdaQueryBillCalculateDto billCalculateDto);
}

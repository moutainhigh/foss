package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.EscWayBillRequestEntity;
import com.deppon.foss.module.pickup.waybill.shared.response.EscWayBillResponseEntity;

/**
 * FOSS 查询总运费提供给悟空
 * @author Foss-308595-GELL
 * 2016年5月6日 18:30:33
 *
 */
public interface IWaybillPriceChangeServiceForECS {
	
	//改单查询总运费
	EscWayBillResponseEntity queryChangePrice(EscWayBillRequestEntity requestEntity);
}

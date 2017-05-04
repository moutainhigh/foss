package com.deppon.foss.module.pickup.pricing.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.EscWayBillRequestEntity;
import com.deppon.foss.module.pickup.waybill.shared.response.EscWayBillResponseEntity;

/**
 * FOSS 提供给悟空查询快递总运费
 * @author foss-265475-GELL
 *
 */

public interface IEcsBillPriceDubboService {

	/**
	 * 悟空查询总运费
	 * 2017年2月18日 08:54:35
	 * @return
	 */
	
	 EscWayBillResponseEntity queryBillPrice(EscWayBillRequestEntity requestEntity);	
}

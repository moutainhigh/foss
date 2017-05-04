/**
 * 
 */
package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillGXGEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResponseGXG;


/**
 * @author 
 *
 */
public interface IWaybillGXGService {
	
	ResponseGXG addWaybillExpressEntity(WaybillGXGEntity waybillGXGEntity);
	
}
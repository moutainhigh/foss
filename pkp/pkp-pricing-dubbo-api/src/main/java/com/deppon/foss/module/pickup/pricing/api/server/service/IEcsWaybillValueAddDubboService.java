package com.deppon.foss.module.pickup.pricing.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.request.WaybillValueAddRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.WaybillValueAddResponse;

/**
 * FOSS 提供给ECS系统查询增值服务信息
 * @author foss-265475-GELL
 *
 */
public interface IEcsWaybillValueAddDubboService {

	/**
	 * ECS系统查询增值服务信息
	 * 2017年2月18日 08:54:35
	 * @return
	 */
	WaybillValueAddResponse getValueAddDetailInfosForECS(WaybillValueAddRequest waybillValueAddRequest);
}

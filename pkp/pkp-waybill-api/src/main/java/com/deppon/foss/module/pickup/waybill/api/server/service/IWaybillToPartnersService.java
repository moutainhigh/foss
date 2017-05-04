package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDestinationDto;

/**
 * 推送合伙人信息
 * @author 272311-sangwenhao
 * @date 2016-1-30
 */
public interface IWaybillToPartnersService {
	/**
	 * 根据运单号，目的站是否为合伙人 推送运单信息至ptp
	 * @return
	 * @author 272311-sangwenhao
	 * @date 2016-1-30
	 */
	boolean sendDestinationInfo(WaybillDestinationDto waybillDestinationDto);

}

package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.api.shared.dto.WaybillHomeInfoDto;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.WaybillHomeInfoResponseDto;

public interface IWaybillHomeImpPushService {
	/**
	 * FOSS向DOP推送家装运单信息
	 * @param requestDto
	 * @return
	 */
	public WaybillHomeInfoResponseDto pushWaybillHomeInfo(WaybillHomeInfoDto requestDto);
}

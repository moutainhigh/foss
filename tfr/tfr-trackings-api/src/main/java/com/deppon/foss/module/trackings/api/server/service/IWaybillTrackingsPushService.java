package com.deppon.foss.module.trackings.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.trackings.api.shared.dto.ResponseDto;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsRequestDto;

public interface IWaybillTrackingsPushService extends IService{

	public ResponseDto pushWaybillTrack(WaybillTrackingsRequestDto requestDto);
}

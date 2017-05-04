package com.deppon.foss.module.trackings.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.trackings.api.shared.dto.OrderWaybillDto;
import com.deppon.foss.module.trackings.api.shared.dto.ResponseDto;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;

public interface IWaybillTrackingsService  extends IService{
	void addOneWaybillTrack(WaybillTrackingsDto trackDto);
	void addWaybillTracks(List<WaybillTrackingsDto> trackDtoList);
	ResponseDto saveOrderWaybillNo(OrderWaybillDto waybillDto);
	ResponseDto savePushResult(ResponseDto rspDto);
	void pushWaybillTracks();
}

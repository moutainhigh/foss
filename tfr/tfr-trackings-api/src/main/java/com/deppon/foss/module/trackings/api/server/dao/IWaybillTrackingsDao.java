package com.deppon.foss.module.trackings.api.server.dao;

import java.util.List;

import com.deppon.foss.module.trackings.api.shared.domain.WaybillTrackingsLogEntity;
import com.deppon.foss.module.trackings.api.shared.dto.OrderWaybillDto;
import com.deppon.foss.module.trackings.api.shared.dto.WaybillTrackingsDto;

public interface IWaybillTrackingsDao{
	public int addOrderWaybillNo(OrderWaybillDto dto);

	public List<OrderWaybillDto> queryWaybillInfoByNo(String waybillNo);

	public void saveWaybillTrack(WaybillTrackingsDto trackDto);

	public List<OrderWaybillDto> queryPushWaybillInfo(OrderWaybillDto paramDto);

	public boolean checkWaybillNo(String code);

	public void addTrackLog(WaybillTrackingsLogEntity log);

	public void updateOrderWaybillByNo(OrderWaybillDto dto);

	public void deleteTrackByNo(String code);

	public String queryRouteId(String waybillNo);

//	public void updateWaybillTrack(WaybillTrackingsDto trackDto);

//	public List<WaybillTrackingsDto> queryTrackInfo();

	public List<OrderWaybillDto> queryStopPushWaybillInfo();

	public List<WaybillTrackingsDto> queryTrackInfoByWaybillNo(String code);

	public void updateOrderWaybillInfoByNo(String code,String orderType);

	public void updateTrackByNoAndRoute(String waybillNo, List<String> routeList);

	public void updateOrderWaybillByNoAndPush(OrderWaybillDto dto);

	public List<OrderWaybillDto> queryAbortPushWaybillInfo();

	public List<OrderWaybillDto> queryAppendPushWaybillInfo();

	public List<OrderWaybillDto> queryOverridePushWaybillInfo();

	public boolean checkExistsTrack(WaybillTrackingsDto trackDto);

}

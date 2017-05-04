package com.deppon.foss.module.transfer.airfreight.api.server.service;

import com.deppon.foss.framework.service.IService;

public interface IAirWaybillToCubcService extends IService {
	
	/**
	 * 同步航空正单信息至CUBC
	 * 
	 * @param requestStr
	 */
	public void pushAddAirWaybill(String requestStr);

	/**
	 * 同步合大票信息至CUBC
	 * 
	 * @param requestStr
	 */
	public void pushAddAirRevisebill(String requestStr);
	
	/**
	 * 同步中转提货清单至CUBC
	 * 
	 * @param requestStr
	 */
	public void pushAddTransferWaybill(String requestStr);

}

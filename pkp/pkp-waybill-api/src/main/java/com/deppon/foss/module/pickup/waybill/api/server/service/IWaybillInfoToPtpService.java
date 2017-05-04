package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.dto.PtpWaybillDto;



/**
 * 运单信息异步发送给ptp系统
 * @author 272311-sangwenhao
 * @date 2016-1-15
 */
public interface IWaybillInfoToPtpService {
	/**
	 * 
	 * @param ptpDto 运单信息
	 * @author 272311-sangwenhao
	 * @date 2016-1-15
	 */
	void asynSendWaybillInfoToPtp(PtpWaybillDto ptpWaybillDto);
	
}

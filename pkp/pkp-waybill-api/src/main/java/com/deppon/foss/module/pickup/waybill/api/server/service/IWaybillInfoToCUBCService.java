package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillLogEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;

/**
 * 运单(零担)信息、实际承运表信息、更改单信息、结算单据信息异步发送给cubc(客户结算中心)系统
 * IWaybillInfoToCubcService
 * @author 198771-zhangwei
 * 2016-10-12 上午10:55:01
 */
public interface IWaybillInfoToCUBCService {
	
	//推送运单、实际承运和财务单据
	void asyncSendWaybillInfoToCUBC(WaybillDto waybillDto);
	
	//推送更改单、实际承运和财务单据
	void asyncSendWaybillRfcInfoToCUBC(WaybillRfcEntity waybillRfcEntity, WaybillDto newVersionDto);
	
	//页面推送报文
	void asyncSendWaybillToCUBC(WaybillLogEntity waybillLogEntity);
}

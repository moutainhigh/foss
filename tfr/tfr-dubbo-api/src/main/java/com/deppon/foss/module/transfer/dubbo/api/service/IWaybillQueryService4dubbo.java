package com.deppon.foss.module.transfer.dubbo.api.service;

import java.util.List;

import com.deppon.foss.module.transfer.dubbo.api.define.WaybillExpressEntity;
import com.deppon.foss.module.transfer.dubbo.api.define.WaybillInfoDto;

public interface IWaybillQueryService4dubbo {

	List<WaybillInfoDto> queryWaybillInfoForSOC(List<String> waybillNoList);

	WaybillExpressEntity queryWaybillByOriginalWaybillNo(String waybillNo, String waybillExpresstypeReturnCargo);

	List<WaybillInfoDto> queryWaybillInfo(List<String> waybillList);

}
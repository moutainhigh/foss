package com.deppon.foss.module.pickup.waybill.api.server.service;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPackBIEntity;

public interface IWaybillPackBIService {
	
	int deleteByPrimaryKey(String id);

    int addWaybillPackBIEntity(WaybillEntity waybillEntity);

    int updateByWaybillNo(WaybillPackBIEntity waybillPackBIEntity);

    WaybillPackBIEntity queryByWaybillNo(String waybillNo);

}

package com.deppon.foss.module.pickup.waybill.api.server.dao;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPackBIEntity;

public interface IWaybillPackBIDao {
	
	int deleteByPrimaryKey(String id);

    int addWaybillPackBIEntity(WaybillPackBIEntity waybillPackBIEntity);

    int updateByWaybillNo(WaybillPackBIEntity waybillPackBIEntity);

    WaybillPackBIEntity queryByWaybillNo(String waybillNo);

}

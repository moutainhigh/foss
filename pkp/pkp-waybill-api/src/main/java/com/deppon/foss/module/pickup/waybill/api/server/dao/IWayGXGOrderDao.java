
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillGXGEntity;


public interface IWayGXGOrderDao {

    int checkGXGOrderbill(String customerLableNum);
	int createGXGOrderbill(WaybillGXGEntity waybillGXGEntity);
	
}
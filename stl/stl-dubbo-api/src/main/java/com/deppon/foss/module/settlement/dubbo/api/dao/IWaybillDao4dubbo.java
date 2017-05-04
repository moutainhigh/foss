package com.deppon.foss.module.settlement.dubbo.api.dao;

import com.deppon.foss.module.settlement.dubbo.api.define.WaybillEntity;

public interface IWaybillDao4dubbo {
	
	/**
	 * 通过运单编号查询运单
	 * @param waybillNo
	 * @return
	 */
	WaybillEntity queryWaybillByNo(String waybillNo);

}

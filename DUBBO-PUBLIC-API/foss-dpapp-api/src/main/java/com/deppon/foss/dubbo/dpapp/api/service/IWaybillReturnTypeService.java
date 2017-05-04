package com.deppon.foss.dubbo.dpapp.api.service;

import com.deppon.foss.dubbo.dpapp.api.define.WaybillCheckStatus;

public interface IWaybillReturnTypeService {

	/**
	 * 判别查询原单号关联的单号类型
	 * @param waybillNo
	 * @return
	 */
	WaybillCheckStatus getWaybillReturnType(String waybillNo);
	
}

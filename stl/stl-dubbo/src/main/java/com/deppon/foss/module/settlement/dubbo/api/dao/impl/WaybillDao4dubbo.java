package com.deppon.foss.module.settlement.dubbo.api.dao.impl;

import com.deppon.foss.module.settlement.dubbo.api.dao.IWaybillDao4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillEntity;
import com.deppon.foss.module.settlement.dubbo.api.define.WaybillQueryArgsDto;
import com.deppon.foss.module.settlement.dubbo.api.util.FossConstants;

public class WaybillDao4dubbo extends BaseDao implements IWaybillDao4dubbo {
	
	private static final String NAMESPACE = "foss.ecs.WaybillEntity4dubboMapper.";

	/**
	 * 327090
	 */
	@Override
	public WaybillEntity queryWaybillByNo(String waybillNo) {
		// 封装查询条件
		WaybillQueryArgsDto argsDto = new WaybillQueryArgsDto();
		argsDto.setWaybillNo(waybillNo);
		argsDto.setActive(FossConstants.YES);

		return (WaybillEntity) this.getSqlSession().selectOne(
				NAMESPACE + "selectByWaybillNoAndOrderNo_4_dubbo", argsDto);
	}

}

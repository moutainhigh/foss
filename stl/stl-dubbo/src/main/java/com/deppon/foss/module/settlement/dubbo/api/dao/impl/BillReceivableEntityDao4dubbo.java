package com.deppon.foss.module.settlement.dubbo.api.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.settlement.dubbo.api.dao.IBillReceivableEntityDao4dubbo;
import com.deppon.foss.module.settlement.dubbo.api.define.BillReceivableEntity;

public class BillReceivableEntityDao4dubbo extends BaseDao implements IBillReceivableEntityDao4dubbo {

	@Override
	public int updateBillReceivableWithholdStatus(BillReceivableEntity entity) {
		return (Integer) this.getSqlSession().update("updateBillReceivableWithholdStatus_4_dubbo", entity);
	}

	@Override
	public List<BillReceivableEntity> selectByWayBillNoAndBillType(String wayBillNo, String billType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("billType", billType);
		map.put("wayBillNo", wayBillNo);
		map.put("active", "Y");
		return this.getSqlSession().selectList("selectByWayBillNoAndBillType_4_dubbo", map);
	}

}
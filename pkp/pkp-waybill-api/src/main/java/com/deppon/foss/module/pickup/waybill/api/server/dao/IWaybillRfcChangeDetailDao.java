package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;

public interface IWaybillRfcChangeDetailDao {
	
	WaybillRfcChangeDetailEntity selectByPrimaryKey(String id);
	
	int deleteByPrimaryKey(String id);
	
	int insert(WaybillRfcChangeDetailEntity record);
	
	int insertSelective(WaybillRfcChangeDetailEntity record);
	
	int updateByPrimaryKeySelective(WaybillRfcChangeDetailEntity record);
	
	int updateByPrimaryKey(WaybillRfcChangeDetailEntity record);
	
	List<WaybillRfcChangeDetailEntity> queryRfcChangeDetail(String waybillRfcId);
}

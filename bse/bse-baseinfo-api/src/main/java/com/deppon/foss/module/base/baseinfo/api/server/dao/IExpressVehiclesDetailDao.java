package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesDetailEntity;

public interface IExpressVehiclesDetailDao {
	int addExpressVehiclesDetail(ExpressVehiclesDetailEntity expressVehiclesDetailEntity);

	List<ExpressVehiclesDetailEntity> queryExpressVehiclesByEmpCode(
			String empCode);

	int deleteExpressVehiclesByEmpCode(String empCode);

	int updateExpressVehiclesDetailBySelective(
			ExpressVehiclesDetailEntity expressVehiclesDetailEntity,List<String> ids);
}

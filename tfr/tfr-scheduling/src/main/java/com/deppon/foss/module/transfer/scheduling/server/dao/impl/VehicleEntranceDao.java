package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IVehicleEntranceDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.VehicleEntranceEntity;

public class VehicleEntranceDao extends iBatis3DaoImpl implements
		IVehicleEntranceDao {

	private final String NAMESPACE = "com.deppon.foss.module.transfer.scheduling.api.server.dao.IVehicleEntranceDao.";

	@Override
	public void insertVehicleEntrance(VehicleEntranceEntity entity) {
		super.getSqlSession().insert(NAMESPACE + "insertVehicleEntrance",
				entity);

	}

	@Override
	public int updateVehicleEntrance(VehicleEntranceEntity entity) {
		return super.getSqlSession().update(
				NAMESPACE + "updateVehicleEntrance", entity);

	}

}

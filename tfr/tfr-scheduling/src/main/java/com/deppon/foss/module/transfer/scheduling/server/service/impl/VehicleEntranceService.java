package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DuplicateKeyException;

import com.deppon.foss.module.transfer.scheduling.api.server.dao.IVehicleEntranceDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IVehicleEntranceService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.VehicleEntranceEntity;
import com.deppon.foss.util.UUIDUtils;

public class VehicleEntranceService implements IVehicleEntranceService {

	private IVehicleEntranceDao vehicleEntranceDao;

	public void setVehicleEntranceDao(IVehicleEntranceDao vehicleEntranceDao) {
		this.vehicleEntranceDao = vehicleEntranceDao;
	}

	/**
	 * @desc 车辆入场
	 * @param entity
	 * @date 2015年7月6日 上午11:10:11
	 * @author Ouyang
	 */
	@Override
	public void saveVehicleEntrance(VehicleEntranceEntity entity) {
		if (entity == null || StringUtils.isEmpty(entity.getTruckTaskId())
				|| StringUtils.isEmpty(entity.getTfrCtrCode())
				|| StringUtils.isEmpty(entity.getVehicleNo())) {
			return;
		}

		Date date = new Date();
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateTime(date);
		entity.setModifyTime(date);

		int cnt = vehicleEntranceDao.updateVehicleEntrance(entity);
		if (cnt == 0) {
			try {
				vehicleEntranceDao.insertVehicleEntrance(entity);
			} catch (DuplicateKeyException e) {
				vehicleEntranceDao.updateVehicleEntrance(entity);
			}
		}
	}

}

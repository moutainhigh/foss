package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonVehicleDrivingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonVehicleDrivingService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleDrivingEntity;


public class CommonVehicleDrivingService implements ICommonVehicleDrivingService  {
	/**
	 * 日志打印对象申明
	 */

	
	private ICommonVehicleDrivingDao commonVehicleDrivingDao;

	public void setCommonVehicleDrivingDao(
			ICommonVehicleDrivingDao commonVehicleDrivingDao) {
		this.commonVehicleDrivingDao = commonVehicleDrivingDao;
	}


	/**
	 * 选择器查询出的总条数
	 */
	@Override
	public long queryCommonVehicleDrivingRecordByCondition(
			VehicleDrivingEntity entity) {
		
		return commonVehicleDrivingDao.queryVehicleDrivingCount(entity);
	}


	/**
	 * 选择器查询出维护的所有长途车队信息
	 */
	@Override
	public List<VehicleDrivingEntity> queryCommonVehicleDrivingByCondition(
			VehicleDrivingEntity entity, int start, int limit) {
		
		return commonVehicleDrivingDao.queryVehicleDriving(entity, start, limit);
	}
	
	

}

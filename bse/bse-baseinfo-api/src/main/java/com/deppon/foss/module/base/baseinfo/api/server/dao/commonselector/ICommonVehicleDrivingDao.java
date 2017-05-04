package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleDrivingEntity;

public interface ICommonVehicleDrivingDao {
	 /**
	  * 选择器查询
	  * @param entity
	  * @return
	  */
	  long queryVehicleDrivingCount(VehicleDrivingEntity entity);
	    
	  /**
	   * 选择器查询
	   * @param entity
	   * @param start
	   * @param limit
	   * @return
	   */
	   List<VehicleDrivingEntity> queryVehicleDriving(VehicleDrivingEntity entity,int start, int limit);
	
	
}

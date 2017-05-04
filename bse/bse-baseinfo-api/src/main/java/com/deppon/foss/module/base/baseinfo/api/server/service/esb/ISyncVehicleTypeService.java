package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;

/**
 * 车型每公里费用表基础资料同步 
 * @author 310854
 * @date 2016-4-20
 */
public interface ISyncVehicleTypeService {

	/**
	 * 车型每公里费用表基础资料同步 
	 * @author 310854
	 * @date 2016-4-20
	 */
	void syncVehicleType(List<VehicleTypeEntity> entitys);
}

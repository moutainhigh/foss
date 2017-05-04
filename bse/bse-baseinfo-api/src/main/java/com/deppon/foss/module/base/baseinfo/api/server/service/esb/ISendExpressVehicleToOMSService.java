package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesDetailEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;

public interface ISendExpressVehicleToOMSService {

	/**
	 * 同步快递车辆信息到OMS
	 * 
	 * @author 313353
	 * @date 2016-3-21 上午11:50:25
	 */
	void syncExpressVehicleToOMS(List<ExpressVehiclesEntity> entitys,
			List<ExpressVehiclesDetailEntity> detailEntitys) ;
}

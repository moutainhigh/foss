package com.deppon.foss.module.pickup.pda.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pda.api.shared.domain.BindingLeasedTruckEntity;

/**
 * 外请车司机登录APP
 * PDA调用，通过车牌号获取车队code、操作人code
 * @author 310854
 * @date 2016-5-28
 */
public interface IBindingLeasedVehicleService extends IService {
	BindingLeasedTruckEntity queryBindingLeasedDate(String vehicleNo);
}

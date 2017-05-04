package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;

/**
 * 同步定人定区信息给OMS  Web Service客户端服务接口实现
 * @author 310854
 * @date 2016-3-22下午5:09:57
 */
public interface ISyncRegionalVehicleService {

	/**
	 * 同步定人定区信息
	 * @author 310854
	 * @date 2016-3-22下午5:22:38
	 */
	void syncRegionalVehicle(List<RegionVehicleEntity> entitys);
}

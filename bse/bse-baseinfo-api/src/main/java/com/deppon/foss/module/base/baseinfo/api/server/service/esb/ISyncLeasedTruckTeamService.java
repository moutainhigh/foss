package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleException;

/**
 * 同步外请车服务资料信息同步给OMS  Web Service客户端服务接口实现
 * @author 310854
 * @date 2016-6-22
 */
public interface ISyncLeasedTruckTeamService {

	/**
	 * 外请车服务资料信息同步给OMS
	 * @author 310854
	 * @date 2016-6-22
	 */
	void syncLeasedTruckTeam(List<LeasedTruckEntity> entitys) throws LeasedVehicleException;
}

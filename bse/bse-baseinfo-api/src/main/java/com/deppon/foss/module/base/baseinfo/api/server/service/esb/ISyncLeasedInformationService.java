package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.ecs.inteface.domain.LeasedDriverInfo;
import com.deppon.ecs.inteface.domain.LeasedTruckInfo;
import com.deppon.ecs.inteface.domain.WhiteListAuditInfo;

public interface ISyncLeasedInformationService {
	
	/**
	 * 同步外请车信息到ECS
	 * @param entitys
	 * @throws Exception
	 */
	void syncLeasedTruckToECS(List<LeasedTruckInfo> entitys);

	/**
	 * 同步外请司机信息到ECS
	 * @param entitys
	 * @throws Exception
	 */
	void syncLeasedDriverToECS(List<LeasedDriverInfo> entitys);

	
	/**
	 * 同步外请司机信息到ECS
	 * @param entitys
	 * @throws Exception
	 */
	void syncWhiteListAuditToECS(List<WhiteListAuditInfo> entitys);
	
	
	
}

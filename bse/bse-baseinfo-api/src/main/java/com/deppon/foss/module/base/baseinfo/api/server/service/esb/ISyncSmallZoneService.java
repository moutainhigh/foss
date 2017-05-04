package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;

/**
 * 同步集中接送货小区信息给OMS  Web Service客户端服务接口实现
 * @author 310854
 * @date 2016-4-6
 */
public interface ISyncSmallZoneService {

	/**
	 * 集中接送货小区信息
	 * @author 310854
	 * @date 2016-3-22下午5:22:38
	 */
	void syncSmallZone(List<SmallZoneEntity> entitys);
}

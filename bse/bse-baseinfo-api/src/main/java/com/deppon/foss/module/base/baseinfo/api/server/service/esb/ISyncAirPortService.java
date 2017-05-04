package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity;
/**
 * 
 * 同步机场信息
 * @author 273311 
 * @date 2016-10-29 上午11:06:49
 * @since
 * @version
 */
public interface ISyncAirPortService {
	
     void SyncAirPort(List<AirportEntity> entitys,String operateType);
}

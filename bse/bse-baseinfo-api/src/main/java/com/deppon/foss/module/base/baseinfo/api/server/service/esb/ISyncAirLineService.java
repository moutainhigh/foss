package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
/**
 * 
 * 同步航空公司
 * @author 273311 
 * @date 2016-10-29 上午11:00:55
 * @since
 * @version
 */
public interface ISyncAirLineService {
	
     void SyncAirLine(List<AirlinesEntity> entitys,String operateType);
}

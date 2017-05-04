package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity;
/**
 * 同步航空公司代理人
 * @author 273311 
 * @date 2016-10-29 上午10:43:13
 * @since
 * @version
 */
public interface ISyncAirLinesAgentService {
	
     void SyncAirLinesAgent(List<AirlinesAgentEntity> entitys,String operateType);
}

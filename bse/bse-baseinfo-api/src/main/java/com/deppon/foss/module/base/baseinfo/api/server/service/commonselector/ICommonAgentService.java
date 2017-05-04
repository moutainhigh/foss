package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AgentEntity;

public interface ICommonAgentService {

	
	List<AgentEntity> queryAgentListBySelectiveCondition(
			AgentEntity airlinesAgent, int offset, int limit);

	long queryAgentRecordCountBySelectiveCondition(AgentEntity airlinesAgent);

}

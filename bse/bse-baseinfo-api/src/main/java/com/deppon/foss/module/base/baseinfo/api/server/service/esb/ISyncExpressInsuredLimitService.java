package com.deppon.foss.module.base.baseinfo.api.server.service.esb;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ConfigurationParamsEntity;


public interface ISyncExpressInsuredLimitService {
	
	void synInfoToCRMCCGW(ConfigurationParamsEntity entity);
	
}

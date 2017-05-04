package com.deppon.foss.module.pickup.pricing.api.server.dao;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.FossToEngineLogEntity;

public interface IFossToEngineDao {
	void insertLog(FossToEngineLogEntity fossToEngineLogEntity);
}

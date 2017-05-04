package com.deppon.foss.module.settlement.ecsitf.server.rs;

import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;

public interface ISignForService{

	int updateOrInsertSignFor(WaybillSignResultEntity waybillSignResultEntity);
}

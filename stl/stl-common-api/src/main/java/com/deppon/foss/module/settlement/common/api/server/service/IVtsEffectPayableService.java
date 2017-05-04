package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillSignResultEntity;

public interface IVtsEffectPayableService {
	
	/**
	 *VTS生效装卸费,代收货款应付单 
	 */
	void EffectPayableByVtsInfo(WaybillSignResultEntity entity ,CurrentInfo currentInfo);
}

package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;

public interface IVtsTakingService {
	/**
	 * 确认收入service接口
	 */
	void confirmIncome(LineSignDto dto,
			CurrentInfo currentInfo);
}

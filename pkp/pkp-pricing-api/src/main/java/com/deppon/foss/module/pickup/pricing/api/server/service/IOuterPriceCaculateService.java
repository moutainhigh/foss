package com.deppon.foss.module.pickup.pricing.api.server.service;

import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryOuterPriceCaccilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultOuterPriceCaccilateDto;

public interface IOuterPriceCaculateService {

	/**
	 * 计算偏线中转费
	 * @param queryOuterPriceCaccilateDto
	 */
	public ResultOuterPriceCaccilateDto calulateOuterPrice(QueryOuterPriceCaccilateDto queryOuterPriceCaccilateDto);
	
}

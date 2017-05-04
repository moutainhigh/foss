package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultBillCalculateDto;

/**
 * 实现PDA计算运费
 * @author Foss-308595-GELL
 *
 */
public interface IWaybillPDAServiceForEcs {
	//根据条件查询快遞PDA价格
	List<PdaResultBillCalculateDto> queryExpressBillCalculate(PdaQueryBillCalculateDto billCalculateDto);
}

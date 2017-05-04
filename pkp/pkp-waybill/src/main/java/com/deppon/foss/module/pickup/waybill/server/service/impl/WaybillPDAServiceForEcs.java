package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.osoa.sca.annotations.Service;

import com.deppon.foss.module.pickup.pricing.api.server.service.IPdaBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.util.PriceUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultBillCalculateDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPDAServiceForEcs;
/**
 * 实现PDA计算运费
 * @author Foss-308595-GELL
 *
 */
@Service
public class WaybillPDAServiceForEcs implements IWaybillPDAServiceForEcs {
	/**
     * PDA价格计算
     */
	@Resource
    IPdaBillCaculateService pdaBillCaculateService;

	@Override
	public List<PdaResultBillCalculateDto> queryExpressBillCalculate(
			PdaQueryBillCalculateDto billCalculateDto) {
		//数据检验
		PriceUtil.checkExpressPdaQueryBillCalcuateDto(billCalculateDto);
		return pdaBillCaculateService.queryPdaExpressBillPrice(billCalculateDto);
	}
}

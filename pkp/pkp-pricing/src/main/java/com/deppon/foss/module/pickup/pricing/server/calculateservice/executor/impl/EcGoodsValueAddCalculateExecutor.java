package com.deppon.foss.module.pickup.pricing.server.calculateservice.executor.impl;

import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.executor.ICalculateExecutor;

import java.util.List;


public class EcGoodsValueAddCalculateExecutor implements ICalculateExecutor<List<ValueAddDto>,QueryBillCacilateValueAddDto>{

	private IBillCaculateService ecGoodsBillCalculateService;
	
	private IGuiBillCaculateService ecGoodsGuiBillCaculateService;
	
	@Override
	public List<ValueAddDto> execute(QueryBillCacilateValueAddDto u) {
		List<ValueAddDto> rstBillCalDtoLst = ecGoodsBillCalculateService.searchValueAddPriceList(u);
		if(rstBillCalDtoLst != null)
			return rstBillCalDtoLst;
		else
			return null;
	}

	public IBillCaculateService getEcGoodsBillCalculateService() {
		return ecGoodsBillCalculateService;
	}

	public void setEcGoodsBillCalculateService(
			IBillCaculateService ecGoodsBillCalculateService) {
		this.ecGoodsBillCalculateService = ecGoodsBillCalculateService;
	}

	public IGuiBillCaculateService getEcGoodsGuiBillCaculateService() {
		return ecGoodsGuiBillCaculateService;
	}

	public void setEcGoodsGuiBillCaculateService(
			IGuiBillCaculateService ecGoodsGuiBillCaculateService) {
		this.ecGoodsGuiBillCaculateService = ecGoodsGuiBillCaculateService;
	}
}

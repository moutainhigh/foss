package com.deppon.foss.module.pickup.pricing.server.calculateservice.executor.impl;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.executor.ICalculateExecutor;


public class EcGoodsCalculateExecutor implements ICalculateExecutor<List<GuiResultBillCalculateDto>,GuiQueryBillCalculateDto>{

	private IBillCaculateService ecGoodsBillCalculateService;
	
	private IGuiBillCaculateService ecGoodsGuiBillCaculateService;
	
	@Override
	public List<GuiResultBillCalculateDto> execute(GuiQueryBillCalculateDto u) {
		List<GuiResultBillCalculateDto> rstBillCalDtoLst = ecGoodsGuiBillCaculateService.queryGuiBillPrice(u);
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

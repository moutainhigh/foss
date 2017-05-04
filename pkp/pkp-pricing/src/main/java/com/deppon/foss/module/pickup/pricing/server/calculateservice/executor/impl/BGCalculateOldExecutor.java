package com.deppon.foss.module.pickup.pricing.server.calculateservice.executor.impl;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.executor.ICalculateExecutor;


public class BGCalculateOldExecutor implements ICalculateExecutor<List<GuiResultBillCalculateDto>,GuiQueryBillCalculateDto>{

	private IBillCaculateService billCaculateOldService;
	
	private IGuiBillCaculateService guiBillCaculateOldService;
	
	@Override
	public List<GuiResultBillCalculateDto> execute(GuiQueryBillCalculateDto u) {
		List<GuiResultBillCalculateDto> rstBillCalDtoLst = guiBillCaculateOldService.queryGuiBillPrice(u);
		if(rstBillCalDtoLst != null)
			return rstBillCalDtoLst;
		else
			return null;
	}

	public IBillCaculateService getBillCaculateOldService() {
		return billCaculateOldService;
	}

	public void setBillCaculateOldService(
			IBillCaculateService billCaculateOldService) {
		this.billCaculateOldService = billCaculateOldService;
	}

	public IGuiBillCaculateService getGuiBillCaculateOldService() {
		return guiBillCaculateOldService;
	}

	public void setGuiBillCaculateOldService(
			IGuiBillCaculateService guiBillCaculateOldService) {
		this.guiBillCaculateOldService = guiBillCaculateOldService;
	}
}

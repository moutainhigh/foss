package com.deppon.foss.module.pickup.pricing.server.calculateservice.executor.impl;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.executor.ICalculateExecutor;


public class BGValueAddCalculateOldExecutor implements ICalculateExecutor<List<ValueAddDto>,QueryBillCacilateValueAddDto>{

	private IBillCaculateService billCaculateOldService;
	
	private IGuiBillCaculateService guiBillCaculateOldService;
	
	@Override
	public List<ValueAddDto> execute(QueryBillCacilateValueAddDto u) {
		List<ValueAddDto> rstBillCalDtoLst = billCaculateOldService.searchValueAddPriceList(u);
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

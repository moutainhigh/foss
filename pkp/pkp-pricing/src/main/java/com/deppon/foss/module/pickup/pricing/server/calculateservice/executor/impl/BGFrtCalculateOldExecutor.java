package com.deppon.foss.module.pickup.pricing.server.calculateservice.executor.impl;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.executor.ICalculateExecutor;


public class BGFrtCalculateOldExecutor implements ICalculateExecutor<List<ProductPriceDto>,QueryBillCacilateDto>{

	private IBillCaculateService billCaculateOldService;
	
	private IGuiBillCaculateService guiBillCaculateOldService;
	
	@Override
	public List<ProductPriceDto> execute(QueryBillCacilateDto u) {
		List<ProductPriceDto> rstBillCalDtoLst = billCaculateOldService.searchProductPriceList(u);
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

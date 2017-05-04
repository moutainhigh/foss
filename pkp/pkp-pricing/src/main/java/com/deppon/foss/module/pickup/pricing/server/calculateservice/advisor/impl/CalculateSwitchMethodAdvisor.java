package com.deppon.foss.module.pickup.pricing.server.calculateservice.advisor.impl;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.advisor.AbstractCalculateAdvisor;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.advisor.ResultRedirect;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.annotation.BeforeAdv;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.control.AbstractCalculateControl;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.dispatcher.IBillCalculateDispatcher;

public class CalculateSwitchMethodAdvisor extends AbstractCalculateAdvisor{
	
	private IBillCalculateDispatcher billCalculateDispatcher;
	
	@BeforeAdv(value = "queryGuiBillPrice")
	public ResultRedirect queryGuiBillPrice(GuiQueryBillCalculateDto billCalculateDto) throws Throwable{
		AbstractCalculateControl calculateControl = (AbstractCalculateControl)billCalculateDispatcher.dispatch(billCalculateDto.getProductCode());
		if(calculateControl != null){
			List<GuiResultBillCalculateDto> resultBillCalculateDtoLst = calculateControl.handler(billCalculateDto);
			return new ResultRedirect(resultBillCalculateDtoLst);
		}else
			return null;
	}

	public IBillCalculateDispatcher getBillCalculateDispatcher() {
		return billCalculateDispatcher;
	}

	public void setBillCalculateDispatcher(
			IBillCalculateDispatcher billCalculateDispatcher) {
		this.billCalculateDispatcher = billCalculateDispatcher;
	}

}

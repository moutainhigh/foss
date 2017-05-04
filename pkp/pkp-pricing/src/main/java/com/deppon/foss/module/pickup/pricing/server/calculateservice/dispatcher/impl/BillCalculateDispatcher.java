package com.deppon.foss.module.pickup.pricing.server.calculateservice.dispatcher.impl;

import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.control.ICalculateControl;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.control.factory.ICalculateControlFactory;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.dispatcher.IBillCalculateDispatcher;

public class BillCalculateDispatcher implements IBillCalculateDispatcher<String>{
	
	private ICalculateControlFactory calculateFactory;
	
	//精准大票控制器code
	private String CONTROL_BG_CODE = "BGCONTROL";
	//精准电商控制器code
	private String CONTROL_EC_GOODS_CODE = "ECGOODSCONTROL";

	@Override
	public ICalculateControl dispatch(
			String productCode) {
		System.out.println("调度器执行正常...");
		ICalculateControl control = null;
		if("BGFLF".equals(productCode) 
				|| "BGLRF".equals(productCode)
				|| "BGFSF".equals(productCode)
				|| "BGSRF".equals(productCode)
				|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR.equals(productCode)
				|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD.equals(productCode)){
			control = calculateFactory.getControlByCode(CONTROL_BG_CODE);
		}
		if("PCP".equals(productCode)){
			control = calculateFactory.getControlByCode(CONTROL_EC_GOODS_CODE);
		}
		return control;
	}

	public ICalculateControlFactory getCalculateFactory() {
		return calculateFactory;
	}

	public void setCalculateFactory(ICalculateControlFactory calculateFactory) {
		this.calculateFactory = calculateFactory;
	}

}

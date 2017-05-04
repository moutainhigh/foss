package com.deppon.foss.module.pickup.pricing.server.calculateservice.control.factory;

import com.deppon.foss.module.pickup.pricing.server.calculateservice.control.ICalculateControl;


public interface ICalculateControlFactory {
	
	public ICalculateControl getControlByCode(String controlCode);

}

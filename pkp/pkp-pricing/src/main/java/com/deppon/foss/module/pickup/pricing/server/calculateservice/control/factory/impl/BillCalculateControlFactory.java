package com.deppon.foss.module.pickup.pricing.server.calculateservice.control.factory.impl;

import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.module.pickup.pricing.server.calculateservice.control.ICalculateControl;
import com.deppon.foss.module.pickup.pricing.server.calculateservice.control.factory.ICalculateControlFactory;

public class BillCalculateControlFactory implements ICalculateControlFactory{
	
	private Map<String,ICalculateControl> controlMap = new HashMap<String,ICalculateControl>();
	
	@Override
	public ICalculateControl getControlByCode(String controlCode) {
		ICalculateControl control = controlMap.get(controlCode);
		return control;
	}

	public Map<String, ICalculateControl> getControlMap() {
		return controlMap;
	}

	public void setControlMap(Map<String, ICalculateControl> controlMap) {
		this.controlMap = controlMap;
	}

}

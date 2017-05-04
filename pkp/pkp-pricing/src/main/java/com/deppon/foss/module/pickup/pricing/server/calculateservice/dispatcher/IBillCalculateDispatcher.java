package com.deppon.foss.module.pickup.pricing.server.calculateservice.dispatcher;

import com.deppon.foss.module.pickup.pricing.server.calculateservice.control.ICalculateControl;


public interface IBillCalculateDispatcher<T> {
	public ICalculateControl dispatch(T t);

}

package com.deppon.foss.module.pickup.pricing.server.calculateservice.executor;

public interface ICalculateExecutor<T,U> {
	
	public T execute(U u);
	
}

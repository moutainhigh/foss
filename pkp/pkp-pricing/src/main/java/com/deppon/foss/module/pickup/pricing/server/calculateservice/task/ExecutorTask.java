package com.deppon.foss.module.pickup.pricing.server.calculateservice.task;

import java.util.concurrent.Callable;

import com.deppon.foss.module.pickup.pricing.server.calculateservice.executor.ICalculateExecutor;

/**
 * 多线程任务
 * @author 157229-zxy
 *
 */
public class ExecutorTask<T,U> implements Callable<T>{

	private ICalculateExecutor<T,U> executor;
	private U param;
	
	public ExecutorTask(ICalculateExecutor<T,U> executor,U u){
		this.executor = executor;
		this.param = u;
	}
	
	@Override
	public T call() throws Exception {
		return (T)executor.execute(param);
	}
	
}

package com.deppon.foss.module.transfer.lostwarning.api.shared.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池类
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：ThreadPool
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-11 上午10:21:51
 * 
 * 版权所有：上海德邦物流有限公司
 */
public class ThreadPool extends ThreadPoolExecutor {
	
	public ThreadPool(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		//System.out.println("Perform beforeExecute() logic");
	}
	
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		if(t != null)
        {
            System.err.println("Perform exception handler logic");
        }
        //System.out.println("Perform afterExecute() logic");
	}
	
}  
	


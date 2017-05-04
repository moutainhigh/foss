package com.deppon.foss.module.pickup.predeliver.api.shared.tools;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.deppon.foss.framework.server.components.logger.exception.BufferedStateException;


public abstract class DeliverTheadPool  implements  InitializingBean,
		DisposableBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeliverTheadPool.class);
    
	/**
	 * 初始线程数，默认为单线程处理
	 */
	private int initThreads =1;
	private ExecutorService threadPool = null;
	private static final long MILLISENDS = 1000l;
	@Override
	public void destroy() {
		/**
		 * 关闭线程池
		 */
		threadPool.shutdown();
		// 确保线程池已经关闭
		/**
		 * 循环：若线程池未完全关闭，则等待1000ms，使得线程池完全关闭
		 */
		while (!threadPool.isTerminated()) {
			try {
				synchronized (this) {
					this.wait(MILLISENDS);
				}
			} catch (InterruptedException e) {
				throw new BufferedStateException(e.getMessage());
			}
		}

	}

	@Override
	public void afterPropertiesSet() {
		/*
		 * 发送任务的线程池 最少线程数：threads 最大线程数：threads 任务缓冲队列:固定为维护线程数的5倍 拒绝任务处理策略：
		 * abort，并抛出异常（开发自定义）
		 */
		initThreads = getActiveThreads();
		if(threadPool == null){
			//zxy 20140723  修改：队列修改成20倍数
			threadPool = new ThreadPoolExecutor(initThreads, initThreads, 3,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
						20 * initThreads),new DeliverThreadAborPolicy(){
			@Override
			public void outOfPool(Object obj) {
				outOfOrderPool(obj);
			}
		});
		}
	}
	

	/**
	 * params:obj 每个线程要处理的单位数据
	 * 功能：创建线程并且加入线程池中
	 * */
	public void pushThreadsPool(Object obj){
		threadPool.submit(new SendTask(obj) );
		try {
			//每启动一个线程休眠0.002s
			Thread.currentThread();
			Thread.sleep(2l);
		} catch (InterruptedException e) {
			LOGGER.info("线程池休眠失败："+e.getMessage());
		}
		}
	
	 class SendTask implements Runnable {		
		Object obj;			
		SendTask(Object obj){
			this.obj = obj;
		}
		@Override
		public void run() {
			try{
			businessExecutor(obj);
			}catch(Exception e ){
				e.printStackTrace();
			}
		}
	}
	
     /**
      * 
      * @param obj 线程处理单位
      */
	public abstract void businessExecutor(Object obj);
	
	/**
	 * 功能：超出线程池数量时处理方式
	 * */
	public abstract void outOfOrderPool(Object obj);
	/**
	 * 功能：获得最大线程数
	 * */
	public abstract int getActiveThreads();
	

}

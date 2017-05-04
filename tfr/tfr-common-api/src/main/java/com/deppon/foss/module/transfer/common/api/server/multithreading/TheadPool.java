package com.deppon.foss.module.transfer.common.api.server.multithreading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.deppon.foss.framework.server.components.logger.exception.BufferedStateException;
import com.deppon.foss.module.transfer.common.api.shared.define.TransferConstants;


public abstract class TheadPool implements InitializingBean, DisposableBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(TheadPool.class);
	/**
	 * 初始线程数，默认为单线程处理
	 */
	private int initThreads =1;
	private ExecutorService threadPool = null;
	private static final long MILLISENDS = 1000l;

	@Override
	public void destroy() throws Exception {
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
	public void afterPropertiesSet() throws Exception {
		/*
		 * 发送任务的线程池 最少线程数：threads 最大线程数：threads 任务缓冲队列:固定为维护线程数的5倍 拒绝任务处理策略：
		 * abort，并抛出异常（开发自定义）
		 */
		initThreads = getActiveThreads();
		if(threadPool == null){
			threadPool = new ThreadPoolExecutor(initThreads, initThreads, TransferConstants.SONAR_NUMBER_3,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>( initThreads),
				new ThreadAborPolicy(){
							@Override
						public void outOfPool(Object obj) {
							outOfUnloadPool(obj);
						}
				});
			}
	}
	
	
	/**
	 * 
	 * <p>obj 每个线程要处理的单位数据</p> 
	 * 功能：创建线程并且加入线程池中
	 * @author alfred
	 * @date 2015-4-27 下午7:38:01
	 * @param obj
	 * @see
	 */
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
	
	
	/**
	 * 
	 * <p>线程内部类，执行业务逻辑</p> 
	 * @author alfred
	 * @date 2015-4-27 下午7:36:50
	 * @see
	 */
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
					LOGGER.info("业务处理失败！错误："+e.toString());
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
	public abstract void outOfUnloadPool(Object obj);
	/**
	 * 功能：获得最大线程数
	 * */
	public abstract int getActiveThreads();

}

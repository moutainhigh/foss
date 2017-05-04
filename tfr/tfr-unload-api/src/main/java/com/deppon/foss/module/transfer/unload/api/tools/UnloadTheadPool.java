package com.deppon.foss.module.transfer.unload.api.tools;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService				;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.deppon.foss.framework.server.components.logger.exception.BufferedStateException;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PDAUnloadAsyncBillMsgDto;


public abstract class UnloadTheadPool implements  InitializingBean, DisposableBean{
	private static final Logger LOGGER = LoggerFactory.getLogger(UnloadTheadPool.class);
	/**
	 * 初始线程数，默认为单线程处理
	 */
	private int initThreads =1;
	private ExecutorService threadPool = null;
	private static final long MILLISENDS = 1000l;
	
//	//公共类
//	 ITfrCommonService  tfrCommonService;
//	public void setTfrCommonService(ITfrCommonService tfrCommonService) {
//		this.tfrCommonService = tfrCommonService;
//	}
	
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
	
	/**
	 * 线程池设置 : 
	 * 1、initThreads：中所保存的线程数，包括空闲线程 ；  
	 * 2、initThreads：线程数最大上限 ；
	 * 3、 3：当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
	 * 4、 TimeUnit.SECONDS：等待新任务的最长时间的类型
	 * 5、ArrayBlockingQueue：执行前用于保持任务的队列。此队列仅保持由 execute方法提交的 Runnable任务
	 * 6、20 * initThreads：固定大小线程池
	 * 7、UnloadThreadAborPolicy ：由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序
	 * */
	@Override
	public void afterPropertiesSet() {

		/*
		 * 发送任务的线程池 最少线程数：threads 最大线程数：threads 任务缓冲队列:固定为维护线程数的5倍 拒绝任务处理策略：
		 * abort，并抛出异常（开发自定义）
		 */
		initThreads = getActiveThreads();
		if(threadPool == null){
			threadPool = new ThreadPoolExecutor(initThreads, initThreads, UnloadConstants.SONAR_NUMBER_3,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>( initThreads),
				new UnloadThreadAborPolicy(){
							@Override
						public void outOfPool(Object obj) {
							outOfUnloadPool(obj);
						}
				});
			}
	
	
	}
	

	/**
	 * params:obj 每个线程要处理的单位数据
	 * 功能：创建线程并且加入线程池中
	 * @author 105869
	 * @date 2015年3月21日 14:08:40
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
	
	/**
	 * 线程内部类，执行业务逻辑
	 * @author 105869-heyongdong
	 * @date 2015年3月21日 14:08:16
	 *
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
				List<PDAUnloadAsyncBillMsgDto>  inStockEntitys =(List<PDAUnloadAsyncBillMsgDto>) obj;
				if(CollectionUtils.isNotEmpty(inStockEntitys) && inStockEntitys.size()>0){
					//运单号
					String waybillNo = inStockEntitys.get(0).getWayBillNo();
					LOGGER.info("运单："+waybillNo+"入库失败！错误："+e.toString());

				}
				
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

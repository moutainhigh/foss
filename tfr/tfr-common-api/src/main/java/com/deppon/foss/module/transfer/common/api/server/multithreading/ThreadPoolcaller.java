package com.deppon.foss.module.transfer.common.api.server.multithreading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public abstract class ThreadPoolcaller implements InitializingBean {

	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadPoolcaller.class);
	
	private int maxOpentimes = 0;
	//主线程休眠时间
	private long sleepTime=1l;
	//无数据是线程休眠时间
	private long noDataSleepTime = 1000l;
	//确定休眠时间
	private static final long MILLISENDS = 1000l;
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//创建并启动线程
		StartThread startThead = new StartThread();
		startThead.start();

	}
	
	//创建启动线程类
	class StartThread extends Thread {
			@Override
			public void run() {
				super.run();
				try {
					reSetDate();
					//循环执行
					while (true) {
					  noDataSleepTime = getNoDataSleepTime();
					  sleepTime=getSleepTime();
					  LOGGER.info("noDataSleepTime:"+noDataSleepTime+",sleepTime"+sleepTime);
						//执行方法
					  int executNo = serviceCaller();
					  //每次执行休眠 设定时间
					  Thread.currentThread();
					  Thread.sleep(sleepTime);
					  //如果没有处理的数据 线程休眠5秒
					  if(executNo <= 0){
						 Thread.currentThread();
						 Thread.sleep(noDataSleepTime);
					  } 
					}
				} catch (Exception e) {
					//实现递归调用，防止主线程挂掉
					LOGGER.info("线程调用者报错，异常信息为："+e.getMessage());
					Thread.currentThread();
					try {
						Thread.sleep(getMaxOpentimes()*MILLISENDS);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
						this.run();	
					}
					maxOpentimes++;
					this.run();	
				}
			}
			
		}
		
		public abstract int serviceCaller();
		
		public int getMaxOpentimes() {
			return maxOpentimes;
		}

		public void setMaxOpentimes(int maxOpentimes) {
			this.maxOpentimes = maxOpentimes;
		}
		/**
		 * 功能：获取等待时间
		 * */
		public abstract long getSleepTime();
		
		/**
		 * 还原异常数据 重置站位符
		 * */
		public abstract void reSetDate();
		
		/**
		 * 无数据是线程休眠时间
		 */
		public abstract long getNoDataSleepTime();
}

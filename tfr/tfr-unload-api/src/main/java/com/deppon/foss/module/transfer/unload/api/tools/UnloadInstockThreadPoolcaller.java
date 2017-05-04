package com.deppon.foss.module.transfer.unload.api.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;



public abstract class UnloadInstockThreadPoolcaller implements InitializingBean {
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UnloadInstockThreadPoolcaller.class);
	
	private int maxOpentimes = 0;
	//主线程休眠时间
	private long sleepTime=1l;
	//TODO 确定时间用途
	private static final long MILLISENDS = 1000l;
	
	
	/**
	 * @descriptionx:卸车入库运单单线程 
	 * @author 105869-heyongdong
	 * @date 2015年3月21日 10:59:49
	 */
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
				sleepTime=getSleepTime();
				//循环执行
				while (true) {
					//执行方法
				  int executNo = serviceCaller();
				  //每次执行休眠 设定时间
				  Thread.currentThread();
				  Thread.sleep(sleepTime);
				  //如果没有处理的数据 线程休眠5秒
				  if(executNo <= 0){
					 Thread.currentThread();
					 Thread.sleep(MILLISENDS*UnloadConstants.SONAR_NUMBER_10);
				  } 
				}
			} catch (Exception e) {
				Thread.currentThread();
				try {
					Thread.sleep(getMaxOpentimes()*MILLISENDS);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					this.run();	
				}
				maxOpentimes++;
				this.run();	
				//实现递归调用，防止主线程挂掉
				LOGGER.info("线程调用者报错，异常信息为："+e.getMessage());
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
}

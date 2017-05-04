package com.deppon.foss.module.pickup.predeliver.api.shared.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @description 自动车辆排班信息更新---单个线程执行规则
 * @author 269044
 * @date 2015-10-28
 */
public abstract class EditVehicleSchedulingThreadPoolCaller implements InitializingBean {	
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(EditVehicleSchedulingThreadPoolCaller.class);
	
	private int maxOpentimes = 0;
	private static final long MILLISENDS = 1000l;

	/**
	 * @description:自动调用
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
				//循环执行
				while (true) {
				 int executNo =	serviceCaller();
				if(executNo<= 0){
					Thread.currentThread().sleep(MILLISENDS*30);
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

}

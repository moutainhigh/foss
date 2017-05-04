package com.deppon.foss.module.transfer.taobao.server.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.common.api.server.multithreading.ThreadPoolcaller;
import com.deppon.foss.module.transfer.common.api.server.service.IPushLoadToWkService;

/**
 * <pre>
 * 2016年8月23日 10:38:50
 * IMPORTANT! 给悟空系统的提交装车任务已经改为同步方式，该类不会被调用。
 * 已从expressExternalSpring.xml中删掉对其的注入。
 * </pre>
 * @author 335284
 *
 */
public class PushCreatLoadToWKJob extends ThreadPoolcaller {

	private IPushLoadToWkService pushLoadToWkService;
	private static final Logger LOGGER = LoggerFactory.getLogger(PushCreatLoadToWKJob.class);


	@Override
	public int serviceCaller() {
		LOGGER.info("开始执行任务...");
		pushLoadToWkService.ThreadsPool(null);
		return 0;
	}

	@Override
	public long getSleepTime() {
		// 设置线程执行休眠时间1s
		long sleepTime = 1000l;
		return sleepTime;
	}

	@Override
	public void reSetDate() {

	}

	@Override
	public long getNoDataSleepTime() {
		// 设置线程执行休眠时间1min
		long sleepTime = 60000l;
		return sleepTime;
	}

	public void setPushLoadToWkService(IPushLoadToWkService pushLoadToWkService) {
		this.pushLoadToWkService = pushLoadToWkService;
	}
}

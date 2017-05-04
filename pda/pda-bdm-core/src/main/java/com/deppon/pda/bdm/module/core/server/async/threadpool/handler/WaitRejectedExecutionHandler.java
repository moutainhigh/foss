package com.deppon.pda.bdm.module.core.server.async.threadpool.handler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

/**
 * 线程池满拒绝处理异常处理器
 * 当报RejectedException时，等待一段时间后重新处理。
 * @author wanghongling
 * @date 2012-10-18
 * @version 1.0
 *
 */
public class WaitRejectedExecutionHandler implements RejectedExecutionHandler {
	private Logger log = Logger.getLogger(getClass());
	private int waitTime = 1000;

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		try {
			Thread.sleep(waitTime);
			executor.execute(r);
		} catch (InterruptedException e) {
			log.warn("[class:WaitRejectedExecutionHandler] thread sleep err:"+e.getMessage());
		}
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

}

package com.deppon.pda.bdm.module.core.server.async.jobSecond;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;
import com.deppon.pda.bdm.module.core.server.async.queue.second.IAsyncDataSecondQueue;
import com.deppon.pda.bdm.module.core.server.async.service.job.IAsyncDataReadSecondService;
import com.deppon.pda.bdm.module.core.server.async.util.PropertiesUtil;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;

public class AsyncDataReadSecondJob<T extends QueueMessage> {

	private static final Logger log = Logger.getLogger(AsyncDataReadSecondJob.class);

	private int minQueueSize = 100;
	private int perReadSize = 50;
	private long lastActiveTime;
	private int sleepTime = 30000;

	private Thread thread;
	private IAsyncDataReadSecondService<T> asyncDataReadService;
	private IAsyncDataSecondQueue<T> queue;
	private boolean initedDB = false;

	private long currentThreadId;

	private long lastInitTime = 0;

	protected void init(Properties prop) {
		minQueueSize = PropertiesUtil.getInt(prop, "minQueueSize", 100);
		perReadSize = PropertiesUtil.getInt(prop, "perReadSize", 50);
		sleepTime = PropertiesUtil.getInt(prop, "sleepTime", 30000);

	}

	public void start() {
		log.info("[AsyncDataReadJob]start");
		if (thread != null && !isActive()) {
			log.info("[AsyncDataReadJob]AsyncDataReadJob is not active and stop it!");
			stop();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.warn("[class:AsyncDataReadJob] thread sleep err:"
						+ e.getMessage());
			}
		}
		thread = new Thread(new Runnable() {
			// long start;
			@Override
			public void run() {
				currentThreadId = Thread.currentThread().getId();
				while (currentThreadId == Thread.currentThread().getId()) {
					try {
						lastActiveTime = System.currentTimeMillis();
						if (!isRequestRead()) {
							try {
								log.info("[asyncinfo][job]queueSecond.size()>minQueueSize :"
										+ queue.size());
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								log.warn("[class:AsyncDataReadJob] thread sleep err:"
										+ e.getMessage());
							}
							continue;
						}
						if (lastInitTime == 0
								|| ((System.currentTimeMillis() - lastInitTime) > 3600000
										&& !initedDB && isQueueEmpty())) {
							long initTime = System.currentTimeMillis();
							asyncDataReadService.initAsyncDataStatus(queue
									.getQueueId());
							log.info("[asyncinfo][job]initAsyncDataStatus:"
									+ (System.currentTimeMillis() - initTime));
							initedDB = true;
							lastInitTime = System.currentTimeMillis();
						}
						long start = System.currentTimeMillis();
						List<T> queueMsgs = asyncDataReadService.readAsyncData(
								perReadSize, queue.getQueueId());
						log.info("[asyncinfo][job]message size:"
								+ queueMsgs.size() + "queueSecond size:"
								+ queue.size());
						long readTime = System.currentTimeMillis() - start;
						log.info("[asyncinfo][job]消息读取时间：" + readTime);
						QueueMonitorSecondInfo.addReadCount();
						QueueMonitorSecondInfo.addTotalReadTime(readTime);
						if (queueMsgs.isEmpty()) {
							try {
								Thread.sleep(sleepTime);
							} catch (InterruptedException e) {
								log.warn("[class:AsyncDataReadJob] thread sleep err:"
										+ e.getMessage());
							}
						} else {
							QueueMonitorSecondInfo.addActiveReadCount();
							QueueMonitorSecondInfo.addTotalActiveReadTime(readTime);
							QueueMonitorSecondInfo.addLastReadTime(readTime);
							log.info("[asyncinfo][job]read size:"
									+ queueMsgs.size() + " read time:"
									+ readTime + " queueSecond size:" + queue.size()
									+ " dealing size:" + queue.dealingSize());
							start = System.currentTimeMillis();
							queue.put(queueMsgs);
							initedDB = false;
							QueueMonitorSecondInfo.addPutCount();
							QueueMonitorSecondInfo.addTotalPutTime(System
									.currentTimeMillis() - start);
						}
					} catch (Exception e) {
						log.error(LogUtil.logFormat(e));
						try {
							Thread.sleep(sleepTime);
						} catch (InterruptedException e1) {
							log.warn("[class:AsyncDataReadJob] thread sleep err:"
									+ e1.getMessage());
						}
					}
				}
			}
		});
		thread.start();
	}

	public boolean isActive() {
		return (System.currentTimeMillis() - lastActiveTime) / 1000 < 600;
	}

	public void stop() {
		currentThreadId = 0;
	}

	private boolean isRequestRead() {
		return queue.size() <= minQueueSize;
	}

	private boolean isQueueEmpty() {
		return queue.size() == 0 && queue.dealingSize() == 0;
	}

	public int getMinQueueSize() {
		return minQueueSize;
	}

	public void setMinQueueSize(int minQueueSize) {
		this.minQueueSize = minQueueSize;
	}

	public int getPerReadSize() {
		return perReadSize;
	}

	public void setPerReadSize(int perReadSize) {
		this.perReadSize = perReadSize;
	}


	public IAsyncDataReadSecondService<T> getAsyncDataReadSecondService() {
		return asyncDataReadService;
	}

	public void setAsyncDataReadSecondService(
			IAsyncDataReadSecondService<T> asyncDataReadService) {
		this.asyncDataReadService = asyncDataReadService;
	}

	public int getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}

	public IAsyncDataSecondQueue<T> getQueue() {
		return queue;
	}

	public void setQueue(IAsyncDataSecondQueue<T> queue) {
		this.queue = queue;
	}


}

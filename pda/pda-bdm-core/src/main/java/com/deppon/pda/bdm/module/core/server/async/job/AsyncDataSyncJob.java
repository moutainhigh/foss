package com.deppon.pda.bdm.module.core.server.async.job;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.deppon.pda.bdm.module.core.server.async.queue.IAsyncDataQueue;
import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;

public class AsyncDataSyncJob<T extends QueueMessage> {
	private Logger log = Logger.getLogger(getClass());
	private ThreadPoolTaskExecutor threadPoolExecutor;
	private AsyncDataSyncExecutor<T> syncExceutor;
	private IAsyncDataQueue<T> queue;
	private long lastActiveTime;
	private Thread thread;
	
	public void start(){
		log.info("[AsyncDataSyncJob]start");
		if(thread!=null && !isActive()){
			log.info("[AsyncDataSyncJob]AsyncDataSyncJob is not active and interrupt!");
			thread.interrupt();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.warn("[class:AsyncDataSyncJob] thread sleep err:"+e.getMessage());
			}
		}
		thread = new Thread(new Runnable() {
			long start;
			@Override
			public void run() {
				while(true){
					try{
						lastActiveTime = System.currentTimeMillis();
						if(!hasMsg()){
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								log.warn("[class:AsyncDataSyncJob] thread sleep err:"+e.getMessage());
							}
							continue;
						}
						threadPoolExecutor.execute(syncExceutor);
					}catch (Exception e) {
						log.error(LogUtil.logFormat(e));
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e1) {
							log.warn("[class:AsyncDataReadJob] thread sleep err:"+e1.getMessage());
						}
					}
				}
			}
		});
		thread.start();
	}
	
	public boolean isActive(){
		return (System.currentTimeMillis()-lastActiveTime)/1000<60;
	}
	
	private boolean hasMsg(){
		return queue.size()>0;
	}
	
	public ThreadPoolTaskExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}
	public void setThreadPoolExecutor(ThreadPoolTaskExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}
	public AsyncDataSyncExecutor<T> getSyncExceutor() {
		return syncExceutor;
	}
	public void setSyncExceutor(AsyncDataSyncExecutor<T> syncExceutor) {
		this.syncExceutor = syncExceutor;
	}

	public IAsyncDataQueue<T> getQueue() {
		return queue;
	}

	public void setQueue(IAsyncDataQueue<T> queue) {
		this.queue = queue;
	}
}

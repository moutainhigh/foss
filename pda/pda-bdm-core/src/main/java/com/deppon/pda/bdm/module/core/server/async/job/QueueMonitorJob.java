package com.deppon.pda.bdm.module.core.server.async.job;

import org.apache.log4j.Logger;

import com.deppon.pda.bdm.module.core.server.async.queue.IAsyncDataQueue;
import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;

public class QueueMonitorJob<T extends QueueMessage> {
	private IAsyncDataQueue<T> queue;
	private long queueMonitorJobSleepTime = 60000;
	private Logger log = Logger.getLogger(getClass());
	private Thread thread;
	
	public void start() {

		if(thread!=null){
			thread.interrupt();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.warn("[class:AsyncDataSyncJob] thread sleep err:"+e.getMessage());
			}
		}
		thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try{
						try {
							Thread.sleep(queueMonitorJobSleepTime);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						log.info("[QueueMonitorJob]==============================================================");
						log.info("[QueueMonitorJob]queue size:"+queue.size()+" dealing size:"+queue.dealingSize());
				
						log.info("[QueueMonitorJob]**************************************************************");
						log.info("[QueueMonitorJob]read count:"+QueueMonitorInfo.readCount);
						log.info("[QueueMonitorJob]read total time"+QueueMonitorInfo.totalReadTime);
						log.info("[QueueMonitorJob]read average time:"+QueueMonitorInfo.getAverageReadTime());
						log.info("[QueueMonitorJob]read active count:"+QueueMonitorInfo.activeReadCount);
						log.info("[QueueMonitorJob]read active total time:"+QueueMonitorInfo.totalActiveReadTime);
						log.info("[QueueMonitorJob]read active average time:"+QueueMonitorInfo.getAcerageActiveReadTime());
						log.info("[QueueMonitorJob]read last time:"+QueueMonitorInfo.lastReadTime);
				
						log.info("[QueueMonitorJob]**************************************************************");
						log.info("[QueueMonitorJob]put count:"+QueueMonitorInfo.putCount);
						log.info("[QueueMonitorJob]put total time:"+QueueMonitorInfo.totalPutTime);
						log.info("[QueueMonitorJob]put average time:"+QueueMonitorInfo.getAveragePutTime());
				
						log.info("[QueueMonitorJob]**************************************************************");
						log.info("[QueueMonitorJob]get count:"+QueueMonitorInfo.getCount);
						log.info("[QueueMonitorJob]get total time"+QueueMonitorInfo.totalGetTime);
						log.info("[QueueMonitorJob]get average time:"+QueueMonitorInfo.getAverageGetTime());
						log.info("[QueueMonitorJob]get active count:"+QueueMonitorInfo.activeGetCount);
						log.info("[QueueMonitorJob]get active total time:"+QueueMonitorInfo.totalActiveGetTime);
						log.info("[QueueMonitorJob]get active average time:"+QueueMonitorInfo.getAcerageActiveGetTime());
				
						log.info("[QueueMonitorJob]**************************************************************");
						log.info("[QueueMonitorJob]sync count:"+QueueMonitorInfo.syncCount);
						log.info("[QueueMonitorJob]sync total time:"+QueueMonitorInfo.totalSyncTime);
						log.info("[QueueMonitorJob]sync totalFoss time:"+QueueMonitorInfo.totalFossTime);
						log.info("[QueueMonitorJob]sync average time:"+QueueMonitorInfo.getAverageSyncTime());
				
						log.info("[QueueMonitorJob]**************************************************************");
						log.info("[QueueMonitorJob]commit count:"+QueueMonitorInfo.commitTime);
						log.info("[QueueMonitorJob]commit total time:"+QueueMonitorInfo.totalCommitTime);
						log.info("[QueueMonitorJob]commit average time:"+QueueMonitorInfo.getAverageCommitTime());
						log.info("[QueueMonitorJob]==============================================================");
						QueueMonitorInfo.clean();
					}catch (Exception e) {
						log.error(LogUtil.logFormat(e));
					}
				}
				
			}
		});
		thread.start();
	}

	public IAsyncDataQueue<T> getQueue() {
		return queue;
	}

	public void setQueue(IAsyncDataQueue<T> queue) {
		this.queue = queue;
	}

	public long getQueueMonitorJobSleepTime() {
		return queueMonitorJobSleepTime;
	}

	public void setQueueMonitorJobSleepTime(long queueMonitorJobSleepTime) {
		this.queueMonitorJobSleepTime = queueMonitorJobSleepTime;
	}
	
}

package com.deppon.pda.bdm.module.core.server.async.jobSecond;

import org.apache.log4j.Logger;

import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;
import com.deppon.pda.bdm.module.core.server.async.queue.second.IAsyncDataSecondQueue;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;

public class QueueMonitorSecondJob<T extends QueueMessage> {
	private IAsyncDataSecondQueue<T> queueSecond;
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
						log.info("[QueueMonitorJob]queueSecond size:"+queueSecond.size()+" dealing size:"+queueSecond.dealingSize());
				
						log.info("[QueueMonitorJob]**************************************************************");
						log.info("[QueueMonitorJob]read count:"+QueueMonitorSecondInfo.readCount);
						log.info("[QueueMonitorJob]read total time"+QueueMonitorSecondInfo.totalReadTime);
						log.info("[QueueMonitorJob]read average time:"+QueueMonitorSecondInfo.getAverageReadTime());
						log.info("[QueueMonitorJob]read active count:"+QueueMonitorSecondInfo.activeReadCount);
						log.info("[QueueMonitorJob]read active total time:"+QueueMonitorSecondInfo.totalActiveReadTime);
						log.info("[QueueMonitorJob]read active average time:"+QueueMonitorSecondInfo.getAcerageActiveReadTime());
						log.info("[QueueMonitorJob]read last time:"+QueueMonitorSecondInfo.lastReadTime);
				
						log.info("[QueueMonitorJob]**************************************************************");
						log.info("[QueueMonitorJob]put count:"+QueueMonitorSecondInfo.putCount);
						log.info("[QueueMonitorJob]put total time:"+QueueMonitorSecondInfo.totalPutTime);
						log.info("[QueueMonitorJob]put average time:"+QueueMonitorSecondInfo.getAveragePutTime());
				
						log.info("[QueueMonitorJob]**************************************************************");
						log.info("[QueueMonitorJob]get count:"+QueueMonitorSecondInfo.getCount);
						log.info("[QueueMonitorJob]get total time"+QueueMonitorSecondInfo.totalGetTime);
						log.info("[QueueMonitorJob]get average time:"+QueueMonitorSecondInfo.getAverageGetTime());
						log.info("[QueueMonitorJob]get active count:"+QueueMonitorSecondInfo.activeGetCount);
						log.info("[QueueMonitorJob]get active total time:"+QueueMonitorSecondInfo.totalActiveGetTime);
						log.info("[QueueMonitorJob]get active average time:"+QueueMonitorSecondInfo.getAcerageActiveGetTime());
				
						log.info("[QueueMonitorJob]**************************************************************");
						log.info("[QueueMonitorJob]sync count:"+QueueMonitorSecondInfo.syncCount);
						log.info("[QueueMonitorJob]sync total time:"+QueueMonitorSecondInfo.totalSyncTime);
						log.info("[QueueMonitorJob]sync totalFoss time:"+QueueMonitorSecondInfo.totalFossTime);
						log.info("[QueueMonitorJob]sync average time:"+QueueMonitorSecondInfo.getAverageSyncTime());
				
						log.info("[QueueMonitorJob]**************************************************************");
						log.info("[QueueMonitorJob]commit count:"+QueueMonitorSecondInfo.commitTime);
						log.info("[QueueMonitorJob]commit total time:"+QueueMonitorSecondInfo.totalCommitTime);
						log.info("[QueueMonitorJob]commit average time:"+QueueMonitorSecondInfo.getAverageCommitTime());
						log.info("[QueueMonitorJob]==============================================================");
						QueueMonitorSecondInfo.clean();
					}catch (Exception e) {
						log.error(LogUtil.logFormat(e));
					}
				}
				
			}
		});
		thread.start();
	}


	public IAsyncDataSecondQueue<T> getQueueSecond() {
		return queueSecond;
	}



	public void setQueueSecond(IAsyncDataSecondQueue<T> queueSecond) {
		this.queueSecond = queueSecond;
	}



	public long getQueueMonitorJobSleepTime() {
		return queueMonitorJobSleepTime;
	}

	public void setQueueMonitorJobSleepTime(long queueMonitorJobSleepTime) {
		this.queueMonitorJobSleepTime = queueMonitorJobSleepTime;
	}
	
}

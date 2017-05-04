package com.deppon.pda.bdm.module.core.server.async.jobSecond;

import org.apache.log4j.Logger;

public class AsyncDataSyncMonitorSecondJob {
	
	private static AsyncDataReadSecondJob<?> asyncDataReadSecondJob;
	private static AsyncDataSyncSecondJob<?> asyncDataSyncSecondJob;
	private static QueueMonitorSecondJob<?> queueMonitorSecondJob;
	
	private IAsyncDataSyncSecondFactory<?> asyncDataSyncSecondFactory;
	private Logger log = Logger.getLogger(getClass());
	
	public void start() {
		log.info("[AsyncDataSyncMonitorJob]start ----------  Second start--------- ");
		if(asyncDataReadSecondJob == null){
			log.info("[AsyncDataSyncMonitorJob]asyncDataReadJobSecond is null-----------second is null---------------");
			asyncDataReadSecondJob = asyncDataSyncSecondFactory.getAsyncDataReadSecondJob();
			log.info("[AsyncDataSyncMonitorJob]asyncDataReadJobSecond init---------second init --------------");
		}
		
		if(!asyncDataReadSecondJob.isActive()){
			log.info("[AsyncDataSyncMonitorJob]asyncDataReadJobSecond is not active ----------second actice----------");
			asyncDataReadSecondJob.start();
			log.info("[AsyncDataSyncMonitorJob]asyncDataReadJobSecond start");
		}
		
		if(asyncDataSyncSecondJob == null){
			log.info("[AsyncDataSyncMonitorJob]asyncDataSyncJobSecond is null -------------second   is null----------------");
			asyncDataSyncSecondJob = asyncDataSyncSecondFactory.getAsyncDataSyncSecondJob();
			log.info("[AsyncDataSyncMonitorJob]asyncDataSyncJobSecond init");
		}

		if(!asyncDataSyncSecondJob.isActive()){
			log.info("[AsyncDataSyncMonitorJob]asyncDataSyncJobSecond is not active----------second   is not active--------");
			asyncDataSyncSecondJob.start();
			log.info("[AsyncDataSyncMonitorJob]asyncDataSyncJobSecond start-----------second   start--------------");
		}
		
		if(queueMonitorSecondJob==null){
			queueMonitorSecondJob = asyncDataSyncSecondFactory.getQueueMonitorSecondJob();
			queueMonitorSecondJob.start();
		}
		log.info("[AsyncDataSyncMonitorJob]end ----------second end ---------------");
	}

	public IAsyncDataSyncSecondFactory<?> getAsyncDataSyncSecondFactory() {
		return asyncDataSyncSecondFactory;
	}

	public void setAsyncDataSyncSecondFactory(
			IAsyncDataSyncSecondFactory<?> asyncDataSyncSecondFactory) {
		this.asyncDataSyncSecondFactory = asyncDataSyncSecondFactory;
	}

	
}

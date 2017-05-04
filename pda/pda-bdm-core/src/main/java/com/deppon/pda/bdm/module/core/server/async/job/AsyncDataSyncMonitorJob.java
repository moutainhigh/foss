package com.deppon.pda.bdm.module.core.server.async.job;

import org.apache.log4j.Logger;

public class AsyncDataSyncMonitorJob {
	
	private static AsyncDataReadJob<?> asyncDataReadJob;
	private static AsyncDataSyncJob<?> asyncDataSyncJob;
	private static QueueMonitorJob<?> queueMonitorJob;
	
	private IAsyncDataSyncFactory<?> asyncDataSyncFactory;
	private Logger log = Logger.getLogger(getClass());
	
	public void start() {
		log.info("[AsyncDataSyncMonitorJob]start");
		if(asyncDataReadJob == null){
			log.info("[AsyncDataSyncMonitorJob]asyncDataReadJob is null");
			asyncDataReadJob = asyncDataSyncFactory.getAsyncDataReadJob();
			log.info("[AsyncDataSyncMonitorJob]asyncDataReadJob init");
		}
		
		if(!asyncDataReadJob.isActive()){
			log.info("[AsyncDataSyncMonitorJob]asyncDataReadJob is not active");
			asyncDataReadJob.start();
			log.info("[AsyncDataSyncMonitorJob]asyncDataReadJob start");
		}
		
		if(asyncDataSyncJob == null){
			log.info("[AsyncDataSyncMonitorJob]asyncDataSyncJob is null");
			asyncDataSyncJob = asyncDataSyncFactory.getAsyncDataSyncJob();
			log.info("[AsyncDataSyncMonitorJob]asyncDataSyncJob init");
		}

		if(!asyncDataSyncJob.isActive()){
			log.info("[AsyncDataSyncMonitorJob]asyncDataSyncJob is not active");
			asyncDataSyncJob.start();
			log.info("[AsyncDataSyncMonitorJob]asyncDataSyncJob start");
		}
		
		if(queueMonitorJob==null){
			queueMonitorJob = asyncDataSyncFactory.getQueueMonitorJob();
			queueMonitorJob.start();
		}
		log.info("[AsyncDataSyncMonitorJob]end");
	}

	public IAsyncDataSyncFactory<?> getAsyncDataSyncFactory() {
		return asyncDataSyncFactory;
	}

	public void setAsyncDataSyncFactory(
			IAsyncDataSyncFactory<?> asyncDataSyncFactory) {
		this.asyncDataSyncFactory = asyncDataSyncFactory;
	}
	
}

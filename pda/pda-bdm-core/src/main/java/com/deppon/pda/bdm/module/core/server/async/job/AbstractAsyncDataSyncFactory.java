package com.deppon.pda.bdm.module.core.server.async.job;

import java.util.Properties;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.deppon.pda.bdm.module.core.server.async.queue.IAsyncDataQueue;
import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;
import com.deppon.pda.bdm.module.core.server.async.service.IAsyncDataReadService;
import com.deppon.pda.bdm.module.core.server.async.service.IAsyncDataSyncService;

public abstract class AbstractAsyncDataSyncFactory<T extends QueueMessage> implements IAsyncDataSyncFactory<T> {
	private Properties properties;
	private IAsyncDataReadService<T> asyncDataReadService;
	private IAsyncDataSyncService<T> asyncDataSyncService;
	private ThreadPoolTaskExecutor threadPoolExecutor;
	

	@Override
	public AsyncDataReadJob<T> getAsyncDataReadJob() {
		AsyncDataReadJob<T> readJob = new AsyncDataReadJob<T>();
		readJob.init(properties);
		readJob.setAsyncDataReadService(asyncDataReadService);
		readJob.setQueue(getAsyncDataQueue());
		return readJob;
	}

	@Override
	public AsyncDataSyncJob<T> getAsyncDataSyncJob() {
		AsyncDataSyncExecutor<T> syncExecutor = new AsyncDataSyncExecutor<T>();
		syncExecutor.setQueue(getAsyncDataQueue());
		syncExecutor.setAsyncDataSyncService(asyncDataSyncService);
		
		AsyncDataSyncJob<T> syncJob = new AsyncDataSyncJob<T>();
		syncJob.setQueue(getAsyncDataQueue());
		syncJob.setSyncExceutor(syncExecutor);
		syncJob.setThreadPoolExecutor(threadPoolExecutor);
		
		return syncJob;
	}
	
	@Override
	public QueueMonitorJob<T> getQueueMonitorJob(){
		QueueMonitorJob<T> monitorJob = new QueueMonitorJob<T>();
		monitorJob.setQueue(getAsyncDataQueue());
		return monitorJob;
	}
	
	protected abstract IAsyncDataQueue<T> getAsyncDataQueue();

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public IAsyncDataReadService<T> getAsyncDataReadService() {
		return asyncDataReadService;
	}

	public void setAsyncDataReadService(
			IAsyncDataReadService<T> asyncDataReadService) {
		this.asyncDataReadService = asyncDataReadService;
	}

	public IAsyncDataSyncService<T> getAsyncDataSyncService() {
		return asyncDataSyncService;
	}

	public void setAsyncDataSyncService(
			IAsyncDataSyncService<T> asyncDataSyncService) {
		this.asyncDataSyncService = asyncDataSyncService;
	}

	public ThreadPoolTaskExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

	public void setThreadPoolExecutor(ThreadPoolTaskExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}

}

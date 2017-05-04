package com.deppon.pda.bdm.module.core.server.async.jobSecond;

import java.util.Properties;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;
import com.deppon.pda.bdm.module.core.server.async.queue.second.IAsyncDataSecondQueue;
import com.deppon.pda.bdm.module.core.server.async.service.job.IAsyncDataReadSecondService;
import com.deppon.pda.bdm.module.core.server.async.service.job.IAsyncDataSyncSecondService;


public abstract class AbstractAsyncDataSyncSecondFactory<T extends QueueMessage> implements IAsyncDataSyncSecondFactory<T> {
	private Properties properties;
	private IAsyncDataReadSecondService<T> asyncDataReadSecondService;
	private IAsyncDataSyncSecondService<T> asyncDataSyncSecondService;
	private ThreadPoolTaskExecutor threadPoolExecutor;
	

	@Override
	public AsyncDataReadSecondJob<T> getAsyncDataReadSecondJob() {
		AsyncDataReadSecondJob<T> readJob = new AsyncDataReadSecondJob<T>();
		readJob.init(properties);
		readJob.setAsyncDataReadSecondService(asyncDataReadSecondService);
		readJob.setQueue(getAsyncDataQueue());
		return readJob;
	}

	@Override
	public AsyncDataSyncSecondJob<T> getAsyncDataSyncSecondJob() {
		AsyncDataSyncSecondExecutor<T> syncExecutor = new AsyncDataSyncSecondExecutor<T>();
		syncExecutor.setSecondQueue(getAsyncDataQueue());
		syncExecutor.setAsyncDataSyncSecondService(asyncDataSyncSecondService);
		
		AsyncDataSyncSecondJob<T> syncJob = new AsyncDataSyncSecondJob<T>();
		syncJob.setQueue(getAsyncDataQueue());
		syncJob.setSyncExceutor(syncExecutor);
		syncJob.setThreadPoolExecutor(threadPoolExecutor);
		
		return syncJob;
	}
	
	@Override
	public QueueMonitorSecondJob<T> getQueueMonitorSecondJob(){
		QueueMonitorSecondJob<T> monitorJob = new QueueMonitorSecondJob<T>();
		monitorJob.setQueueSecond(getAsyncDataQueue());
		return monitorJob;
	}
	
	protected abstract IAsyncDataSecondQueue<T> getAsyncDataQueue();

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public IAsyncDataReadSecondService<T> getAsyncDataReadSecondService() {
		return asyncDataReadSecondService;
	}

	public void setAsyncDataReadSecondService(
			IAsyncDataReadSecondService<T> asyncDataReadSecondService) {
		this.asyncDataReadSecondService = asyncDataReadSecondService;
	}

	public IAsyncDataSyncSecondService<T> getAsyncDataSyncSecondService() {
		return asyncDataSyncSecondService;
	}

	public void setAsyncDataSyncSecondService(
			IAsyncDataSyncSecondService<T> asyncDataSyncSecondService) {
		this.asyncDataSyncSecondService = asyncDataSyncSecondService;
	}

	public ThreadPoolTaskExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

	public void setThreadPoolExecutor(ThreadPoolTaskExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}




}

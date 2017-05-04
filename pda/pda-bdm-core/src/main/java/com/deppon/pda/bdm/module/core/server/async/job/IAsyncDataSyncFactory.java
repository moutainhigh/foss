package com.deppon.pda.bdm.module.core.server.async.job;

import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;

public interface IAsyncDataSyncFactory<T extends QueueMessage> {
	public AsyncDataReadJob<T> getAsyncDataReadJob();
	public AsyncDataSyncJob<T> getAsyncDataSyncJob();
	public QueueMonitorJob<T> getQueueMonitorJob();
}

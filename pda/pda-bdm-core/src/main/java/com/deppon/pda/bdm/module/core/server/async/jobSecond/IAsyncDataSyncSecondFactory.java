package com.deppon.pda.bdm.module.core.server.async.jobSecond;

import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;

public interface IAsyncDataSyncSecondFactory<T extends QueueMessage> {
	public AsyncDataReadSecondJob<T> getAsyncDataReadSecondJob();
	public AsyncDataSyncSecondJob<T> getAsyncDataSyncSecondJob();
	public QueueMonitorSecondJob<T> getQueueMonitorSecondJob();
}

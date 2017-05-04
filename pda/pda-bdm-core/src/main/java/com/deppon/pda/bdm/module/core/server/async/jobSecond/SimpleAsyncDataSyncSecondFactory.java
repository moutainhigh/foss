package com.deppon.pda.bdm.module.core.server.async.jobSecond;

import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;
import com.deppon.pda.bdm.module.core.server.async.queue.second.AsyncDataSecondQueue;
import com.deppon.pda.bdm.module.core.server.async.queue.second.IAsyncDataSecondQueue;

public class SimpleAsyncDataSyncSecondFactory extends AbstractAsyncDataSyncSecondFactory<QueueMessage> {
	
	protected IAsyncDataSecondQueue<QueueMessage> getAsyncDataQueue(){
		return AsyncDataSecondQueue.getInstance();
	}

}

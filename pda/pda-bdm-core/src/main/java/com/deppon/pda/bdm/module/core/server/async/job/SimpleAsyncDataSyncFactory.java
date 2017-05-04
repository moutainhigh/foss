package com.deppon.pda.bdm.module.core.server.async.job;

import com.deppon.pda.bdm.module.core.server.async.queue.AsyncDataQueue;
import com.deppon.pda.bdm.module.core.server.async.queue.IAsyncDataQueue;
import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;

public class SimpleAsyncDataSyncFactory extends AbstractAsyncDataSyncFactory<QueueMessage> {
	
	protected IAsyncDataQueue<QueueMessage> getAsyncDataQueue(){
		return AsyncDataQueue.getInstance();
	}

}

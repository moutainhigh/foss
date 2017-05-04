package com.deppon.pda.bdm.module.core.server.async.job;

import com.deppon.pda.bdm.module.core.server.async.queue.AsyncDataGroupQueue;
import com.deppon.pda.bdm.module.core.server.async.queue.GroupQueueMessage;
import com.deppon.pda.bdm.module.core.server.async.queue.IAsyncDataQueue;

public class GroupAsyncDataSyncFactory extends AbstractAsyncDataSyncFactory<GroupQueueMessage> {
	
	protected IAsyncDataQueue<GroupQueueMessage> getAsyncDataQueue(){
		return AsyncDataGroupQueue.getInstance();
	}

}

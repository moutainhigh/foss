package com.deppon.pda.bdm.module.core.server.async.jobSecond;

import com.deppon.pda.bdm.module.core.server.async.queue.GroupQueueMessage;
import com.deppon.pda.bdm.module.core.server.async.queue.second.AsyncDataGroupSecondQueue;
import com.deppon.pda.bdm.module.core.server.async.queue.second.IAsyncDataSecondQueue;

public class GroupAsyncDataSyncSecondFactory extends AbstractAsyncDataSyncSecondFactory<GroupQueueMessage> {
	
	protected IAsyncDataSecondQueue<GroupQueueMessage> getAsyncDataQueue(){
		return AsyncDataGroupSecondQueue.getInstance();
	}

}

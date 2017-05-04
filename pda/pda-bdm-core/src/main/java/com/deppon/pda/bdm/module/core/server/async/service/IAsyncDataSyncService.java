package com.deppon.pda.bdm.module.core.server.async.service;

import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;

public interface IAsyncDataSyncService<T extends QueueMessage> {

	public void syncData(T queueMsg) throws Exception;
	
	public void dealException(T queueMsg, Exception e) throws Exception;
	
	public void dealBussinessException(T queueMsg,Exception e)throws Exception;
}

package com.deppon.pda.bdm.module.core.server.async.queue.second;

import java.util.List;

import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;

public interface IAsyncDataSecondQueue<T extends QueueMessage> {
	public void put(T queueMsg);
	
	public void put(List<T> queueMsgs);
	
	public T get();
	
	public void commit(T queueMsg);
	
	public void rollback(T queueMsg);
	
	public int size();
	public int dealingSize();
	
	public String getQueueId();
}

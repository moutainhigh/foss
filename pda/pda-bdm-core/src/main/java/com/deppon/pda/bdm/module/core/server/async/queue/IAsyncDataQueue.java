package com.deppon.pda.bdm.module.core.server.async.queue;

import java.util.List;

public interface IAsyncDataQueue<T extends QueueMessage> {
	public void put(T queueMsg);
	
	public void put(List<T> queueMsgs);
	
	public T get();
	
	public void commit(T queueMsg);
	
	public void rollback(T queueMsg);
	
	public int size();
	public int dealingSize();
	
	public String getQueueId();
}

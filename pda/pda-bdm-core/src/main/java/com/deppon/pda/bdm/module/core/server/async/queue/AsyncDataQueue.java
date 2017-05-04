package com.deppon.pda.bdm.module.core.server.async.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 异步数据队列
 * @author wanghongling
 * @date 2012-10-18
 * @version 1.0
 *
 */
public class AsyncDataQueue implements IAsyncDataQueue<QueueMessage> {
	private List<QueueMessage> queue = new ArrayList<QueueMessage>();
	private List<QueueMessage> dealingQueue = new ArrayList<QueueMessage>();
	private String id;
	
	private static AsyncDataQueue instance;
	
	private AsyncDataQueue(){
		id = UUID.randomUUID().toString();
	}
	
	public static AsyncDataQueue getInstance(){
		if(instance == null){
			instance = new AsyncDataQueue();
		}
		return instance;
	}
	
	public synchronized void put(QueueMessage queueMsg){
		queue.add(queueMsg);
	}
	
	public synchronized void put(List<QueueMessage> queueMsgs){
		queue.addAll(queueMsgs);
	}
	
	public synchronized QueueMessage get(){
		if(queue.isEmpty()){
			return null;
		}
		QueueMessage queueMsg = queue.remove(0);
		if(queueMsg!=null){
			dealingQueue.add(queueMsg);
		}
		return queueMsg;
	}
	
	public synchronized void commit(QueueMessage queueMsg){
		dealingQueue.remove(queueMsg);
	}
	
	public synchronized void rollback(QueueMessage queueMsg){
		dealingQueue.remove(queueMsg);
		queue.add(0,queueMsg);
	}
	
	public synchronized int size(){
		return queue.size();
	}
	public synchronized int dealingSize(){
		return dealingQueue.size();
	}

	@Override
	public String getQueueId() {
		return id;
	}
	
}

package com.deppon.pda.bdm.module.core.server.async.queue.second;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;


public class AsyncDataSecondQueue implements IAsyncDataSecondQueue<QueueMessage> {
	private List<QueueMessage> queueSecond = new ArrayList<QueueMessage>();
	private List<QueueMessage> dealingQueueSecond = new ArrayList<QueueMessage>();
	private String id;
	
	private static AsyncDataSecondQueue instance;
	
	private AsyncDataSecondQueue(){
		id = UUID.randomUUID().toString();
	}
	
	public static AsyncDataSecondQueue getInstance(){
		if(instance == null){
			instance = new AsyncDataSecondQueue();
		}
		return instance;
	}
	
	public synchronized void put(QueueMessage queueMsg){
		queueSecond.add(queueMsg);
	}
	
	public synchronized void put(List<QueueMessage> queueMsgs){
		queueSecond.addAll(queueMsgs);
	}
	
	public synchronized QueueMessage get(){
		if(queueSecond.isEmpty()){
			return null;
		}
		QueueMessage queueMsg = queueSecond.remove(0);
		if(queueMsg!=null){
			dealingQueueSecond.add(queueMsg);
		}
		return queueMsg;
	}
	
	public synchronized void commit(QueueMessage queueMsg){
		dealingQueueSecond.remove(queueMsg);
	}
	
	public synchronized void rollback(QueueMessage queueMsg){
		dealingQueueSecond.remove(queueMsg);
		queueSecond.add(0,queueMsg);
	}
	
	public synchronized int size(){
		return queueSecond.size();
	}
	public synchronized int dealingSize(){
		return dealingQueueSecond.size();
	}

	@Override
	public String getQueueId() {
		return id;
	}
	
}

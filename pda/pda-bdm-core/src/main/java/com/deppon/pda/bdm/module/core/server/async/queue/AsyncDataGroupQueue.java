package com.deppon.pda.bdm.module.core.server.async.queue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class AsyncDataGroupQueue implements IAsyncDataQueue<GroupQueueMessage> {
	private List<GroupQueueMessage> queue = new ArrayList<GroupQueueMessage>();
	private Set<GroupQueueMessage> dealingQueue = new HashSet<GroupQueueMessage>();
	private Set<String> dealingKeyQueue = new HashSet<String>();
	private String id;
	
	private static AsyncDataGroupQueue instance;
	
	private AsyncDataGroupQueue(){
		id = UUID.randomUUID().toString();
	}
	
	public static AsyncDataGroupQueue getInstance(){
		if(instance == null){
			instance = new AsyncDataGroupQueue();
		}
		return instance;
	}
	public synchronized void put(GroupQueueMessage queueMsg){
		if(queueMsg == null) return;
		queue.add(queueMsg);
	}
	
	public synchronized void put(List<GroupQueueMessage> queueMsgs){
		if(queueMsgs == null || queueMsgs.isEmpty()) return ;
		queue.addAll(queueMsgs);
	}
	
	public synchronized GroupQueueMessage get(){
		if(queue.isEmpty()){
			return null;
		}
		for(int i=0;i<queue.size();i++){
			if(!dealingKeyQueue.contains(queue.get(i).getGroupId())){
				GroupQueueMessage msg = queue.remove(i);
				dealingQueue.add(msg);
				dealingKeyQueue.add(msg.getGroupId());
				return msg;
			}
		}
		return null;
	}
	
	public synchronized void commit(GroupQueueMessage queueMsg){
		dealingQueue.remove(queueMsg);
		dealingKeyQueue.remove(queueMsg.getGroupId());
	}
	
	public synchronized void rollback(GroupQueueMessage queueMsg){
		dealingKeyQueue.remove(queueMsg.getGroupId());
		dealingQueue.remove(queueMsg);
		queue.add(queueMsg);
	}
	
	public int size(){
		return queue.size();
	}
	public int dealingSize(){
		return dealingQueue.size();
	}
	
	public static void main(String[] args) {
		List<GroupQueueMessage> queue = new ArrayList<GroupQueueMessage>();
		Set<String> dealingKeyQueue = new HashSet<String>();
		for(int i=0;i<2000;i++){
			dealingKeyQueue.add("key"+i);
		}
		for(int i=0;i<5000;i++){
			GroupQueueMessage msg = new GroupQueueMessage();
			msg.setGroupId("groupid");
			queue.add(msg);
		}
		long start = System.currentTimeMillis(); 
		for(int j=0;j<200;j++){
		for(int i=0;i<queue.size();i++){
			dealingKeyQueue.remove(null);
//			if(!dealingKeyQueue.contains(queue.get(i).getGroupId())){
////			for(int k=0;k<500;k++){
////				if(queue.get(i).getGroupId()==""){
////				}
//			}
		}
		}
		long end = System.currentTimeMillis();
		System.out.println("time:"+(end-start));
	}

	@Override
	public String getQueueId() {
		return id;
	}
}

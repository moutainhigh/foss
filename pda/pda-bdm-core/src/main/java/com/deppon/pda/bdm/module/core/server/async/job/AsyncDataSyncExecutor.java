package com.deppon.pda.bdm.module.core.server.async.job;

import org.apache.log4j.Logger;

import com.deppon.pda.bdm.module.core.server.async.queue.IAsyncDataQueue;
import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;
import com.deppon.pda.bdm.module.core.server.async.service.IAsyncDataSyncService;
import com.deppon.pda.bdm.module.core.server.monitor.MonitorControl;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;

public class AsyncDataSyncExecutor<T extends QueueMessage> implements Runnable {
	private IAsyncDataSyncService<T> asyncDataSyncService;
	private IAsyncDataQueue<T> queue;
	private Logger log = Logger.getLogger(getClass());
	//private long start;

	@Override
	public void run() {
		T queueMsg = null;
		try{
			long start = System.currentTimeMillis();
			queueMsg = queue.get();
			QueueMonitorInfo.addGetCount();
			QueueMonitorInfo.addTotalGetTime(System.currentTimeMillis()-start);
			if(queueMsg!=null){
				QueueMonitorInfo.addActiveGetCount();
				QueueMonitorInfo.addTotalActiveGetTime(System.currentTimeMillis()-start);
				String operType = "";
				if(queueMsg instanceof AsyncMsg){
					operType = ((AsyncMsg)queueMsg).getOperType();
				}
				start = System.currentTimeMillis();
				asyncDataSyncService.syncData(queueMsg);
				log.info("[asyncinfo][job]["+operType+"]消息处理时间："+(System.currentTimeMillis()-start));
				QueueMonitorInfo.addSyncCount();
				QueueMonitorInfo.addTotalSyncTime(System.currentTimeMillis()-start);
				start = System.currentTimeMillis();
				queue.commit(queueMsg);
				QueueMonitorInfo.addCommitTime();
				QueueMonitorInfo.addTotalCommitTime(System.currentTimeMillis()-start);
			}
		}catch(FossInterfaceException e){
			if(queueMsg!=null){
				//这里commit的实际动作是在内存里删除，rollback的实际动作是重新排到队列中，
				//所以如果写rollback就会不停的重复处理,即使是数据库中状态此时已改为异常状态，也会不停的重复处理。
				//而commit在内存中删除后，asyncDataSyncService.dealException(queueMsg, e)语句就会修改数据库状态为异常状态。
				try{
					asyncDataSyncService.dealBussinessException(queueMsg, e);
					queue.commit(queueMsg);
					//将数据提交至监控，这里需要进行监控
					//if(queueMsg != null){
						AsyncMsg asyncMsg = (AsyncMsg)queueMsg;
						MonitorControl.put(asyncMsg,Constant.MONITOR.ERROR);
					//}
				}catch(Exception e1){
					queue.rollback(queueMsg);
					log.error("["+queueMsg+"]"+LogUtil.logFormat(e1));
				}
			}
			log.error("["+queueMsg+"]"+LogUtil.logFormat(e));
		}catch(Exception e){
			if(queueMsg!=null){
				//这里commit的实际动作是在内存里删除，rollback的实际动作是重新排到队列中，
				//所以如果写rollback就会不停的重复处理,即使是数据库中状态此时已改为异常状态，也会不停的重复处理。
				//而commit在内存中删除后，asyncDataSyncService.dealException(queueMsg, e)语句就会修改数据库状态为异常状态。
				try{
					asyncDataSyncService.dealException(queueMsg, e);
					queue.commit(queueMsg);
					//将数据提交至监控，这里需要进行监控
					if(queueMsg != null){
						AsyncMsg asyncMsg = (AsyncMsg)queueMsg;
						MonitorControl.put(asyncMsg,Constant.MONITOR.ERROR);
					}
				}catch(Exception e1){
					queue.rollback(queueMsg);
					log.error("["+queueMsg.getId()+"]"+LogUtil.logFormat(e1));
				}
			}
			log.error("["+queueMsg+"]"+LogUtil.logFormat(e));
		}
	}
	
	public IAsyncDataSyncService<T> getAsyncDataSyncService() {
		return asyncDataSyncService;
	}

	public void setAsyncDataSyncService(IAsyncDataSyncService<T> asyncDataSyncService) {
		this.asyncDataSyncService = asyncDataSyncService;
	}
	
	public IAsyncDataQueue<T> getQueue() {
		return queue;
	}

	public void setQueue(IAsyncDataQueue<T> queue) {
		this.queue = queue;
	} 
}

package com.deppon.pda.bdm.module.core.server.async.jobSecond;

import org.apache.log4j.Logger;

import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;
import com.deppon.pda.bdm.module.core.server.async.queue.second.IAsyncDataSecondQueue;
import com.deppon.pda.bdm.module.core.server.async.service.job.IAsyncDataSyncSecondService;
import com.deppon.pda.bdm.module.core.server.monitor.second.MonitorSecondControl;
import com.deppon.pda.bdm.module.core.shared.constants.Constant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.LogUtil;

public class AsyncDataSyncSecondExecutor<T extends QueueMessage> implements Runnable {
	private IAsyncDataSyncSecondService<T> asyncDataSyncSecondService;
	private IAsyncDataSecondQueue<T> secondQueue;
	private Logger log = Logger.getLogger(getClass());

	@Override
	public void run() {
		T queueMsg = null;
		try{
			long start = System.currentTimeMillis();
			queueMsg = secondQueue.get();
			QueueMonitorSecondInfo.addGetCount();
			QueueMonitorSecondInfo.addTotalGetTime(System.currentTimeMillis()-start);
			if(queueMsg!=null){
				QueueMonitorSecondInfo.addActiveGetCount();
				QueueMonitorSecondInfo.addTotalActiveGetTime(System.currentTimeMillis()-start);
				String operType = "";
				if(queueMsg instanceof AsyncMsg){
					operType = ((AsyncMsg)queueMsg).getOperType();
				}
				start = System.currentTimeMillis();
				asyncDataSyncSecondService.syncData(queueMsg);
				log.info("[asyncinfo][job]["+operType+"]消息处理时间："+(System.currentTimeMillis()-start));
				QueueMonitorSecondInfo.addSyncCount();
				QueueMonitorSecondInfo.addTotalSyncTime(System.currentTimeMillis()-start);
				start = System.currentTimeMillis();
				secondQueue.commit(queueMsg);
				QueueMonitorSecondInfo.addCommitTime();
				QueueMonitorSecondInfo.addTotalCommitTime(System.currentTimeMillis()-start);
			}
		}catch(FossInterfaceException e){
			if(queueMsg!=null){
				//这里commit的实际动作是在内存里删除，rollback的实际动作是重新排到队列中，
				//所以如果写rollback就会不停的重复处理,即使是数据库中状态此时已改为异常状态，也会不停的重复处理。
				//而commit在内存中删除后，asyncDataSyncServiceSecond.dealException(queueMsg, e)语句就会修改数据库状态为异常状态。
				try{
					asyncDataSyncSecondService.dealBussinessException(queueMsg, e);
					secondQueue.commit(queueMsg);
					//将数据提交至监控，这里需要进行监控
					//if(queueMsg != null){
						AsyncMsg asyncMsg = (AsyncMsg)queueMsg;
						MonitorSecondControl.put(asyncMsg,Constant.MONITOR.ERROR);
					//}
				}catch(Exception e1){
					secondQueue.rollback(queueMsg);
					log.error("["+queueMsg+"]"+LogUtil.logFormat(e1));
				}
			}
			log.error("["+queueMsg+"]"+LogUtil.logFormat(e));
		}catch(Exception e){
			if(queueMsg!=null){
				//这里commit的实际动作是在内存里删除，rollback的实际动作是重新排到队列中，
				//所以如果写rollback就会不停的重复处理,即使是数据库中状态此时已改为异常状态，也会不停的重复处理。
				//而commit在内存中删除后，asyncDataSyncServiceSecond.dealException(queueMsg, e)语句就会修改数据库状态为异常状态。
				try{
					asyncDataSyncSecondService.dealException(queueMsg, e);
					secondQueue.commit(queueMsg);
					//将数据提交至监控，这里需要进行监控
					if(queueMsg != null){
						AsyncMsg asyncMsg = (AsyncMsg)queueMsg;
						MonitorSecondControl.put(asyncMsg,Constant.MONITOR.ERROR);
					}
				}catch(Exception e1){
					secondQueue.rollback(queueMsg);
					log.error("["+queueMsg.getId()+"]"+LogUtil.logFormat(e1));
				}
			}
			log.error("["+queueMsg+"]"+LogUtil.logFormat(e));
		}
	}
	
	
	
	public IAsyncDataSyncSecondService<T> getAsyncDataSyncSecondService() {
		return asyncDataSyncSecondService;
	}

	public void setAsyncDataSyncSecondService(
			IAsyncDataSyncSecondService<T> asyncDataSyncSecondService) {
		this.asyncDataSyncSecondService = asyncDataSyncSecondService;
	}



	public IAsyncDataSecondQueue<T> getSecondQueue() {
		return secondQueue;
	}



	public void setSecondQueue(IAsyncDataSecondQueue<T> secondQueue) {
		this.secondQueue = secondQueue;
	}


}

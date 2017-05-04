package com.deppon.pda.bdm.module.core.server.async.service;

import com.deppon.pda.bdm.module.core.server.async.queue.ScanDataQueueMsg;

public class TestAsyncDataSyncService implements IAsyncDataSyncService<ScanDataQueueMsg> {

	@Override
	public void syncData(ScanDataQueueMsg queueMsg) {
		try {
			if(queueMsg!=null){
				System.out.println("消息："+queueMsg.getId());
				Thread.sleep(2000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void dealException(ScanDataQueueMsg queueMsg, Exception e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dealBussinessException(ScanDataQueueMsg queueMsg, Exception e)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}

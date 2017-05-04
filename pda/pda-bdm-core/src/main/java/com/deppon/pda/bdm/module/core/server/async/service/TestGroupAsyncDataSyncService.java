package com.deppon.pda.bdm.module.core.server.async.service;

import com.deppon.pda.bdm.module.core.server.async.queue.GroupScanDataQueueMsg;

public class TestGroupAsyncDataSyncService implements IAsyncDataSyncService<GroupScanDataQueueMsg> {

	@Override
	public void syncData(GroupScanDataQueueMsg queueMsg) {
		try {
			if(queueMsg!=null){
				System.out.println(queueMsg.getScanMsgId()+"/"+queueMsg.getGroupId());
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void dealException(GroupScanDataQueueMsg queueMsg, Exception e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dealBussinessException(GroupScanDataQueueMsg queueMsg,
			Exception e) throws Exception {
		// TODO Auto-generated method stub
		
	}

}

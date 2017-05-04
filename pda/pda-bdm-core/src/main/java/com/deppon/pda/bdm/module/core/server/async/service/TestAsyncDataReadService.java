package com.deppon.pda.bdm.module.core.server.async.service;

import java.util.LinkedList;

import com.deppon.pda.bdm.module.core.server.async.queue.ScanDataQueueMsg;

public class TestAsyncDataReadService implements IAsyncDataReadService<ScanDataQueueMsg> {
	private static long index;
	private int maxCount;

	@Override
	public LinkedList<ScanDataQueueMsg> readAsyncData(int count, String queueId) {
		LinkedList<ScanDataQueueMsg> result = new LinkedList<ScanDataQueueMsg>();
		if(index<maxCount){
			for(int i=0;i<count;i++){
				ScanDataQueueMsg msg = new ScanDataQueueMsg();
				msg.setId(""+(index++));
				result.add(msg);
			}
		}
		return result;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	@Override
	public void initAsyncDataStatus(String queueId) {
		// TODO Auto-generated method stub
		System.out.println("initAsyncDataStatus");
	}

}

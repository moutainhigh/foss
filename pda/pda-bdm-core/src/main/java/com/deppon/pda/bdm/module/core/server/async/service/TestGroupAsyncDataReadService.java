package com.deppon.pda.bdm.module.core.server.async.service;

import java.util.LinkedList;
import java.util.Random;

import com.deppon.pda.bdm.module.core.server.async.queue.GroupScanDataQueueMsg;

public class TestGroupAsyncDataReadService implements IAsyncDataReadService<GroupScanDataQueueMsg> {
	private static long index;
	private int maxCount;
	private static int totalCount;

	@Override
	public LinkedList<GroupScanDataQueueMsg> readAsyncData(int count, String queueId) {
		LinkedList<GroupScanDataQueueMsg> result = new LinkedList<GroupScanDataQueueMsg>();
		if(index<maxCount){
			for(int i=0;i<count;i++){
				GroupScanDataQueueMsg msg = new GroupScanDataQueueMsg();
				msg.setScanMsgId(""+(index++));
				msg.setGroupId(""+new Random().nextInt(10));
				result.add(msg);
			}
		}
		totalCount+=count;
		try {
			Thread.sleep(4000);
			System.out.println("totalCount:"+totalCount);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		System.out.println("initAsyncDataStatus");
		
	}

}

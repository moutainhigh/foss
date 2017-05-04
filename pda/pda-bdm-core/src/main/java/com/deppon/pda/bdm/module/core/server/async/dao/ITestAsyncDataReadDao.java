package com.deppon.pda.bdm.module.core.server.async.dao;

import java.util.List;

import com.deppon.pda.bdm.module.core.server.async.queue.ScanDataQueueMsg;

public interface ITestAsyncDataReadDao {
	public List<ScanDataQueueMsg> readAsyncData(int count, String queueId);
	
	public void initAsyncDataStatus(String queueId);
}

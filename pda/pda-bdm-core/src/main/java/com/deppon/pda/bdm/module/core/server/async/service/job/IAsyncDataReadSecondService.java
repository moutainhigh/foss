package com.deppon.pda.bdm.module.core.server.async.service.job;

import java.util.List;

import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;

public interface IAsyncDataReadSecondService<T extends QueueMessage> {

	public List<T> readAsyncData(int count, String queueId);
	
	public void initAsyncDataStatus(String queueId);
}

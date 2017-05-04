package com.deppon.pda.bdm.module.core.server.async.service;

import java.util.List;

import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;

public interface IAsyncDataReadService<T extends QueueMessage> {

	public List<T> readAsyncData(int count, String queueId);
	
	public void initAsyncDataStatus(String queueId);
}

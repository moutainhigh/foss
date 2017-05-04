package com.deppon.pda.bdm.module.core.server.async.service;

import java.util.List;

import com.deppon.pda.bdm.module.core.server.async.dao.ITestAsyncDataReadDao;
import com.deppon.pda.bdm.module.core.server.async.queue.ScanDataQueueMsg;

public class TestClusterAsyncDataReadService extends
		AbstractClusterAsyncDataReadService<ScanDataQueueMsg> {
	
	private ITestAsyncDataReadDao asyncDataReadDao;

	@Override
	protected List<ScanDataQueueMsg> doReadAsyncData(int count, String queueId) {
		
		return asyncDataReadDao.readAsyncData(count, queueId);
	}

	public ITestAsyncDataReadDao getAsyncDataReadDao() {
		return asyncDataReadDao;
	}

	public void setAsyncDataReadDao(ITestAsyncDataReadDao asyncDataReadDao) {
		this.asyncDataReadDao = asyncDataReadDao;
	}

	@Override
	public void doInitAsyncDataStatus(String queueId) {
		asyncDataReadDao.initAsyncDataStatus(queueId);
		
	}

}

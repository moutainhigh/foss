package com.deppon.pda.bdm.module.core.server.async.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.pda.bdm.module.core.server.async.dao.IClusterAsyncDataReadLockDao;
import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;

public abstract class AbstractClusterAsyncDataReadService<T extends QueueMessage> implements
		IAsyncDataReadService<T > {
	private IClusterAsyncDataReadLockDao clusterAsyncDataReadLockDao;
	@Transactional
	@Override
	public List<T> readAsyncData(int count, String queueId) {
		readLock(queueId);
		List<T> result = doReadAsyncData(count, queueId);
		readUnLock(queueId);
		return result;
	}
	@Transactional
	@Override
	public void initAsyncDataStatus(String queueId) {
		clusterAsyncDataReadLockDao.clearInvalidJob();
		doInitAsyncDataStatus(queueId);
	}
	
	private void readLock(String queueId){
		clusterAsyncDataReadLockDao.readLock(queueId);
	}
	private void readUnLock(String queueId){
		clusterAsyncDataReadLockDao.readUnLock(queueId);
	}
	
	protected abstract List<T> doReadAsyncData(int count, String queueId);
	protected abstract void doInitAsyncDataStatus(String queueId);

	public IClusterAsyncDataReadLockDao getClusterAsyncDataReadLockDao() {
		return clusterAsyncDataReadLockDao;
	}

	public void setClusterAsyncDataReadLockDao(
			IClusterAsyncDataReadLockDao clusterAsyncDataReadLockDao) {
		this.clusterAsyncDataReadLockDao = clusterAsyncDataReadLockDao;
	}

}

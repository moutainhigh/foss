package com.deppon.pda.bdm.module.core.server.async.service.job;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.deppon.pda.bdm.module.core.server.async.dao.second.IClusterAsyncDataReadLockSecondDao;
import com.deppon.pda.bdm.module.core.server.async.queue.QueueMessage;

public abstract class AbstractClusterAsyncDataReadSecondService<T extends QueueMessage> implements
		IAsyncDataReadSecondService<T > {
	private IClusterAsyncDataReadLockSecondDao clusterAsyncDataReadLockSecondDao;
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
		clusterAsyncDataReadLockSecondDao.clearInvalidJob();
		doInitAsyncDataStatus(queueId);
	}
	
	private void readLock(String queueId){
		clusterAsyncDataReadLockSecondDao.readLock(queueId);
	}
	private void readUnLock(String queueId){
		clusterAsyncDataReadLockSecondDao.readUnLock(queueId);
	}
	
	protected abstract List<T> doReadAsyncData(int count, String queueId);
	protected abstract void doInitAsyncDataStatus(String queueId);
	
	
	public IClusterAsyncDataReadLockSecondDao getClusterAsyncDataReadLockSecondDao() {
		return clusterAsyncDataReadLockSecondDao;
	}
	public void setClusterAsyncDataReadLockSecondDao(
			IClusterAsyncDataReadLockSecondDao clusterAsyncDataReadLockSecondDao) {
		this.clusterAsyncDataReadLockSecondDao = clusterAsyncDataReadLockSecondDao;
	}
	
	
	
	

}

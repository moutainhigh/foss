package com.deppon.pda.bdm.module.core.server.async.dao;

public interface IClusterAsyncDataReadLockDao {
	public void readLock(String queueId);
	public void readUnLock(String queueId);
	public void clearInvalidJob();
}

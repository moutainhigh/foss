package com.deppon.pda.bdm.module.core.server.async.dao.second;

public interface IClusterAsyncDataReadLockSecondDao {
	public void readLock(String queueId);
	public void readUnLock(String queueId);
	public void clearInvalidJob();
}

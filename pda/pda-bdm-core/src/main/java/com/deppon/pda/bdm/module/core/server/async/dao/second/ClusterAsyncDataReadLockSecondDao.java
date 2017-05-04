package com.deppon.pda.bdm.module.core.server.async.dao.second;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class ClusterAsyncDataReadLockSecondDao extends JdbcDaoSupport implements
		IClusterAsyncDataReadLockSecondDao {
	private final static String SQL_READ_LOCK = "select 1 from pda.t_pda_async_job_second_lock where id='async_job_se_lock' for update nowait";
	private final static String SQL_READ_LOCK_INSERT = "insert into pda.t_pda_async_job_second_lock(id,value) values ('async_job_se_lock',1)";
	private final static String SQL_JOB_ACTIVE_UPD = "update pda.t_pda_async_job_second_active set lastActiveTime=sysdate where id=?";
	private final static String SQL_JOB_ACTIVE_INSERT = "insert into pda.t_pda_async_job_second_active(id,lastActiveTime) values (?,sysdate)";
	private final static String SQL_CLEAR_INVALID_JOB = "delete pda.t_pda_async_job_second_active where ROUND(TO_NUMBER(sysdate - lastActiveTime) * 24)>=1";

	@Override
	public void readLock(String queueId) {
		int updcount = getJdbcTemplate().update(SQL_READ_LOCK);
		if(updcount==0){
			getJdbcTemplate().update(SQL_READ_LOCK_INSERT);
			getJdbcTemplate().update(SQL_READ_LOCK);
		}
		updcount = getJdbcTemplate().update(SQL_JOB_ACTIVE_UPD,queueId);
		if(updcount==0){
			getJdbcTemplate().update(SQL_JOB_ACTIVE_INSERT, queueId);
		}
	}

	@Override
	public void readUnLock(String queueId) {

	}

	@Override
	public void clearInvalidJob() {
		getJdbcTemplate().update(SQL_CLEAR_INVALID_JOB);
	}

}

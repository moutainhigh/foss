package com.deppon.pda.bdm.module.core.server.async.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.deppon.pda.bdm.module.core.server.async.queue.ScanDataQueueMsg;

public class TestAsyncDataReadDao extends JdbcDaoSupport implements ITestAsyncDataReadDao {

	@Override
	public List<ScanDataQueueMsg> readAsyncData(int count, String queueId) {
		final List<ScanDataQueueMsg> result = new ArrayList<ScanDataQueueMsg>();
		getJdbcTemplate().query("select * from msg where exportstatus=0 and rownum<="+count, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				ScanDataQueueMsg msg = new ScanDataQueueMsg();
				msg.setId(rs.getString("id"));
				msg.setScanType(rs.getString("scanType"));
				msg.setReadTime(new Date());
				result.add(msg);
				System.out.println("read:"+msg.getId());
			}
		});
		for(ScanDataQueueMsg msg:result){
			getJdbcTemplate().update("update msg set exportstatus=1,readtime="+msg.getReadTime()+",queueId='"+queueId+"' where id='"+msg.getId()+"'");
		}
		return result;
	}

	@Override
	public void initAsyncDataStatus(String queueId) {
		getJdbcTemplate().update("update msg set exportstatus=0,readtime=0,queueId='' where queueId=? and exportstatus=1",queueId);
		getJdbcTemplate().update("update msg set exportstatus=0,readtime=0,queueId='' where queueId not in (select id from async_job_active) and exportstatus=1");
	}

}

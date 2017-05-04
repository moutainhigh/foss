package com.deppon.foss.module.transfer.platform.server.dao;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.platform.api.server.dao.IPullbackRateDao;
import com.deppon.foss.module.transfer.platform.api.shared.domain.PullbackRateEntity;
import com.deppon.foss.module.transfer.platform.server.BaseTestCase;

public class PullbackRateDaoTest extends BaseTestCase {
	@Autowired IPullbackRateDao pullbackRateDao;

	@Test
	public void testQueryPullbackRateList() {
	}

	@Test
	public void testQueryPullbackRateListCount() {
	}

	@Test
	public void testQueryPullbackRateJobList() {
	}

	@Test
	public void testQueryPullbackRateLogList() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("beginDate", "2014-01-01");
		map.put("endDate", "2014-03-14");
		map.put("orgCode", "W01140603");
		List<PullbackRateEntity> xx= pullbackRateDao.queryPullbackRateLogList(map, -1, -1);
		int i = 0;
		for (PullbackRateEntity pullbackRateEntity : xx) {
			i++;
			System.out.println("第"+i+"条："+pullbackRateEntity.getOrgName());
		}
	}

	@Test
	public void testQueryPullbackRateLogListCount() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("beginDate", "2014-01-01");
		map.put("endDate", "2014-03-14");
		map.put("orgCode", "W01140603");
		long xx = pullbackRateDao.queryPullbackRateLogListCount(map);
		System.out.println("===="+xx);
	}

	//@Test
	public void testQueryPullbackRateAllLogList() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("beginDate", "2014-01-01");
		map.put("endDate", "2014-03-14");
		List<PullbackRateEntity> xx= pullbackRateDao.queryPullbackRateAllLogList(map, -1, -1);
		int i = 0;
		for (PullbackRateEntity pullbackRateEntity : xx) {
			i++;
			System.out.println("第"+i+"条："+pullbackRateEntity.getOrgName());
		}
	}

	//@Test
	public void testQueryPullbackRateAllLogListCount() {
		Map<String,String> map = new HashMap<String,String>();
		map.put("beginDate", "2014-01-01");
		map.put("endDate", "2014-03-14");
		//map.put("orgCode", "W3100020616");
		long xx = pullbackRateDao.queryPullbackRateAllLogListCount(map);
		System.out.println("===="+xx);
	}

	@Test
	public void testInsertPullbackRatePojo() {
	}

}

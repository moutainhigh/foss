package com.deppon.foss.module.base.baseinfo.server.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressVehiclesDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.ExpressVehiclesDao;
import com.deppon.foss.module.base.baseinfo.server.service.LdpAgencyCompanyServiceTest;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class ExpressVehiclesDaoTest {

	private JdbcTemplate jdbc;

	private IExpressVehiclesDao expressVehiclesDao;

	private static final Logger log = Logger
			.getLogger(LdpAgencyCompanyServiceTest.class);

	@Before
	public void setup() {
		jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
				JdbcTemplate.class);
		expressVehiclesDao = (IExpressVehiclesDao) SpringTestHelper
				.get().getBeanByClass(ExpressVehiclesDao.class);
	}

	@After
	public void teardown() {
		jdbc = null;
	}
	
	@Test
	public void testAddExpressVehicles() {
		
		ExpressVehiclesEntity entity = new ExpressVehiclesEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.ACTIVE);
		entity.setCreateUser("092444");
		entity.setModifyUser("092444");
		entity.setModifyDate(new Date());
		entity.setCreateDate(new Date());
		entity.setEmpCode("084567");
		entity.setNotes("6464646");
		entity.setVehicleNo("德084567");
		entity.setAreaCode("410000");
		expressVehiclesDao.addExpressVehicles(entity);
	}

	@Test
	public void testDeleteExpressVehicles() {
		List<String> ids = new ArrayList<String>();
		ids.add("1212114584");
		ids.add("12125656561");
		ids.add("121565154");
		ExpressVehiclesEntity entity = new ExpressVehiclesEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.ACTIVE);
		expressVehiclesDao.deleteExpressVehicles(entity, ids);
	}

	@Test
	public void testUpdateExpressVehicles() {
		ExpressVehiclesEntity entity = new ExpressVehiclesEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setCreateUser("092444");
		entity.setModifyUser("092444");
		entity.setModifyDate(new Date());
		entity.setCreateDate(new Date());
		entity.setEmpCode("084567");
		entity.setNotes("6464646");
		entity.setVehicleNo("德084567");
		entity.setAreaCode("410000");
		expressVehiclesDao.updateExpressVehicles(entity);
	}

	@Test
	public void testQueryExpressVehiclesList() {
		ExpressVehiclesEntity entity = new ExpressVehiclesEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setEmpCode("084567");
		entity.setVehicleNo("德084567");
		expressVehiclesDao.queryInfos(entity, 10, 0);
	}

	@Test
	public void testQueryExpressVehiclesById() {
		expressVehiclesDao.queryExpressVehiclesById("321313131313131");
	}

	@Test
	public void testQueryRecordCount() {
		ExpressVehiclesEntity entity = new ExpressVehiclesEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setEmpCode("084567");
		entity.setVehicleNo("德084567");
		expressVehiclesDao.queryRecordCount(entity);
	}

	@Test
	public void testRecordCountByQueryParams() {
		ExpressVehiclesEntity entity = new ExpressVehiclesEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setEmpCode("084567");
		entity.setVehicleNo("德084567");
		expressVehiclesDao.recordCountByQueryParams(entity);
	}

}

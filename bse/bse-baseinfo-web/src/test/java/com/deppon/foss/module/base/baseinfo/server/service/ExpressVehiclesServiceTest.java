package com.deppon.foss.module.base.baseinfo.server.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.ExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class ExpressVehiclesServiceTest {

	private JdbcTemplate jdbc;

	private IExpressVehiclesService expressVehiclesService;

	private static final Logger log = Logger
			.getLogger(LdpAgencyCompanyServiceTest.class);

	@Before
	public void setup() {
		jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
				JdbcTemplate.class);
		expressVehiclesService = (IExpressVehiclesService) SpringTestHelper
				.get().getBeanByClass(ExpressVehiclesService.class);
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
		expressVehiclesService.addExpressVehicles(entity, "078816", true);
	}

	@Test
	public void testDeleteExpressVehicles() {
		List<String> ids = new ArrayList<String>();
		ids.add("1212114584");
		ids.add("12125656561");
		ids.add("121565154");
		expressVehiclesService.deleteExpressVehicles(ids, "078816");
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
		expressVehiclesService.updateExpressVehicles(entity, "013564", false);
	}

	@Test
	public void testQueryExpressVehiclesList() {
		ExpressVehiclesEntity entity = new ExpressVehiclesEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setEmpCode("084567");
		entity.setVehicleNo("德084567");
		expressVehiclesService.queryExpressVehiclesList(entity, 0, 10);
	}

	@Test
	public void testQueryExpressVehiclesById() {
		expressVehiclesService.queryExpressVehiclesById("321313131313131");
	}

	@Test
	public void testQueryRecordCount() {
		ExpressVehiclesEntity entity = new ExpressVehiclesEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setEmpCode("084567");
		entity.setVehicleNo("德084567");
		expressVehiclesService.queryRecordCount(entity);
	}

	@Test
	public void testRecordCountByQueryParams() {
		ExpressVehiclesEntity entity = new ExpressVehiclesEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setEmpCode("084567");
		entity.setVehicleNo("德084567");
		expressVehiclesService.recordCountByQueryParams(entity);
	}

}

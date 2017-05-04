package com.deppon.foss.module.base.baseinfo.server.dao;


import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILdpAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LdpAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.server.service.LdpAgencyCompanyServiceTest;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class LdpAgencyDeptDaoTest {

	@SuppressWarnings("unused")
	private JdbcTemplate jdbc;

	private ILdpAgencyDeptDao ldpAgencyDeptDao;

	private static final Logger log = Logger
			.getLogger(LdpAgencyCompanyServiceTest.class);

	@Before
	public void setup() {
		jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
				JdbcTemplate.class);
		ldpAgencyDeptDao = (ILdpAgencyDeptDao) SpringTestHelper
				.get().getBeanByClass(LdpAgencyDeptDao.class);
	}

	@After
	public void teardown() {
		jdbc = null;
	}
	@Test
	public void testAddLdpAgencyDept() {
		OuterBranchExpressEntity entity = new OuterBranchExpressEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.ACTIVE);
		entity.setAgentCompany("LDP00011");
		entity.setAgentDeptCode("LDP01101");
		entity.setAgentDeptName("浙江芝麻开门浙江店");
		entity.setProvCode("420000");
		entity.setCityCode("420100");
		entity.setCountyCode("420104");
		entity.setContact("王先生");
		entity.setCreateDate(new Date());
		entity.setCreateUser("078816");
		entity.setModifyDate(new Date());
		entity.setModifyUser("078816");
		ldpAgencyDeptDao.addLdpAgencyDept(entity);
		log.debug(entity);
	}

	@Test
	public void testDeleteLdpAgencyDeptByCode() {
		String[] codes = new String[]{"dad6ecc6-1000-4bc3-bb82-d17f3d7f2cd3","c6b6a69f-d4ec-43f5-8c06-6d75dce64c26"};
		ldpAgencyDeptDao.deleteLdpAgencyDeptByCode(codes, "078816");
	}

	@Test
	public void testUpdateLdpAgencyDept() {
		OuterBranchExpressEntity entity = new OuterBranchExpressEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.ACTIVE);
		entity.setAgentCompany("LDP00011");
		entity.setAgentDeptCode("LDP01101");
		entity.setAgentDeptName("浙江芝麻开门浙江店");
		entity.setProvCode("420000");
		entity.setCityCode("420100");
		entity.setCountyCode("420104");
		entity.setContact("王先生");
		entity.setCreateDate(new Date());
		entity.setCreateUser("078816");
		entity.setModifyDate(new Date());
		entity.setModifyUser("078816");
//	    int count = ldpAgencyDeptDao.updateLdpAgencyDept(entity);
//	    log.info("更新的行数"+count);
	}

	@Test
	public void testQueryLdpAgencyDepts() {
		OuterBranchExpressEntity entity = new OuterBranchExpressEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setAgentDeptCode("LDP01101");
		ldpAgencyDeptDao.queryLdpAgencyDepts(entity, 10, 0);
	}

	@Test
	public void testQueryLdpAgencyDeptsByComVirtualCode() {
		ldpAgencyDeptDao.queryLdpAgencyDeptsByComVirtualCode("dad6ecc6-1000-4bc3-bb82-d17f3d7f2cd3", "Y");
	}

	@Test
	public void testQueryLdpAgencyDeptsByComVirtualCodes() {
		String[] codes = new String[]{"dad6ecc6-1000-4bc3-bb82-d17f3d7f2cd3","c6b6a69f-d4ec-43f5-8c06-6d75dce64c26"};
		ldpAgencyDeptDao.queryLdpAgencyDeptsByComVirtualCodes(codes);
	}

	@Test
	public void testQueryRecordCount() {
		OuterBranchExpressEntity entity = new OuterBranchExpressEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.ACTIVE);
		entity.setAgentCompany("LDP00011");
		entity.setAgentDeptCode("LDP01101");
		entity.setAgentDeptName("浙江芝麻开门浙江店");
		entity.setProvCode("420000");
		entity.setCityCode("420100");
		entity.setCountyCode("420104");
		ldpAgencyDeptDao.queryRecordCount(entity);
	}

	@Test
	public void testQueryLdpAgencyDeptIsExistsByCode() {
		ldpAgencyDeptDao.queryLdpAgencyDeptIsExistsByCode("LDP01101");
	}

	@Test
	public void testQueryLdpAgencyDeptByCode() {
		ldpAgencyDeptDao.queryLdpAgencyDeptByCode("LDP01101", "Y");
	}

	@Test
	public void testQueryLdpAgencyDeptsByagencyCompanyCode() {
		ldpAgencyDeptDao.queryLdpAgencyDeptsByagencyCompanyCode("LDP00011", FossConstants.ACTIVE);
	}

	@Test
	public void testQueryLdpAgencyDeptsByagencyCompanyCodeS() {
		ldpAgencyDeptDao.queryLdpAgencyDeptsByagencyCompanyCode("LDP00011", FossConstants.ACTIVE, 10, 0);
	}

}

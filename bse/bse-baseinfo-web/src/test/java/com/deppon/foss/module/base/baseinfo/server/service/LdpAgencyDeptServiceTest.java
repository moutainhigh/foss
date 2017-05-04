package com.deppon.foss.module.base.baseinfo.server.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.LdpAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class LdpAgencyDeptServiceTest {

	@SuppressWarnings("unused")
	private JdbcTemplate jdbc;

	private ILdpAgencyDeptService ldpAgencyDeptService;

	private static final Logger log = Logger
			.getLogger(LdpAgencyCompanyServiceTest.class);

	@Before
	public void setup() {
		jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
				JdbcTemplate.class);
		ldpAgencyDeptService = (ILdpAgencyDeptService) SpringTestHelper
				.get().getBeanByClass(LdpAgencyDeptService.class);
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
		ldpAgencyDeptService.addLdpAgencyDept(entity);
		log.debug(entity);
	}

	@Test
	public void testDeleteLdpAgencyDeptByCode() {
		ldpAgencyDeptService.deleteLdpAgencyDeptByCode("LDP04312", "078816");
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
	    int count = ldpAgencyDeptService.updateLdpAgencyDept(entity);
	    log.info("更新的行数"+count);
	}

	@Test
	public void testQueryLdpAgencyDepts() {
		OuterBranchExpressEntity entity = new OuterBranchExpressEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setAgentDeptCode("LDP01101");
		ldpAgencyDeptService.queryLdpAgencyDepts(entity, 10, 0);
	}

	@Test
	public void testQueryLdpAgencyDeptsByComVirtualCode() {
		ldpAgencyDeptService.queryLdpAgencyDeptsByComVirtualCode("dad6ecc6-1000-4bc3-bb82-d17f3d7f2cd3", "Y");
	}

	@Test
	public void testQueryLdpAgencyDeptsByComVirtualCodes() {
		String[] codes = new String[]{"dad6ecc6-1000-4bc3-bb82-d17f3d7f2cd3","c6b6a69f-d4ec-43f5-8c06-6d75dce64c26"};
		ldpAgencyDeptService.queryLdpAgencyDeptsByComVirtualCodes(codes);
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
		ldpAgencyDeptService.queryRecordCount(entity);
	}

	@Test
	public void testQueryLdpAgencyDeptIsExistsByCode() {
		ldpAgencyDeptService.queryLdpAgencyDeptIsExistsByCode("LDP01101");
	}

	@Test
	public void testQueryLdpAgencyDeptByCode() {
		ldpAgencyDeptService.queryLdpAgencyDeptByCode("LDP01101", "Y");
	}

	@Test
	public void testQueryLdpAgencyDeptsByagencyCompanyCode() {
		ldpAgencyDeptService.queryLdpAgencyDeptsByagencyCompanyCode("LDP00011", FossConstants.ACTIVE);
	}

	@Test
	public void testQueryLdpAgencyDeptsByagencyCompanyCodeS() {
		ldpAgencyDeptService.queryLdpAgencyDeptsByagencyCompanyCode("LDP00011", FossConstants.ACTIVE, 10, 0);
	}

}

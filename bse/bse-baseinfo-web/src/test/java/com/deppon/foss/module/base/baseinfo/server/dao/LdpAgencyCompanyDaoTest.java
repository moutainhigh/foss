package com.deppon.foss.module.base.baseinfo.server.dao;


import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILdpAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchExpressEntity;
import com.deppon.foss.module.base.baseinfo.server.dao.impl.LdpAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.server.service.LdpAgencyCompanyServiceTest;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class LdpAgencyCompanyDaoTest {

	@SuppressWarnings("unused")
	private JdbcTemplate jdbc;

	private ILdpAgencyCompanyDao ldpAgencyCompanyDao;

	private static final Logger log = Logger
			.getLogger(LdpAgencyCompanyServiceTest.class);

	@Before
	public void setup() {
		jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(
				JdbcTemplate.class);
		ldpAgencyCompanyDao = (ILdpAgencyCompanyDao) SpringTestHelper
				.get().getBeanByClass(LdpAgencyCompanyDao.class);
	}

	@After
	public void teardown() {
		jdbc = null;
	}
	@Test
	public void testAddLdpAgencyCompany() {
		BusinessPartnerExpressEntity entity = new BusinessPartnerExpressEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.ACTIVE);
		entity.setAgentCompanyCode("LDP000784");
		entity.setAgentCompanyName("上海讯飞");
		entity.setAgentCompanySort("LD");
		entity.setProvCode("120000");
		entity.setCityCode("120000-1");
		entity.setContact("王先生");
		entity.setContactAddress("上海虹桥机场");
		entity.setCreateDate(new Date());
		entity.setCreateUser("078816");
		entity.setModifyDate(new Date());
		entity.setModifyUser("078816");
		ldpAgencyCompanyDao.addLdpAgencyCompany(entity);
		log.debug(entity);
	}

	@Test
	public void testDeleteLdpAgencyCompanyByCode() {
		ldpAgencyCompanyDao.deleteLdpAgencyCompanyByCode(new String[]{"LDP1245","GS54565"}, "092444");
	}

	@Test
	public void testUpdateLdpAgencyCompany() {
		BusinessPartnerExpressEntity entity = new BusinessPartnerExpressEntity();
		entity.setId("9e569670-ef63-406b-a323-500d2966e8c0");
		entity.setActive(FossConstants.ACTIVE);
		int count = ldpAgencyCompanyDao.updateLdpAgencyCompany(entity);
		if(count >0){
			
			entity.setId(UUIDUtils.getUUID());
			entity.setAgentCompanyCode("LDP000784");
			entity.setAgentCompanyName("上海讯飞");
			entity.setAgentCompanySort("LD");
			entity.setProvCode("120000");
			entity.setCityCode("120000-1");
			entity.setContact("王先生");
			entity.setContactAddress("上海虹桥机场");
			entity.setCreateDate(new Date());
			entity.setCreateUser("078816");
			entity.setModifyDate(new Date());
			entity.setModifyUser("078816");
			ldpAgencyCompanyDao.addLdpAgencyCompany(entity);
			log.debug(entity);
		}
	}

	@Test
	public void testQueryLdpAgencyCompanys() {
		
		BusinessPartnerExpressEntity entity = new BusinessPartnerExpressEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setAgentCompanyCode("LDP000784");
		entity.setAgentCompanySort("LD");
		ldpAgencyCompanyDao.queryLdpAgencyCompanys(entity, 10, 0);
		log.debug(entity);
	}

	@Test
	public void testQueryInfos() {
		BusinessPartnerExpressEntity entity = new BusinessPartnerExpressEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setAgentCompanyCode("LDP000784");
		entity.setAgentCompanySort("LD");
		ldpAgencyCompanyDao.queryLdpAgencyCompanys(entity, 10, 0);
		log.debug(entity);
	}

	@Test
	public void testQueryRecordCount() {
		BusinessPartnerExpressEntity entity = new BusinessPartnerExpressEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setAgentCompanyCode("LDP000784");
		entity.setAgentCompanySort("LD");
		ldpAgencyCompanyDao.queryRecordCount(entity);
		log.debug(entity);
	}

	@Test
	public void testQueryEntityByName() {
		BusinessPartnerExpressEntity entity = new BusinessPartnerExpressEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setAgentCompanyName("上海讯飞");
		entity.setAgentCompanySort("LD");
		ldpAgencyCompanyDao.queryEntityByName(entity.getAgentCompanyName(), entity.getActive());
		log.debug(entity);
	}

	@Test
	public void testQueryEntityBySimplename() {
		ldpAgencyCompanyDao.queryEntityBySimplename("讯飞",FossConstants.ACTIVE);
	}

	@Test
	public void testQueryEntityByCode() {
		BusinessPartnerExpressEntity entity = new BusinessPartnerExpressEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setAgentCompanyCode("LDP000784");
	
		ldpAgencyCompanyDao.queryEntityByCode(entity.getAgentCompanyCode(), entity.getActive());
		log.debug(entity);
	}

	@Test
	public void testQueryBusinessPartnerByAgencyDeptCode() {
		OuterBranchExpressEntity entity = new OuterBranchExpressEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setAgentDeptCode("LDP11123");
		ldpAgencyCompanyDao.queryBusinessPartnerByAgencyDeptCode(entity.getAgentDeptCode(), FossConstants.ACTIVE);
		log.debug(entity);
	}

}

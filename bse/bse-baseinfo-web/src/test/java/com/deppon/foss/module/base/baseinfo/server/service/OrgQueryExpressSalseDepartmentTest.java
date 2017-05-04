package com.deppon.foss.module.base.baseinfo.server.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.complex.OrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;

public class OrgQueryExpressSalseDepartmentTest {


	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    @SuppressWarnings("unused")
	private JdbcTemplate jdbc;
    
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	orgAdministrativeInfoComplexService = (IOrgAdministrativeInfoComplexService) SpringTestHelper.get().getBeanByClass(OrgAdministrativeInfoComplexService.class);
    }

    @After
    public void tearDown() throws Exception {
    	jdbc = null;
    }

	@Test
	public void test(){
		
		String code  = "W31000206090611";
		List<String> codeList = new ArrayList<String>();
		codeList.add(code);
		List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryExpressSalesDepartmentByTransCenterCode(codeList);
		System.out.println(orgList.toArray().toString());
		System.out.println(orgList.size());
	}
}

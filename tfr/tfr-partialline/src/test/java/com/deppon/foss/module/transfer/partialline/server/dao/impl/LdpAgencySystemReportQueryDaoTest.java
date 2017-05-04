package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.transfer.partialline.api.server.dao.ILdpAgencySystemReportQueryDao;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpAgencySystemReportQueryDto;
import com.deppon.foss.module.transfer.partialline.test.BaseTestCase;

public class LdpAgencySystemReportQueryDaoTest extends BaseTestCase{

	@Autowired
	private ILdpAgencySystemReportQueryDao ldpAgencySystemReportQueryDao;
	
	@Test
	public void testQueryLdpAgencySystemReportByDto() {
		LdpAgencySystemReportQueryDto dto = new LdpAgencySystemReportQueryDto();
		dto.setProductType("PACKAGE");
		dto.setEmpCode("092444");
		dto.setActive("Y");
		
		ldpAgencySystemReportQueryDao.queryLdpAgencySystemReportByDto(10, 0, dto);
	}

	@Test
	public void testQueryTotalRecordsInDBByDto() {
		LdpAgencySystemReportQueryDto dto = new LdpAgencySystemReportQueryDto();
		dto.setProductType("PACKAGE");
		dto.setEmpCode("092444");
		dto.setActive("Y");
		
		ldpAgencySystemReportQueryDao.queryTotalRecordsInDBByDto(dto);
	}

}

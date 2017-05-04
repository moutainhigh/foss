package com.deppon.foss.module.transfer.partialline.server.service.impl;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpAgencySystemReportQueryService;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpAgencySystemReportQueryDto;
import com.deppon.foss.module.transfer.partialline.test.BaseTestCase;

public class LdpAgencySystemReportQueryServiceTest extends BaseTestCase{

	@Autowired
	private ILdpAgencySystemReportQueryService ldpAgencySystemReportQueryService;
	@Test
	public void testQueryLdpAgencySystemReportByDto() {
		LdpAgencySystemReportQueryDto dto = new LdpAgencySystemReportQueryDto();
		CurrentInfo cInfo = new CurrentInfo(new UserEntity());
		ldpAgencySystemReportQueryService.queryLdpAgencySystemReportByDto(10, 0, dto, cInfo);
	}

	@Test
	public void testQueryTotalRecordsInDBByDto() {
		LdpAgencySystemReportQueryDto dto = new LdpAgencySystemReportQueryDto();
		CurrentInfo cInfo = new CurrentInfo(new UserEntity());
		ldpAgencySystemReportQueryService.queryTotalRecordsInDBByDto(dto, cInfo);
	}

}

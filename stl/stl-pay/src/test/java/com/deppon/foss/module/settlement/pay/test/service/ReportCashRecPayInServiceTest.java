package com.deppon.foss.module.settlement.pay.test.service;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.pay.api.server.service.IReportCashRecPayInService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.UpdateCashIncomerptDto;
import com.deppon.foss.module.settlement.pay.test.BaseTestCase;

public class ReportCashRecPayInServiceTest extends BaseTestCase{
	
	private static final Logger logger = LogManager
			.getLogger(ReportCashRecPayInServiceTest.class);

	@Autowired
	private IReportCashRecPayInService reportCashRecPayInService;
	
	/**
	 * @return the reportCashRecPayInService
	 */
	public IReportCashRecPayInService getReportCashRecPayInService() {
		return reportCashRecPayInService;
	}

	/**
	 * @param reportCashRecPayInService the reportCashRecPayInService to set
	 */
	public void setReportCashRecPayInService(
			IReportCashRecPayInService reportCashRecPayInService) {
		this.reportCashRecPayInService = reportCashRecPayInService;
	}

	@Test
	@Rollback(false)
	public void updateCashIncomerptForProcessor() {
		UpdateCashIncomerptDto dto = new UpdateCashIncomerptDto();
		dto.setSerialNO("BCLtest20140217001");
		dto.setPayAmount(new BigDecimal(-100));
		dto.setDeptCd("W011302020515");
		dto.setPayMethod("CD");
		dto.setUnifiedCode("DP02076");
		
		Map<String, Object> updateCashIncomerptForProcessor = reportCashRecPayInService.updateCashIncomerptForProcessor(dto);
		logger.info(updateCashIncomerptForProcessor.get("num") + "--" + updateCashIncomerptForProcessor.get("amountUse"));
	}
}
